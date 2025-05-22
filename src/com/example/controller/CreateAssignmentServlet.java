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
import java.util.Calendar;
import java.util.Date;

public class CreateAssignmentServlet extends HttpServlet {
    private AssignmentDAO assignmentDAO;
    private SimpleDateFormat dateFormat;

    @Override
    public void init() throws ServletException {
        try {
            assignmentDAO = new AssignmentDAOImpl();
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } catch (Exception e) {
            throw new ServletException("Error initializing CreateAssignmentServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            
            if (teacher == null) {
                System.out.println("CreateAssignmentServlet: No teacher found in session");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error in doGet: " + e.getMessage());
            e.printStackTrace();
            handleError(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("CreateAssignmentServlet: Starting assignment creation process");
        
        try {
            HttpSession session = request.getSession();
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            
            if (teacher == null) {
                System.out.println("CreateAssignmentServlet: No teacher found in session");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // 获取并验证输入参数
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String deadlineStr = request.getParameter("deadline");

            System.out.println("CreateAssignmentServlet: Received parameters - Title: " + title + 
                             ", Content length: " + (content != null ? content.length() : "null") + 
                             ", Deadline: " + deadlineStr);

            // 验证必填字段
            if (!validateInput(title, content, deadlineStr)) {
                System.out.println("CreateAssignmentServlet: Validation failed - missing required fields");
                request.setAttribute("error", "All fields are required");
                request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
                return;
            }

            // 解析和验证截止日期
            Timestamp deadline = parseAndValidateDeadline(deadlineStr);
            if (deadline == null) {
                System.out.println("CreateAssignmentServlet: Invalid deadline format");
                request.setAttribute("error", "Invalid date format. Please use format: yyyy-MM-dd HH:mm");
                request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
                return;
            }

            // 创建作业对象
            Assignment assignment = createAssignment(teacher.getId(), title.trim(), content.trim(), deadline);

            // 保存作业
            System.out.println("CreateAssignmentServlet: Attempting to insert assignment for teacher ID: " + teacher.getId());
            if (assignmentDAO.insert(assignment)) {
                System.out.println("CreateAssignmentServlet: Assignment created successfully with ID: " + assignment.getId());
                response.sendRedirect(request.getContextPath() + "/assignments");
            } else {
                System.out.println("CreateAssignmentServlet: Failed to create assignment - no rows affected");
                request.setAttribute("error", "Failed to create assignment");
                request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.err.println("Error in doPost: " + e.getMessage());
            e.printStackTrace();
            handleError(request, response, e);
        }
    }

    private boolean validateInput(String title, String content, String deadlineStr) {
        return title != null && !title.trim().isEmpty() && 
               content != null && !content.trim().isEmpty() && 
               deadlineStr != null && !deadlineStr.trim().isEmpty();
    }

    private Timestamp parseAndValidateDeadline(String deadlineStr) {
        try {
            System.out.println("CreateAssignmentServlet: Parsing deadline string: " + deadlineStr);
            Date parsedDate = dateFormat.parse(deadlineStr);
            Timestamp deadline = new Timestamp(parsedDate.getTime());
            
            // 获取当前时间（减去1分钟以允许一些误差）
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -1);
            Timestamp currentTime = new Timestamp(cal.getTimeInMillis());
            
            System.out.println("CreateAssignmentServlet: Comparing deadline " + deadline + " with current time " + currentTime);
            if (deadline.before(currentTime)) {
                System.out.println("CreateAssignmentServlet: Deadline validation failed - must be in future");
                return null;
            }
            
            return deadline;
        } catch (ParseException e) {
            System.err.println("Error parsing deadline: " + e.getMessage());
            return null;
        }
    }

    private Assignment createAssignment(int teacherId, String title, String content, Timestamp deadline) {
        Assignment assignment = new Assignment();
        assignment.setTeacherId(teacherId);
        assignment.setTitle(title);
        assignment.setContent(content);
        assignment.setDeadline(deadline);
        assignment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return assignment;
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        String errorMessage;
        if (e instanceof ParseException) {
            errorMessage = "Invalid date format. Please use format: yyyy-MM-dd HH:mm";
        } else if (e instanceof SQLException) {
            errorMessage = "Database error occurred: " + e.getMessage();
        } else {
            errorMessage = "An unexpected error occurred: " + e.getMessage();
        }
        
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
    }
} 