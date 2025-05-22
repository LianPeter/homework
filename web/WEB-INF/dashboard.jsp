<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.Teacher" %>
<!DOCTYPE html>
<html>
<head>
    <title>教师作业管理系统 - 仪表板</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">教师作业管理系统</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/dashboard">仪表板</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/create-assignment">创建作业</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/view-submissions">查看提交</a>
                    </li>
                </ul>
                <div class="d-flex">
                    <% Teacher teacher = (Teacher) session.getAttribute("teacher"); %>
                    <span class="navbar-text me-3">
                        欢迎, <%= teacher.getName() %>
                    </span>
                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-light">退出登录</a>
                </div>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h2>欢迎来到您的仪表板</h2>
        
        <% if(request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <% if(request.getAttribute("success") != null) { %>
            <div class="alert alert-success" role="alert">
                <%= request.getAttribute("success") %>
            </div>
        <% } %>

        <div class="row mt-4">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">创建新作业</h5>
                        <p class="card-text">为您的学生创建新的作业任务。</p>
                        <a href="${pageContext.request.contextPath}/create-assignment" class="btn btn-primary">创建作业</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">查看作业</h5>
                        <p class="card-text">查看和管理您发布的所有作业。</p>
                        <a href="${pageContext.request.contextPath}/assignments" class="btn btn-primary">查看作业</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">查看提交</h5>
                        <p class="card-text">审阅和评分学生提交的作业。</p>
                        <a href="${pageContext.request.contextPath}/view-submissions" class="btn btn-primary">查看提交</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 