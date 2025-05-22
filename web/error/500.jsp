<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>500 - Internal Server Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6 text-center">
                <h1 class="display-1">500</h1>
                <h2 class="mb-4">Internal Server Error</h2>
                <p class="mb-4">Sorry, something went wrong on our server. We're working to fix it.</p>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Return to Login</a>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 