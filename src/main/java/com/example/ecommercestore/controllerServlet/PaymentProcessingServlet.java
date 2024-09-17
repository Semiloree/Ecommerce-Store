package com.example.ecommercestore.controllerServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import com.example.ecommercestore.model.Cart;
import com.example.ecommercestore.dao.OrderDao;

@WebServlet("/processPayment")
public class PaymentProcessingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("cartList") == null) {
            response.sendRedirect("cart.jsp?error=Your cart is empty or session expired.");
            return;
        }

        // Simulated payment details retrieval (for demo purposes)
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        String cvv = request.getParameter("cvv");

        // Simulate payment processing (Replace with actual payment gateway integration)
        if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            response.sendRedirect("paymentsForm.jsp?error=Invalid payment details");
            return;
        }

        // Retrieve cart and user information
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        int userId = (int) session.getAttribute("userId"); // Ensure userId is correctly fetched from the session

        // Update the order creation call to include all required arguments
        OrderDao orderDao = new OrderDao();
        boolean orderSuccess = orderDao.createOrder(cartList, "Some address", "Credit Card", userId);

        if (orderSuccess) {
            session.removeAttribute("cartList"); // Clear the cart
            session.setAttribute("totalPrice", 0.0); // Reset total price
            response.sendRedirect("orders.jsp"); // Redirect to the order tracking page
        } else {
            response.sendRedirect("paymentsForm.jsp?error=Payment failed, please try again.");
        }
    }
}