<%--
  Created by IntelliJ IDEA.
  User: Jesusemilore
  Date: 9/2/2024
  Time: 2:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.ecommercestore.model.User" %>

<%
  User user = (User) session.getAttribute("user");
  String firstName = user != null ? user.getFirstName() : "Guest";
  String lastName = user != null ? user.getLastName() : "";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Welcome - Surulere Market</title>
  <!-- Bootstrap CSS -->
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .welcome-container {
      max-width: 500px;
      margin: 50px auto;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      border: 1px solid #e0e0e0;
    }
    .welcome-container .tick-icon {
      font-size: 3rem;
      color: #007bff; /* Bootstrap primary blue color */
    }
    .welcome-container h4 {
      margin-top: 15px;
      color: #28a745;
    }
    .welcome-container p {
      margin-top: 10px;
      color: #555;
    }
    .btn-login {
      background-color: #007bff;
      color: white;
    }
  </style>
</head>
<body>

<div class="container">
  <div class="welcome-container text-center bg-white">
    <!-- Blue tick icon using Bootstrap -->
    <i class="fas fa-check-circle tick-icon"></i>
    <h4>Welcome, <%= firstName %> <%= lastName %>!</h4>
    <p>You have successfully created an account.</p>
    <!-- Proceed to Login Page button -->
    <a href="${pageContext.request.contextPath}/Login.jsp" class="btn btn-login btn-block mt-3">Proceed to Login Page</a>
  </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<!-- Font Awesome for the tick icon -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/all.min.css">
</body>
</html>
