<%--
  Created by IntelliJ IDEA.
  User: Jesusemilore
  Date: 9/11/2024
  Time: 11:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Bootstrap and jQuery JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        document.querySelector('.payment-form').addEventListener('submit', function (event) {
            let isValid = true;

            // Validate Account Number
            const accountNumber = document.getElementById('accountNumber');
            if (!/^\d{10,12}$/.test(accountNumber.value)) {
                isValid = false;
                document.getElementById('accountError').style.display = 'block';
            } else {
                document.getElementById('accountError').style.display = 'none';
            }

            // Validate Name on Card
            const cardName = document.getElementById('cardName');
            if (cardName.value.trim() === '') {
                isValid = false;
                document.getElementById('nameError').style.display = 'block';
            } else {
                document.getElementById('nameError').style.display = 'none';
            }

            // Validate Expiry Date
            const expiryDate = document.getElementById('expiryDate');
            if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(expiryDate.value)) {
                isValid = false;
                document.getElementById('expiryError').style.display = 'block';
            } else {
                document.getElementById('expiryError').style.display = 'none';
            }

            // Validate CVV
            const cvv = document.getElementById('cvv');
            if (!/^\d{3}$/.test(cvv.value)) {
                isValid = false;
                document.getElementById('cvvError').style.display = 'block';
            } else {
                document.getElementById('cvvError').style.display = 'none';
            }

            // If any field is invalid, prevent form submission
            if (!isValid) {
                event.preventDefault();
            }
        });
    </script>

    </script>
    <title>Title</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .payment-form {
            max-width: 500px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .error-message {
            display: none;
            color: red;
            font-size: 0.9rem;
        }

        .form-group label {
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="text-center mt-4">Checkout</h2>
    <form action="${pageContext.request.contextPath}/processPayment" method="post" class="payment-form">
        <div class="form-group">
            <label for="accountNumber">Account Number</label>
            <input type="text" id="accountNumber" name="accountNumber" class="form-control" required>
            <small class="error-message" id="accountError">Invalid account number.</small>
        </div>

        <div class="form-group">
            <label for="cardName">Name on Card</label>
            <input type="text" id="cardName" name="cardName" class="form-control" required>
            <small class="error-message" id="nameError">Please enter the name on the card.</small>
        </div>

        <div class="form-group">
            <label for="expiryDate">Expiry Date</label>
            <input type="text" id="expiryDate" name="expiryDate" class="form-control" placeholder="MM/YY" required>
            <small class="error-message" id="expiryError">Invalid expiry date.</small>
        </div>

        <div class="form-group">
            <label for="cvv">CVV</label>
            <input type="password" id="cvv" name="cvv" class="form-control" required maxlength="3">
            <small class="error-message" id="cvvError">Invalid CVV.</small>
        </div>

        <button type="submit" class="btn btn-primary btn-block">Pay Now</button>
    </form>
</div>

</body>
</html>
