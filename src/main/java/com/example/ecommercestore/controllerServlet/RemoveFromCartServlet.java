package com.example.ecommercestore.controllerServlet;

import com.example.ecommercestore.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
@WebServlet("/removeFromCart")
public class RemoveFromCartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        List<Cart> cart = (List<Cart>) session.getAttribute("cartList");

        if (cart != null) {
            // Remove the product from the cart
            cart.removeIf(c -> c.getProductId() == productId);
        }

        // Update the cart in the session
        session.setAttribute("cartList", cart);
        response.sendRedirect("cart.jsp");
    }
}
