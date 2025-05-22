<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>创建作业 - 作业管理系统</title>
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
            padding-top: 20px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-dark bg-dark fixed-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">作业管理系统</a>
            <div class="d-flex">
                <span class="navbar-text me-3">Welcome, ${sessionScope.teacher.name}</span>
                <a href="logout" class="btn btn-outline-light btn-sm">Logout</a>
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
                                仪表盘
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="create-assignment">
                                <i class="bi bi-plus-circle"></i>
                                创建作业
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="view-submissions">
                                <i class="bi bi-list-check"></i>
                                查看提交
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">创建作业</h1>
                </div>

                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger" role="alert">
                        <c:out value="${error}" />
                    </div>
                <% } %>

                <% if(request.getAttribute("success") != null) { %>
                    <div class="alert alert-success" role="alert">
                        <c:out value="${success}" />
                    </div>
                <% } %>

                <div class="card">
                    <div class="card-body">
                        <form action="create-assignment" method="post">
                            <div class="mb-3">
                                <label for="title" class="form-label">作业标题</label>
                                <input type="text" class="form-control" id="title" name="title" required>
                            </div>

                            <div class="mb-3">
                                <label for="content" class="form-label">内容</label>
                                <textarea class="form-control" id="content" name="content" rows="10" required></textarea>
                            </div>

                            <div class="mb-3">
                                <label for="deadline" class="form-label">截止日期</label>
                                <input type="text" class="form-control" id="deadline" name="deadline" required>
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button type="button" class="btn btn-secondary me-md-2" onclick="window.location.href='dashboard'">Cancel</button>
                                <button type="submit" class="btn btn-primary">创建作业</button>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script>
        flatpickr("#deadline", {
            enableTime: true,
            dateFormat: "Y-m-d H:i",
            minDate: new Date(),
            defaultHour: new Date().getHours(),
            defaultMinute: new Date().getMinutes() + 5,
            time_24hr: true,
            allowInput: true,
            minuteIncrement: 1,
            onClose: function(selectedDates, dateStr, instance) {
                if (dateStr) {
                    const formattedDate = instance.formatDate(selectedDates[0], "Y-m-d H:i");
                    instance.input.value = formattedDate;
                }
            }
        });

        document.querySelector('form').addEventListener('submit', function(e) {
            const title = document.getElementById('title').value.trim();
            const content = document.getElementById('content').value.trim();
            const deadline = document.getElementById('deadline').value.trim();
            
            if (!title || !content || !deadline) {
                e.preventDefault();
                alert('请填写所有字段');
                return false;
            }

            const dateRegex = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$/;
            if (!dateRegex.test(deadline)) {
                e.preventDefault();
                alert('请使用日期选择器选择一个有效的截止日期');
                return false;
            }

            const selectedDate = new Date(deadline.replace(' ', 'T'));
            const now = new Date();
            if (selectedDate <= now) {
                e.preventDefault();
                alert('请选择截止的日期和时间');
                return false;
            }
        });
    </script>
</body>
</html> 