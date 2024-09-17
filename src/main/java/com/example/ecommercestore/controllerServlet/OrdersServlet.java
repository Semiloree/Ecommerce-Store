package com.example.ecommercestore.controllerServlet;

import com.example.ecommercestore.dao.OrderDao;
import com.example.ecommercestore.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewOrders")
public class OrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            OrderDao orderDao = new OrderDao();
            List<Order> ordersList = orderDao.getOrdersByUserId(userId);
            request.setAttribute("ordersList", ordersList);
        } else {
            request.setAttribute("ordersList", new ArrayList<>()); // Empty list if no user ID
        }

        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }
}