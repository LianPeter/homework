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
import java.util.List;

public class AssignmentServlet extends HttpServlet {
    private AssignmentDAO assignmentDAO;
    private SimpleDateFormat dateFormat;

    @Override
    public void init() throws ServletException {
        try {
            assignmentDAO = new AssignmentDAOImpl();
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } catch (Exception e) {
            throw new ServletException("Error initializing AssignmentServlet", e);
        }
    }

    // 发布作业
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("AssignmentServlet: Starting doPost method");
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            System.out.println("AssignmentServlet: No teacher found in session");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String deadlineStr = request.getParameter("deadline");

        System.out.println("AssignmentServlet: Received parameters - Title: " + title + 
                         ", Content length: " + (content != null ? content.length() : "null") + 
                         ", Deadline: " + deadlineStr);

        try {
            if (title == null || title.trim().isEmpty() ||
                content == null || content.trim().isEmpty() || 
                deadlineStr == null || deadlineStr.trim().isEmpty()) {
                throw new IllegalArgumentException("All fields are required");
            }

            System.out.println("AssignmentServlet: Parsing deadline string: " + deadlineStr);
            // 字符串转换为date对象
            Date parsedDate = dateFormat.parse(deadlineStr);
            Timestamp deadline = new Timestamp(parsedDate.getTime());

            // 确保ddl日期的正确性
            if (deadline.before(new Timestamp(System.currentTimeMillis()))) {
                throw new IllegalArgumentException("Deadline must be in the future");
            }

            // 写入数据库
            Assignment assignment = new Assignment();
            assignment.setTeacherId(teacher.getId());
            assignment.setTitle(title.trim());
            assignment.setContent(content.trim());
            assignment.setDeadline(deadline);
            assignment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            System.out.println("AssignmentServlet: Attempting to insert assignment");
            if (assignmentDAO.insert(assignment)) {
                System.out.println("AssignmentServlet: Assignment created successfully");
                response.sendRedirect(request.getContextPath() + "/assignments");
            } else {
                throw new SQLException("Failed to create assignment");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("AssignmentServlet: Validation error: " + e.getMessage());
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
        } catch (ParseException e) {
            System.out.println("AssignmentServlet: Date parsing error: " + e.getMessage());
            request.setAttribute("error", "Invalid date format. Please use format: yyyy-MM-dd HH:mm");
            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println("AssignmentServlet: Database error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("AssignmentServlet: Unexpected error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred");
            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
        }
    }

    // 查看发布的作业
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("AssignmentServlet: Starting doGet method");
        try {
            HttpSession session = request.getSession();
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            
            if (teacher == null) {
                System.out.println("AssignmentServlet: No teacher found in session");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // 查询所有的作业
            List<Assignment> assignments = assignmentDAO.findByTeacherId(teacher.getId());
            System.out.println("AssignmentServlet: Found " + assignments.size() + " assignments");
            
            request.setAttribute("assignments", assignments);
            request.getRequestDispatcher("/assignments.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println("AssignmentServlet: Database error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error loading assignments: " + e.getMessage());
            request.getRequestDispatcher("/assignments.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("AssignmentServlet: Unexpected error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred");
            request.getRequestDispatcher("/assignments.jsp").forward(request, response);
        }
    }
}
