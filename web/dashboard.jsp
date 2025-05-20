<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>老师管理后台 - 作业管理系统</title>
    <link rel="stylesheet" href="styles.css"> <!-- 自定义样式 -->
</head>
<body>
<header>
    <h1>作业管理系统</h1>
    <div class="navbar">
        <a href="view_assignments.jsp">查看作业</a>
        <a href="create_assignment.jsp">布置作业</a>
        <a href="logout.jsp">注销</a>
    </div>
</header>

<div class="container">
    <h2>欢迎回来，${teacher.teacherName}</h2>

    <!-- 作业列表 -->
    <h3>您的作业</h3>
    <p>以下是您布置的所有作业：</p>

    <table>
        <thead>
        <tr>
            <th>作业标题</th>
            <th>截止日期</th>
            <th>已提交</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <!-- 循环遍历作业 -->
        <c:forEach var="assignment" items="${assignments}">
            <tr>
                <td>${assignment.title}</td>
                <td>${assignment.dueDate}</td>
                <td>${assignment.submittedCount} / ${assignment.totalStudents}</td>
                <td>
                    <!-- 查看提交情况的链接 -->
                    <a href="view_submission.jsp?assignmentId=${assignment.id}">查看提交情况</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<footer>
    <p>作业管理系统 &copy; 2025</p>
</footer>
</body>
</html>
