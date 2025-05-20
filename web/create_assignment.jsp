<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>布置作业 - 作业管理系统</title>
    <link rel="stylesheet" href="styles.css"> <!-- 自定义样式 -->
</head>
<body>
<header>
    <h1>作业管理系统</h1>
    <div class="navbar">
        <a href="view_assignments.jsp">查看作业</a>
        <a href="dashboard.jsp">返回管理后台</a>
    </div>
</header>

<div class="container">
    <h2>布置新作业</h2>

    <!-- 如果存在错误信息，显示错误提示 -->
    <c:if test="${param.error == '1'}">
        <div class="error-message">
            <p>作业布置失败，请稍后重试。</p>
        </div>
    </c:if>

    <!-- 作业布置表单 -->
    <form action="create_assignment" method="post">
        <div class="form-group">
            <label for="title">作业标题：</label>
            <input type="text" id="title" name="title" required placeholder="请输入作业标题">
        </div>

        <div class="form-group">
            <label for="description">作业描述：</label>
            <textarea id="description" name="description" required placeholder="请输入作业描述"></textarea>
        </div>

        <div class="form-group">
            <label for="dueDate">截止日期：</label>
            <input type="date" id="dueDate" name="dueDate" required>
        </div>

        <button type="submit">布置作业</button>
    </form>
</div>

<footer>
    <p>作业管理系统 &copy; 2025</p>
</footer>
</body>
</html>
