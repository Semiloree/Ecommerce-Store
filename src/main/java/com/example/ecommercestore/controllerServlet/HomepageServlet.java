package com.example.ecommercestore.controllerServlet;

import com.example.ecommercestore.dao.ProductDao;
import com.example.ecommercestore.connections.DatabaseConnection;
import com.example.ecommercestore.model.Category;
import com.example.ecommercestore.model.Products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/Home")
public class HomepageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection = null;

        try {
            // Obtain the connection from the DatabaseConnection utility
            connection = DatabaseConnection.getConnection();

            // Initialize ProductDao with the obtained connection
            ProductDao productDAO = new ProductDao(connection);

            // Fetch categories and products
            List<Category> categories = productDAO.getAllCategories();
            List<Products> products = productDAO.getAllProducts();

            // Set attributes in session for later use
            session.setAttribute("categoriesList", categories);
            session.setAttribute("productsList", products);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Optionally, you can forward to an error page or set an error attribute
            request.setAttribute("errorMessage", "An error occurred while loading the homepage data.");
        } finally {
            // Properly close the connection to avoid leaks
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Log the exception properly
                }
            }
        }

        // Forward to home.jsp for rendering
        request.getRequestDispatcher("Home.jsp").forward(request, response);
    }
}