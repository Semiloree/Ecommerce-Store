package com.example.ecommercestore.controllerServlet;

import com.example.ecommercestore.dao.OrderDao;
import com.example.ecommercestore.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        Integer userId = (Integer) session.getAttribute("userId"); // Assuming userId is stored in session

        // Sample address and payment method; these should come from user inputs ideally
        String shippingAddress = "123 Example St"; // Replace with real data
        String paymentMethod = "Credit Card";      // Replace with real data

        if (cartList != null && !cartList.isEmpty() && userId != null) {
            OrderDao orderDao = new OrderDao();
            boolean orderSuccess = orderDao.createOrder(cartList, shippingAddress, paymentMethod, userId);

            if (orderSuccess) {
                // Clear the cart after successful order creation
                session.removeAttribute("cartList");

                // Redirect to the orders.jsp page to display the orders
                response.sendRedirect("orders.jsp");
            } else {
                // Handle order creation failure (e.g., display an error message)
                response.sendRedirect("error.jsp");
            }
        } else {
            // Handle the case where the cart is empty or user is not logged in
            response.sendRedirect("home.jsp");
        }
    }
}