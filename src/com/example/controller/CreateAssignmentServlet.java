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

public class CreateAssignmentServlet extends HttpServlet {
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
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String deadlineStr = request.getParameter("deadline");

        // Validate input
        if (title == null || title.trim().isEmpty() || 
            content == null || content.trim().isEmpty() || 
            deadlineStr == null || deadlineStr.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
            return;
        }

        try {
            // Parse the deadline string to a Date first
            Date parsedDate = dateFormat.parse(deadlineStr);
            // Convert Date to Timestamp
            Timestamp deadline = new Timestamp(parsedDate.getTime());
            
            // Validate deadline is in the future
            if (deadline.before(new Timestamp(System.currentTimeMillis()))) {
                request.setAttribute("error", "Deadline must be in the future");
                request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
                return;
            }
            
            Assignment assignment = new Assignment();
            assignment.setTitle(title.trim());
            assignment.setContent(content.trim());
            assignment.setDeadline(deadline);
            assignment.setTeacherId(teacher.getId());
            assignment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            if (assignmentDAO.insert(assignment)) {
                response.sendRedirect(request.getContextPath() + "/assignments");
            } else {
                request.setAttribute("error", "Failed to create assignment");
                request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
            }
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format. Please use format: yyyy-MM-dd HH:mm");
            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
        }
    }
} 