package com.example.ecommercestore.controllerServlet;

import com.example.ecommercestore.dao.ProductDao;
import com.example.ecommercestore.connections.DatabaseConnection;
import com.example.ecommercestore.model.Cart;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addToCart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the product ID from the request parameter
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp"); // Handle invalid productId input
            return;
        }

        // Retrieve the cart from session, initialize if null
        HttpSession session = request.getSession();
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        // Initialize database connection
        try (Connection conn = DatabaseConnection.getConnection()) {
            ProductDao productDAO = new ProductDao(conn);
            Products product = productDAO.getProductById(productId);

            if (product == null) {
                response.sendRedirect("error.jsp"); // Redirect if the product is not found
                return;
            }

            // Check if the product already exists in the cart
            Cart cartItem = cartList.stream()
                    .filter(c -> c.getProductId() == productId)
                    .findFirst()
                    .orElse(null);

            if (cartItem != null) {
                // Increment the quantity if product is already in the cart
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            } else {
                // Add the new product to the cart
                Cart newCartItem = new Cart();
                newCartItem.setProductId(product.getProductId());
                newCartItem.setProductName(product.getProductName());
                newCartItem.setPrice(product.getPrice());
                newCartItem.setQuantity(1); // Set initial quantity to 1
                cartList.add(newCartItem);
            }

            // Recalculate and update the total price in the session
            double totalPrice = calculateTotalPrice(cartList);
            session.setAttribute("totalPrice", totalPrice);
            session.setAttribute("cartList", cartList);

            // Redirect back to the home page
            response.sendRedirect(request.getContextPath() + "/Home.jsp");
        } catch (SQLException | ClassNotFoundException e) {
            // Log the exception and redirect to an error page
            e.printStackTrace(); // Replace with proper logging
            response.sendRedirect("error.jsp");
        }
    }

    // Calculate the total price of the cart
    private double calculateTotalPrice(List<Cart> cartList) {
        return cartList.stream()
                .mapToDouble(Cart::getTotalPrice) // Assumes getTotalPrice() is correctly implemented
                .sum();
    }
}