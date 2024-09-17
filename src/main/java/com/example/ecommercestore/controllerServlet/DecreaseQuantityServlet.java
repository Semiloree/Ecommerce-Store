package com.example.ecommercestore.controllerServlet;

import com.example.ecommercestore.dao.CartDao;
import com.example.ecommercestore.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/decreaseQuantity")
public class DecreaseQuantityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            // Redirect or handle invalid product ID scenario
            response.sendRedirect("cart.jsp?error=Invalid product ID.");
            return;
        }

        HttpSession session = request.getSession(false); // Use false to prevent creating a new session
        if (session == null) {
            response.sendRedirect("cart.jsp?error=Session expired. Please try again.");
            return;
        }

        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        if (cartList == null) {
            response.sendRedirect("cart.jsp?error=Cart is empty.");
            return;
        }

        boolean found = false; // To track if the product was found
        for (Cart c : cartList) {
            if (c.getProductId() == productId) {
                if (c.getQuantity() > 1) {
                    c.setQuantity(c.getQuantity() - 1);
                } else {
                    // Optionally handle when quantity is 1 and prevent decrease
                    response.sendRedirect("cart.jsp?error=Cannot decrease quantity below 1.");
                    return;
                }
                found = true;
                break;
            }
        }

        if (!found) {
            response.sendRedirect("cart.jsp?error=Product not found in cart.");
            return;
        }

        // Update session attributes after modification
        // Recalculate and update the total price in the session
        session.setAttribute("totalPrice", CartDao.calculateTotalPrice(cartList));
        session.setAttribute("cartList", cartList);
        response.sendRedirect("cart.jsp");
    }
}