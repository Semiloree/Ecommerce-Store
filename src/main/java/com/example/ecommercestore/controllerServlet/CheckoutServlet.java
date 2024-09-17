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

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("cartList") == null) {
            response.sendRedirect("cart.jsp?error=Your cart is empty or session expired."); // Handle empty cart or session expiration
            return;
        }

        // Redirect to the payment form
        response.sendRedirect("paymentsForm.jsp");
    }
}
