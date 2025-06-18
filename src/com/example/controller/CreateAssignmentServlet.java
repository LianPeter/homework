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

/**
 * 创建作业Servlet
 * 处理教师创建新作业的请求
 */
public class CreateAssignmentServlet extends HttpServlet {
    private AssignmentDAO assignmentDAO;
    private SimpleDateFormat dateFormat;

    /**
     * Servlet初始化方法
     * 初始化AssignmentDAO和日期格式化器
     */
    @Override
    public void init() throws ServletException {
        try {
            assignmentDAO = new AssignmentDAOImpl();
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } catch (Exception e) {
            throw new ServletException("Error initializing CreateAssignmentServlet", e);
        }
    }

    /**
     * 处理GET请求
     * 显示创建作业页面
     */
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

    /**
     * 处理POST请求
     * 处理创建作业的表单提交
     */
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

    /**
     * 验证输入参数
     * @param title 作业标题
     * @param content 作业内容
     * @param deadlineStr 截止日期字符串
     * @return 如果所有必填字段都不为空返回true，否则返回false
     */
    private boolean validateInput(String title, String content, String deadlineStr) {
        return title != null && !title.trim().isEmpty() && 
               content != null && !content.trim().isEmpty() && 
               deadlineStr != null && !deadlineStr.trim().isEmpty();
    }

    /**
     * 解析和验证截止日期
     * @param deadlineStr 截止日期字符串
     * @return 如果日期格式正确且是未来时间返回Timestamp对象，否则返回null
     */
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

    /**
     * 创建作业对象
     * @param teacherId 教师ID
     * @param title 作业标题
     * @param content 作业内容
     * @param deadline 截止日期
     * @return 新创建的作业对象
     */
    private Assignment createAssignment(int teacherId, String title, String content, Timestamp deadline) {
        Assignment assignment = new Assignment();
        assignment.setTeacherId(teacherId);
        assignment.setTitle(title);
        assignment.setContent(content);
        assignment.setDeadline(deadline);
        assignment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return assignment;
    }

    /**
     * 处理错误情况
     * @param request HTTP请求
     * @param response HTTP响应
     * @param e 异常对象
     */
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