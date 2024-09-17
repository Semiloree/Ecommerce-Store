package com.example.ecommercestore.dao;

import com.example.ecommercestore.model.Cart;
import com.example.ecommercestore.model.Products;
import com.example.ecommercestore.controllerServlet.CartServlet;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartDao {

    // Retrieve the cart from the session
    public static List<Cart> getCart(HttpSession session) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        if (cartList == null) {
            cartList = new ArrayList<>();
            session.setAttribute("cartList", cartList);
        }
        return cartList;
    }

    // Add a product to the cart
    public static void addToCart(Products product, HttpSession session) {
        List<Cart> cartList = getCart(session);

        Optional<Cart> existingCartItem = cartList.stream()
                .filter(c -> c.getProductId() == product.getProductId())
                .findFirst();

        if (existingCartItem.isPresent()) {
            Cart cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1); // Increment quantity
        } else {
            Cart newCartItem = new Cart();
            newCartItem.setProductId(product.getProductId());
            newCartItem.setProductName(product.getProductName());
            newCartItem.setPrice(product.getPrice());
            newCartItem.setQuantity(1); // Default quantity
            cartList.add(newCartItem);
        }

        // Calculate the total price of the cart
        double totalPrice = calculateTotalPrice(cartList);

        // Update the cart list and total price in the session
        session.setAttribute("cartList", cartList);
        session.setAttribute("totalPrice", totalPrice); // Correct place to set total price

        System.out.println("Updated Total Price: " + totalPrice); // Debugging output
    }


    // Update the quantity of a product in the cart
    public void updateCart(int productId, int quantity, HttpSession session) {
        List<Cart> cartList = getCart(session);

        cartList.stream()
                .filter(c -> c.getProductId() == productId)
                .findFirst()
                .ifPresent(c -> c.setQuantity(quantity));

        // session.setAttribute("cartList", cart);
        session.setAttribute("cartList", cartList);
        session.setAttribute("totalPrice", calculateTotalPrice(cartList));
    }

    // Remove a product from the cart
    public void removeFromCart(int productId, HttpSession session) {
        List<Cart> cartList = getCart(session);

        cartList.removeIf(c -> c.getProductId() == productId);

        session.setAttribute("cartList", cartList);
    }

    // Get the total price of items in the cart
    public static double calculateTotalPrice(List<Cart> cartList) {
        // Calculate total price by summing up the total price of each cart item
        return cartList.stream()
                .mapToDouble(Cart::getTotalPrice) // Sum the total prices
                .sum();
    }

    // Clear the cart
    public void clearCart(HttpSession session) {
        session.removeAttribute("cartList");
    }

}
