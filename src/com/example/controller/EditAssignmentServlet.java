package com.example.controller;

import com.example.dao.AssignmentDAO;
import com.example.dao.impl.AssignmentDAOImpl;
import com.example.model.Assignment;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditAssignmentServlet extends HttpServlet {
    private AssignmentDAO assignmentDAO;
    private SimpleDateFormat dateFormat;

    @Override
    public void init() throws ServletException {
        assignmentDAO = new AssignmentDAOImpl();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("EditAssignmentServlet: Starting doGet method");
        
        HttpSession session = request.getSession(false);
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            System.out.println("EditAssignmentServlet: No teacher found in session");
            response.sendRedirect("login");
            return;
        }

        String assignmentId = request.getParameter("id");
        if (assignmentId == null || assignmentId.trim().isEmpty()) {
            System.out.println("EditAssignmentServlet: No assignment ID provided");
            request.setAttribute("error", "作业ID不能为空");
            response.sendRedirect("assignments");
            return;
        }

        try {
            Assignment assignment = assignmentDAO.findById(Integer.parseInt(assignmentId));
            
            if (assignment == null) {
                System.out.println("EditAssignmentServlet: Assignment not found");
                request.setAttribute("error", "未找到指定作业");
                response.sendRedirect("assignments");
                return;
            }

            // 验证是否是该教师的作业
            if (assignment.getTeacherId() != teacher.getId()) {
                System.out.println("EditAssignmentServlet: Assignment does not belong to this teacher");
                request.setAttribute("error", "您没有权限编辑此作业");
                response.sendRedirect("assignments");
                return;
            }

            request.setAttribute("assignment", assignment);
            request.getRequestDispatcher("/edit-assignment.jsp").forward(request, response);
            
        } catch (SQLException e) {
            System.out.println("EditAssignmentServlet: Database error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "数据库错误：" + e.getMessage());
            response.sendRedirect("assignments");
        } catch (NumberFormatException e) {
            System.out.println("EditAssignmentServlet: Invalid assignment ID format");
            request.setAttribute("error", "无效的作业ID格式");
            response.sendRedirect("assignments");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("EditAssignmentServlet: Starting doPost method");
        
        HttpSession session = request.getSession(false);
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            System.out.println("EditAssignmentServlet: No teacher found in session");
            response.sendRedirect("login");
            return;
        }

        String assignmentId = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String deadlineStr = request.getParameter("deadline");

        System.out.println("EditAssignmentServlet: Received parameters - ID: " + assignmentId + 
                         ", Title: " + title + ", Content length: " + 
                         (content != null ? content.length() : "null") + 
                         ", Deadline: " + deadlineStr);

        try {
            if (assignmentId == null || title == null || content == null || deadlineStr == null ||
                assignmentId.trim().isEmpty() || title.trim().isEmpty() || 
                content.trim().isEmpty() || deadlineStr.trim().isEmpty()) {
                throw new IllegalArgumentException("所有字段都为必填项");
            }

            Assignment assignment = assignmentDAO.findById(Integer.parseInt(assignmentId));
            
            if (assignment == null) {
                throw new IllegalArgumentException("未找到指定作业");
            }

            if (assignment.getTeacherId() != teacher.getId()) {
                throw new IllegalArgumentException("您没有权限编辑此作业");
            }

            // 解析和验证截止日期
            Date parsedDate = dateFormat.parse(deadlineStr);
            Timestamp deadline = new Timestamp(parsedDate.getTime());

            // 更新作业信息
            assignment.setTitle(title.trim());
            assignment.setContent(content.trim());
            assignment.setDeadline(deadline);

            if (assignmentDAO.update(assignment)) {
                System.out.println("EditAssignmentServlet: Assignment updated successfully");
                request.getSession().setAttribute("success", "作业更新成功");
                response.sendRedirect("assignments");
            } else {
                throw new SQLException("更新作业失败");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("EditAssignmentServlet: Validation error: " + e.getMessage());
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (ParseException e) {
            System.out.println("EditAssignmentServlet: Date parsing error: " + e.getMessage());
            request.setAttribute("error", "日期格式无效，请使用 yyyy-MM-dd HH:mm 格式");
            doGet(request, response);
        } catch (SQLException e) {
            System.out.println("EditAssignmentServlet: Database error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "数据库错误：" + e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            System.out.println("EditAssignmentServlet: Unexpected error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "发生未知错误");
            doGet(request, response);
        }
    }
} 