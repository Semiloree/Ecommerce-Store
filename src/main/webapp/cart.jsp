<%--
  Created by IntelliJ IDEA.
  User: Jesusemilore
  Date: 8/28/2024
  Time: 11:47 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="com.example.ecommercestore.model.Cart"%>
<%@ page import="com.example.ecommercestore.model.Products"%>
<jsp:include page="includes/Homenavbar.jsp"/>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>
</head>
<body>

<div class="container">
    <div class="d-flex justify-content-between py-3">
        <h3>Total Price: #<%= session.getAttribute("totalPrice") != null ? session.getAttribute("totalPrice") : "0" %></h3>
        <a class="btn btn-primary" href="paymentsForm.jsp">Check Out</a>
    </div>
    <table class="table table-light">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Price</th>
            <th scope="col">Quantity</th>
            <th scope="col">Actions</th>
            <th scope="col">Remove</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Cart> cart_List = (List<Cart>) session.getAttribute("cartList");
            if (cart_List != null) {
                for (Cart c : cart_List) {
        %>
        <tr>
            <td><%= c.getProductName() %></td>
            <td>#<%= c.getPrice() %></td>
            <td>
                <input type="text" name="quantity" class="form-control mx-2" value="<%= c.getQuantity() %>" readonly>
            </td>
            <td>
                <!-- Plus and Minus Icons for Quantity Adjustment -->
                <div class="form-group d-flex align-items-center">
                    <a class="btn btn-sm btn-success" href="increaseQuantity?id=<%= c.getProductId() %>">
                        <i class="fas fa-plus-square">+</i>
                    </a>
                    <a class="btn btn-sm btn-warning mx-2" href="decreaseQuantity?id=<%= c.getProductId() %>">
                        <i class="fas fa-minus-square">-</i>
                    </a>
                </div>
            </td>
            <td>
                <a class="btn btn-sm btn-danger" href="removeFromCart?id=<%= c.getProductId() %>">Remove</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5" class="text-center">Your cart is empty.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

</body>
</html>