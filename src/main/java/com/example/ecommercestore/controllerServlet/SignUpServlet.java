package com.example.ecommercestore.controllerServlet;

import com.example.ecommercestore.connections.DatabaseConnection;
import com.example.ecommercestore.dao.UserDao;
import com.example.ecommercestore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "SignUpServlet", value = "/SignUpServlet")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String firstName = request.getParameter("FirstName");
            String lastName = request.getParameter("LastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);

            try {
                Connection connection = DatabaseConnection.getConnection();
                UserDao userDao = new UserDao(connection);
                boolean userCreated = userDao.signUp(user);

                if (userCreated) {

                    // Set user object in session
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);

                     response.sendRedirect("welcome.jsp");

                    // response.sendRedirect(request.getContextPath() + "/welcome.jsp");
                } else {
                    request.setAttribute("errorMessage", "Sign-Up Failed. Please try again.");
                    request.getRequestDispatcher("Signup.jsp").forward(request, response);
                }
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("Signup.jsp");
    }
}