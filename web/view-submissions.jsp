<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看作业提交</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>作业提交列表</h2>
        
        <!-- 错误信息显示 -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>
        
        <!-- 成功信息显示 -->
        <c:if test="${not empty success}">
            <div class="alert alert-success" role="alert">
                ${success}
            </div>
        </c:if>

        <!-- 筛选表单 -->
        <div class="card mb-4">
            <div class="card-body">
                <form action="view-submissions" method="get" class="row g-3">
                    <div class="col-md-4">
                        <label for="assignmentId" class="form-label">作业ID</label>
                        <input type="number" class="form-control" id="assignmentId" name="assignmentId" 
                               value="${param.assignmentId}">
                    </div>
                    <div class="col-md-4">
                        <label for="status" class="form-label">状态</label>
                        <select class="form-select" id="status" name="status">
                            <option value="">全部</option>
                            <option value="submitted" ${param.status == 'submitted' ? 'selected' : ''}>已提交</option>
                            <option value="graded" ${param.status == 'graded' ? 'selected' : ''}>已评分</option>
                        </select>
                    </div>
                    <div class="col-md-4 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary">筛选</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- 提交列表 -->
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>学生</th>
                        <th>作业ID</th>
                        <th>提交时间</th>
                        <th>状态</th>
                        <th>分数</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${submissions}" var="submission">
                        <tr>
                            <td>${submission.id}</td>
                            <td>${submission.studentName}</td>
                            <td>${submission.assignmentId}</td>
                            <td><fmt:formatDate value="${submission.submissionTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td>
                                <span class="badge ${submission.status == 'graded' ? 'bg-success' : 'bg-warning'}">
                                    ${submission.status == 'graded' ? '已评分' : '待评分'}
                                </span>
                            </td>
                            <td>${submission.grade != null ? submission.grade : '-'}</td>
                            <td>
                                <button type="button" class="btn btn-sm btn-primary" 
                                        data-bs-toggle="modal" 
                                        data-bs-target="#gradeModal${submission.id}">
                                    查看/评分
                                </button>
                            </td>
                        </tr>
                        
                        <!-- 评分模态框 -->
                        <div class="modal fade" id="gradeModal${submission.id}" tabindex="-1">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">评分 - ${submission.studentName}的作业</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label class="form-label">提交内容</label>
                                            <div class="border rounded p-3 bg-light">
                                                ${submission.submissionContent}
                                            </div>
                                        </div>
                                        <form action="view-submissions" method="post">
                                            <input type="hidden" name="submissionId" value="${submission.id}">
                                            <input type="hidden" name="action" value="grade">
                                            <div class="mb-3">
                                                <label for="grade${submission.id}" class="form-label">分数</label>
                                                <input type="number" class="form-control" id="grade${submission.id}" 
                                                       name="grade" min="0" max="100" step="0.1"
                                                       value="${submission.grade}">
                                            </div>
                                            <div class="mb-3">
                                                <label for="feedback${submission.id}" class="form-label">反馈</label>
                                                <textarea class="form-control" id="feedback${submission.id}" 
                                                          name="feedback" rows="3">${submission.feedback}</textarea>
                                            </div>
                                            <button type="submit" class="btn btn-primary">提交评分</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <!-- 如果没有提交 -->
        <c:if test="${empty submissions}">
            <div class="alert alert-info" role="alert">
                暂无作业提交记录
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 