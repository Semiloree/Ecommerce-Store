<%--
  Created by IntelliJ IDEA.
  User: Jesusemilore
  Date: 8/29/2024
  Time: 12:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="includes/navbar.jsp"/>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<div class="container">
    <div class="card w-50 mx-auto my-5">
        <div class="card-header text-center">Please Sign Up</div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/SignUpServlet" method="post">
                <div class="form-group">
                    <label>First Name</label>
                    <input type="text" class="form-control" name="FirstName" placeholder="Enter your First Name" required>
                </div>
                <div class="form-group">
                    <label>Last Name</label>
                    <input type="text" class="form-control" name="LastName" placeholder="Enter your Last Name" required>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" class="form-control" name="email" placeholder="Enter your Email" required>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" name="password" placeholder="Enter your Password" required>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Sign Up</button>
                </div>
            </form>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger mt-3">${errorMessage}</div>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="includes/footer.jsp"/>
</body>
</html>