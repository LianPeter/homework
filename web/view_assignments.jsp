<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看作业 - 作业管理系统</title>
    <link rel="stylesheet" href="styles.css"> <!-- 自定义样式 -->
</head>
<body>
<header>
    <h1>作业管理系统</h1>
</header>

<div class="container">
    <h2>查看作业</h2>

    <!-- 如果没有作业，显示提示 -->
    <c:if test="${empty assignments}">
        <p>没有找到任何作业。</p>
    </c:if>

    <!-- 作业列表 -->
    <table>
        <thead>
        <tr>
            <th>作业标题</th>
            <th>作业描述</th>
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
                <td>${assignment.description}</td>
                <td>${assignment.dueDate}</td>
                <td>
                    <!-- 显示已提交的学生数量 -->
                        ${assignment.submittedCount} / ${assignment.totalStudents}
                </td>
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
