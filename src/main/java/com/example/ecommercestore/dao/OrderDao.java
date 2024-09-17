package com.example.ecommercestore.dao;

import com.example.ecommercestore.model.Cart;
import com.example.ecommercestore.model.Order;
import com.example.ecommercestore.connections.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    // Method to create an order in the database
    public boolean createOrder(List<Cart> cartList, String shippingAddress, String paymentMethod, int userId) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();

            // Corrected SQL query with distinct columns for price and totalPrice
            String insertOrderQuery = "INSERT INTO orders (productId, productName, quantity, price, totalPrice, shippingAddress, paymentMethod, estimatedDelivery, userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(insertOrderQuery);

            for (Cart cartItem : cartList) {
                ps.setInt(1, cartItem.getProductId());
                ps.setString(2, cartItem.getProductName());
                ps.setInt(3, cartItem.getQuantity());
                ps.setDouble(4, cartItem.getPrice()); // Unit price
                ps.setDouble(5, cartItem.getPrice() * cartItem.getQuantity()); // Calculating total price
                ps.setString(6, shippingAddress);
                ps.setString(7, paymentMethod);
                ps.setString(8, "2024-09-01"); // Example delivery date; adjust logic for dynamic date
                ps.setInt(9, userId);
                ps.addBatch();
            }

            ps.executeBatch();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Log the error properly in real applications
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle closing exception
                }
            }
        }
    }

    // Method to fetch orders by user ID
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> ordersList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Fetching orders for a specific user ID
            String query = "SELECT * FROM orders WHERE userId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderId")); // Ensure the column name matches your DB schema
                order.setUserId(rs.getInt("userId"));
                order.setProductName(rs.getString("productName"));
                order.setQuantity(rs.getInt("quantity"));
                order.setPrice(rs.getDouble("price")); // Unit price
                order.setTotalPrice(rs.getDouble("totalPrice")); // Corrected to fetch totalPrice
                order.setExpectedDeliveryDate(rs.getDate("estimatedDelivery"));
                ordersList.add(order);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Consider logging instead of printing the stack trace
        }
        return ordersList;
    }
}