<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录 - 作业管理系统</title>
    <link rel="stylesheet" href="styles.css"> <!-- 添加自定义样式 -->
</head>
<body>
<header>
    <h1>作业管理系统</h1>
</header>

<div class="container">
    <h2>登录</h2>

    <!-- 错误提示 -->
    <c:if test="${param.error == '1'}">
        <div class="error-message">
            <p>邮箱或密码错误，请重试。</p>
        </div>
    </c:if>

    <!-- 登录表单 -->
    <form action="login" method="post">
        <div class="form-group">
            <label for="email">邮箱：</label>
            <input type="email" id="email" name="email" required placeholder="请输入您的邮箱">
        </div>

        <div class="form-group">
            <label for="password">密码：</label>
            <input type="password" id="password" name="password" required placeholder="请输入您的密码">
        </div>

        <button type="submit">登录</button>
    </form>

    <p>没有账户？ <a href="register.jsp">注册</a></p>
</div>

<footer>
    <p>作业管理系统 &copy; 2025</p>
</footer>
</body>
</html>
