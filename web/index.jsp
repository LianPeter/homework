<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>作业管理系统</title>
    <link rel="stylesheet" href="static/index.css">
</head>
<body>
<header>
    <h1>作业管理系统</h1>
</header>

<div class="container">
    <h2>欢迎来到作业管理系统</h2>
    <p>请登录以访问您的管理界面。</p>

    <div class="login-form">
        <form action="login" method="post">
            <label for="email">邮箱：</label>
            <input type="email" id="email" name="email" required placeholder="请输入您的邮箱">

            <label for="password">密码：</label>
            <input type="password" id="password" name="password" required placeholder="请输入您的密码">

            <button type="submit">登录</button>
        </form>
    </div>

    <p>没有账户？ <a href="register.jsp">注册</a></p>
</div>

<footer>
    <p>作业管理系统 &copy; 2025</p>
</footer>
</body>
</html>
