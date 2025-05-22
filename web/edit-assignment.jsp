<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑作业 - 作业管理系统</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" rel="stylesheet">
    <style>
        .sidebar {
            position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            z-index: 100;
            padding: 48px 0 0;
            box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
            background-color: #f8f9fa;
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
            font-size: 1.2rem;
            font-weight: 600;
        }
        .navbar {
            box-shadow: 0 2px 4px rgba(0,0,0,.1);
        }
        main {
            padding-top: 60px;
        }
        .card {
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
            border-radius: 0.5rem;
        }
        .card-body {
            padding: 2rem;
        }
        .form-label {
            font-weight: 500;
            color: #495057;
        }
        .form-control:focus {
            border-color: #80bdff;
            box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
        }
        .btn {
            padding: 0.5rem 1.5rem;
            font-weight: 500;
        }
        .nav-link {
            color: #495057;
            padding: 0.8rem 1rem;
            margin: 0.2rem 0;
            border-radius: 0.25rem;
        }
        .nav-link:hover {
            background-color: #e9ecef;
        }
        .nav-link.active {
            background-color: #e9ecef;
            color: #007bff;
        }
        .nav-link i {
            margin-right: 0.5rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-dark bg-dark fixed-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">作业管理系统</a>
            <div class="d-flex">
                <span class="navbar-text me-3">欢迎，${sessionScope.teacher.name} 老师</span>
                <a href="logout" class="btn btn-outline-light btn-sm">退出系统</a>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block sidebar">
                <div class="position-sticky sidebar-sticky">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link" href="dashboard">
                                <i class="bi bi-house-door"></i>
                                系统首页
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="create-assignment">
                                <i class="bi bi-plus-circle"></i>
                                发布作业
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="assignments">
                                <i class="bi bi-list-check"></i>
                                作业管理
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="view-submissions">
                                <i class="bi bi-inbox"></i>
                                提交管理
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">编辑作业内容</h1>
                </div>

                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <%= request.getAttribute("error") %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="关闭"></button>
                    </div>
                <% } %>

                <div class="card">
                    <div class="card-body">
                        <form action="edit-assignment" method="post" class="needs-validation" novalidate>
                            <input type="hidden" name="id" value="${assignment.id}">
                            
                            <div class="mb-4">
                                <label for="title" class="form-label">作业标题</label>
                                <input type="text" class="form-control" id="title" name="title" 
                                       value="${assignment.title}" required>
                                <div class="invalid-feedback">
                                    请输入作业标题
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="content" class="form-label">作业内容</label>
                                <textarea class="form-control" id="content" name="content" 
                                          rows="10" required>${assignment.content}</textarea>
                                <div class="invalid-feedback">
                                    请输入作业内容
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="deadline" class="form-label">截止时间</label>
                                <input type="text" class="form-control" id="deadline" name="deadline" 
                                       value="<fmt:formatDate value="${assignment.deadline}" pattern="yyyy-MM-dd HH:mm" />" 
                                       required>
                                <div class="invalid-feedback">
                                    请选择有效的截止时间
                                </div>
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button type="button" class="btn btn-secondary me-md-2" 
                                        onclick="window.location.href='assignments'">取消修改</button>
                                <button type="submit" class="btn btn-primary">保存修改</button>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/zh.js"></script>
    <script>
        // 初始化日期时间选择器
        flatpickr("#deadline", {
            enableTime: true,
            dateFormat: "Y-m-d H:i",
            time_24hr: true,
            minDate: new Date(),
            defaultHour: new Date().getHours(),
            defaultMinute: new Date().getMinutes(),
            allowInput: true,
            minuteIncrement: 1,
            locale: "zh",
            placeholder: "请选择截止时间"
        });

        // 表单验证
        (function () {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.prototype.slice.call(forms)
                .forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault()
                            event.stopPropagation()
                        }
                        form.classList.add('was-validated')
                    }, false)
                })
        })()
    </script>
</body>
</html> 