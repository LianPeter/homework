<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.sql.Timestamp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>作业列表 - 作业管理系统</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .sidebar {
            position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            z-index: 100;
            padding: 48px 0 0;
            box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
        }
        .sidebar-sticky {
            position: relative;
            top: 0;
            height: calc(100vh - 48px);
            padding-top: .5rem;
            overflow-x: hidden;
            overflow-y: auto;
        }
        .navbar-brand {
            padding-top: .75rem;
            padding-bottom: .75rem;
        }
        .navbar {
            box-shadow: 0 .125rem .25rem rgba(0,0,0,.075);
        }
        main {
            padding-top: 60px; /* 增加顶部内边距，防止内容被导航栏遮挡 */
        }
        .table-responsive {
            margin-top: 20px;
        }
        .action-buttons {
            white-space: nowrap; /* 防止按钮换行 */
        }
        /* 优化表格在小屏幕上的显示 */
        @media (max-width: 768px) {
            .table td, .table th {
                min-width: 100px;
            }
            .table td:nth-child(2) {
                max-width: 200px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-dark bg-dark fixed-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">作业管理系统</a>
            <div class="d-flex">
                <span class="navbar-text me-3">欢迎, ${sessionScope.teacher.name}</span>
                <a href="logout" class="btn btn-outline-light btn-sm">退出登录</a>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
                <div class="position-sticky sidebar-sticky">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link" href="dashboard">
                                <i class="bi bi-house-door"></i>
                                主页
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="create-assignment">
                                <i class="bi bi-plus-circle"></i>
                                创建作业
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="assignments">
                                <i class="bi bi-list-check"></i>
                                作业列表
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="view-submissions">
                                <i class="bi bi-inbox"></i>
                                查看提交
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">作业列表</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <a href="create-assignment" class="btn btn-primary">
                            <i class="bi bi-plus"></i> 创建新作业
                        </a>
                    </div>
                </div>

                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger" role="alert">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>

                <% if(request.getSession().getAttribute("success") != null) { %>
                    <div class="alert alert-success" role="alert">
                        <%= request.getSession().getAttribute("success") %>
                        <% request.getSession().removeAttribute("success"); %>
                    </div>
                <% } %>

                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>标题</th>
                                <th>内容</th>
                                <th>创建时间</th>
                                <th>截止时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="assignment" items="${assignments}">
                                <tr>
                                    <td><c:out value="${assignment.title}" /></td>
                                    <td><c:out value="${assignment.content}" /></td>
                                    <td>
                                        <fmt:formatDate value="${assignment.createdAt}" pattern="yyyy-MM-dd HH:mm" />
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${assignment.deadline}" pattern="yyyy-MM-dd HH:mm" />
                                    </td>
                                    <td class="action-buttons">
                                        <a href="edit-assignment?id=${assignment.id}" class="btn btn-sm btn-primary" title="编辑">
                                            <i class="bi bi-pencil"></i>
                                        </a>
                                        <a href="delete-assignment?id=${assignment.id}" class="btn btn-sm btn-danger" 
                                           onclick="return confirm('确定要删除这个作业吗？')" title="删除">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 