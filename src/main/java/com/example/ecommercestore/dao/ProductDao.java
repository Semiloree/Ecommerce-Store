package com.example.ecommercestore.dao;

import com.example.ecommercestore.connections.DatabaseConnection;
import com.example.ecommercestore.model.Category;
import com.example.ecommercestore.model.Products;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private final Connection connection;

    // Constructor that initializes the DAO with a given connection
    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    // Retrieves all categories from the database
    public List<Category> getAllCategories() {
        List<Category> categoriesList = new ArrayList<>();
        String query = "SELECT * FROM categories";

        try (Connection conn = DatabaseConnection.getConnection(); // Always get connection from a single method
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("categoryId"),
                        rs.getString("categoryName"),
                        rs.getString("categoryImage")
                );
                categoriesList.add(category);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return categoriesList;
    }

    // Retrieves all products from the database
    public List<Products> getAllProducts() {
        List<Products> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection(); // Ensure consistent use of connections
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(new Products(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getDouble("price"),
                        rs.getString("productImage"),
                        rs.getInt("categoryId")
                ));
            }
            System.out.println("Products retrieved: " + products.size());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return products;
    }

    // Retrieves a product by its ID
    public Products getProductById(int productId) {
        String query = "SELECT * FROM products WHERE productId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Products(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getDouble("price"),
                        rs.getString("productImage"),
                        rs.getInt("categoryId")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Consider using a logger
        }
        return null; // Return null if not found
    }

    // Adds a new product to the database
    public void addProduct(Products product) {
        String query = "INSERT INTO products (productName, price, productImage, categoryId) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getProductImage());
            stmt.setInt(4, product.getCategoryId());
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Consider using a logger
        }
    }

    // Additional CRUD methods can be implemented similarly

}