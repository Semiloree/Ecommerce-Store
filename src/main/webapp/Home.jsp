<%--
  Created by IntelliJ IDEA.
  User: Jesusemilore
  Date: 8/29/2024
  Time: 11:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.ecommercestore.connections.DatabaseConnection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.example.ecommercestore.model.User" %>
<%@ page import="com.example.ecommercestore.dao.ProductDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.ecommercestore.model.Products" %>
<%@ page import="com.example.ecommercestore.model.Category" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Establish a database connection
    Connection connection = null;
    try {
        try {
            connection = DatabaseConnection.getConnection(); // Ensure this method is available and correctly implemented
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ProductDao productDAO = new ProductDao(connection);
        List<Category> categories = productDAO.getAllCategories();
        List<Products> productsList = productDAO.getAllProducts();
        request.setAttribute("categoriesList", categories);
        request.setAttribute("productsList", productsList);
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>

<%
    Object categoriesObj = session.getAttribute("categoriesList");
    List<Category> categoriesList = new ArrayList<>();
    if (categoriesObj instanceof List<?>) {
    for (Object item : (List<?>) categoriesObj) {
    if (item instanceof Category) {
    categoriesList.add((Category) item);
    }
    }
    }

    Object productsObj = request.getAttribute("productsList");
    List<Products> productsList = new ArrayList<>();
    if (productsObj instanceof List<?>) {
    for (Object item : (List<?>) productsObj) {
    if (item instanceof Products) {
    productsList.add((Products) item);
    }
    }
    }

    if (categoriesList.isEmpty()) {
        System.out.println("<p>No categories available.</p>");
    } else {
        System.out.println("<p>Categories available: " + categoriesList.size() + "</p>");
    }

    if (productsList.isEmpty()) {
        System.out.println("<p>No products available.</p>");
    } else {
        System.out.println("<p>Products available: " + productsList.size() + "</p>");
    }
%>


<jsp:include page="includes/Homenavbar.jsp"/>
<html>
<head>

    <title>Home Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .categories-container, .products-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); /* Flexible columns with a minimum width */
            gap: 20px; /* Equal space between items */
            padding: 20px;
            justify-items: center; /* Center items horizontally */
        }

        /* Card styling for categories and products */
        .category-card, .product-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 10px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease; /* Smooth transition for hover effects */
            background-color: #fff;
        }

        /* Image styling */
        .category-img, .product-img {
            max-width: 100%;
            height: auto;
            border-radius: 4px;
        }

        /* Links and buttons */
        .category-link, .order-btn {
            display: block;
            margin-top: 10px;
            text-decoration: none;
            color: blue;
        }

        .order-btn {
            margin-top: 5px;
        }

        /* Hover effects */
        .category-card:hover, .product-card:hover {
            transform: translateY(-10px); /* Rise up effect */
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15); /* Enhanced shadow */
        }

        .featured-container {
            display: grid;
            gap: 20px;
            padding: 20px;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            overflow-x: auto; /* Allows horizontal scrolling on small screens */
            justify-content: center; /* Centers the items */
        }

        .featured-card {
            flex: 0 0 300px; /* Makes each card a fixed width */
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            overflow: hidden;
            text-align: left; /* Aligns text to the left */
        }

        .featured-img {
            width: 100%;
            object-fit: cover; /* Keeps the image aspect ratio */
        }

        .featured-info {
            padding: 20px;
        }

        .featured-info h6 {
            font-size: 12px;
            text-transform: uppercase;
            color: #888; /* Gray color for the small heading */
        }

        .featured-info h3 {
            font-size: 22px;
            margin: 10px 0;
        }

        .featured-info p {
            color: #555; /* Slightly darker gray for the text */
        }

        /* Hover effect for cards */
        .featured-card:hover {
            transform: translateY(-10px); /* Rise effect */
            box-shadow: 0 15px 25px rgba(0, 0, 0, 0.2); /* Deeper shadow */
        }
    </style>
    <script>
        $(document).ready(function(){
            $('.category-card, .product-card').hover(function(){
                $(this).css('transform', 'translateY(-10px)');
                $(this).css('box-shadow', '0 10px 20px rgba(0, 0, 0, 0.15)');
            }, function(){
                $(this).css('transform', 'translateY(0)');
                $(this).css('box-shadow', '0 4px 8px rgba(0, 0, 0, 0.1)');
            });
        });
    </script>
