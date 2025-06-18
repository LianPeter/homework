<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Teacher Registration - Assignment Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .register-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 30px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .register-title {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="register-container">
            <h2 class="register-title">老师注册</h2>
            
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <form action="register" method="post">
                <div class="mb-3">
                    <label for="username" class="form-label">用户名</label>
                    <input type="text" class="form-control" id="username" name="username" required 
                           pattern="[a-zA-Z0-9_]{4,20}" 
                           title="Username must be 4-20 characters long and can only contain letters, numbers, and underscore">
                </div>
                
                <div class="mb-3">
                    <label for="name" class="form-label">姓名</label>
                    <input type="text" class="form-control" id="name" name="name" required>
                </div>
                
                <div class="mb-3">
                    <label for="email" class="form-label">邮箱</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                
                <div class="mb-3">
                    <label for="password" class="form-label">密码</label>
                    <input type="password" class="form-control" id="password" name="password" required 
                           pattern=".{6,}" title="Password must be at least 6 characters long">
                </div>
                
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">确认密码</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                </div>
                
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">注册</button>
                </div>
                
                <div class="text-center mt-3">
                    <a href="login" class="text-decoration-none">已经有账户？在这里登陆</a>
                </div>
            </form>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Client-side password confirmation validation
        document.querySelector('form').addEventListener('submit', function(e) {
            var password = document.getElementById('password');
            var confirm = document.getElementById('confirmPassword');
            
            if (password.value !== confirm.value) {
                e.preventDefault();
                alert('Passwords do not match!');
                confirm.value = '';
                confirm.focus();
            }
        });
    </script>
</body>
</html>
