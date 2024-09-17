<%--
  Created by IntelliJ IDEA.
  User: Jesusemilore
  Date: 8/28/2024
  Time: 11:47 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- Optional: For date formatting -->

<html>
<head>
    <title>Your Orders</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4">Your Orders</h2>

    <c:out value="${ordersList}" default="ordersList is not set" />

    <!-- Check if ordersList is not empty -->
    <c:if test="${not empty ordersList}">
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
            <tr>
                <th>Order ID</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Total Price</th>
                <th>Expected Delivery Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${ordersList}">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.productName}</td>
                    <td>${order.quantity}</td>
                    <td><fmt:formatNumber value="${order.price}" type="currency" /></td>
                    <td><fmt:formatNumber value="${order.totalPrice}" type="currency" /></td>
                    <td><fmt:formatDate value="${order.expectedDeliveryDate}" pattern="dd-MM-yyyy" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- Display message if ordersList is empty -->
    <c:if test="${empty ordersList}">
        <div class="alert alert-info text-center mt-4">
            No orders found. <a href="Home.jsp" class="alert-link">Continue Shopping</a>
        </div>
    </c:if>
</div>

<!-- Continue shopping button -->
<p class="text-center"><a href="Home.jsp" class="btn btn-primary mt-3">Continue Shopping</a></p>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>