</head>
<body class="bg-light">

<h1 class="mt-5" style="margin-left: 50px">Store. The best way to buy the products you love.</h1>

<h2 style="margin-left: 50px">Categories.</h2>
<div class="categories-container">
    <c:forEach var="category" items="${categoriesList}">
        <div class="category-card">
            <img src="${category.categoryImage}" alt="${category.categoryName}" class="category-img">
            <a href="${pageContext.request.contextPath}/product.jsp?categoryId=${category.categoryId}" class="category-link">${category.categoryName}</a>
        </div>
    </c:forEach>
</div>

<!-- The products part should use a separate logic to fetch and display products -->
<h2 style="margin-left: 50px">Products.</h2>
<div class="products-container">
    <c:forEach var="product" items="${productsList}">
        <div class="product-card">
            <img src="${product.productImage}" alt="${product.productName}" class="product-img">
            <div class="product-info">
                <h5>${product.productName}</h5>
                <p>$${product.price}</p>
                <form action="${pageContext.request.contextPath}/addToCart" method="post" class="d-inline">
                    <input type="hidden" name="productId" value="${product.productId}">
                    <button type="submit" class="btn btn-primary add-to-cart">Add to Cart</button>
                </form>

                <form action="${pageContext.request.contextPath}/OrdersServlet" method="post">
                    <!-- Include any required hidden fields like user ID, product details, etc. -->
                    <a href="${pageContext.request.contextPath}/orders.jsp?productId=${product.productId}" class="btn btn-success order-btn">Order</a>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<h2 style="margin-left: 50px">The Latest. Take a look at what's new, right now.</h2>
<div class="featured-container">
    <!-- Example Product Card -->
    <div class="featured-card">
        <img src="${pageContext.request.contextPath}/images/featuredImages/featuredImage1.jpg" alt="iPhone 15 Pro" class="featured-img">
        <div class="featured-info">
            <h6>iPhone 15 Pro</h6>
            <h3>Titanium</h3>
            <p>From $999 or $41.62/mo. for 24 mo.*</p>
        </div>
    </div>
    <div class="featured-card">
        <img src="${pageContext.request.contextPath}/images/featuredImages/featuredImage2.jpg" alt="Apple Vision Pro" class="featured-img">
        <div class="featured-info">
            <h6>Apple Vision Pro</h6>
            <h3>Welcome to spatial computing.</h3>
            <p>From $3499 or $291.58/mo. for 12 mo.*</p>
        </div>
    </div>
    <div class="featured-card">
        <img src="${pageContext.request.contextPath}/images/featuredImages/featuredImage3.jpg" alt="iPad Pro" class="featured-img">
        <div class="featured-info">
            <h6>iPad Pro</h6>
            <h3>Thinpossible.</h3>
            <p>From $999 or $83.25/mo. for 12 mo.*</p>
        </div>
    </div>
    <div class="featured-card">
        <img src="${pageContext.request.contextPath}/images/featuredImages/featuredImage4.jpg" alt="iPad" class="featured-img">
        <div class="featured-info">
            <h6>LIMITED-TIME OFFER</h6>
            <h3>Save on Mac or iPad for college.</h3>
            <p>Plus get a gift card up to $150,◊◊◊ 20% off AppleCare+,°°° and more.</p>
        </div>
    </div>
    <div class="featured-card">
        <img src="${pageContext.request.contextPath}/images/featuredImages/featuredImage5.jpg" alt="mac book" class="featured-img">
        <div class="featured-info">
            <h6>MACBOOK AIR</h6>
            <h3>Designed to go places.</h3>
            <p>From $999 or $83.25/mo.per month for 12 mo.monthsFootnote†</p>
        </div>
    </div>
    <div class="featured-card">
        <img src="${pageContext.request.contextPath}/images/featuredImages/featuredImage6.jpg" alt="ipad air" class="featured-img">
        <div class="featured-info">
            <h6>IPAD AIR</h6>
            <h3>Fresh Air.</h3>
            <p>From $599 or $49.91/mo.per month for 12 mo.monthsFootnote†</p>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<jsp:include page="includes/footer.jsp"/>
</body>
</html>