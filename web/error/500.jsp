<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 - Internal Server Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #f8f9fa;
        }
        .error-container {
            text-align: center;
            padding: 40px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .error-code {
            font-size: 96px;
            font-weight: bold;
            color: #dc3545;
            margin-bottom: 20px;
        }
        .error-message {
            font-size: 24px;
            color: #6c757d;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">500</div>
        <div class="error-message">Internal Server Error</div>
        <p class="text-muted mb-4">Sorry, something went wrong on our end. Please try again later.</p>
        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Go to Homepage</a>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 