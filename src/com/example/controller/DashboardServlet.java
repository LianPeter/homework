package com.example.controller;

import com.example.dao.AssignmentDAO;
import com.example.dao.StudentAssignmentDAO;
import com.example.dao.impl.AssignmentDAOImpl;
import com.example.dao.impl.StudentAssignmentDAOImpl;
import com.example.model.Assignment;
import com.example.model.Teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class DashboardServlet extends HttpServlet {
    private AssignmentDAO assignmentDAO;
    private StudentAssignmentDAO studentAssignmentDAO;

    @Override
    public void init() throws ServletException {
        assignmentDAO = new AssignmentDAOImpl();
        studentAssignmentDAO = new StudentAssignmentDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("DashboardServlet: Starting doGet method");
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("DashboardServlet: Session is null");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            System.out.println("DashboardServlet: Teacher object is null in session");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        System.out.println("DashboardServlet: Teacher ID = " + teacher.getId());

        try {
            // Initialize with empty lists in case of errors
            List<Assignment> assignments = new ArrayList<>();
            List<Assignment> recentAssignments = new ArrayList<>();
            int pendingSubmissions = 0;
            int completedSubmissions = 0;

            // Get all assignments for the teacher
            System.out.println("DashboardServlet: Fetching assignments for teacher ID: " + teacher.getId());
            assignments = assignmentDAO.findByTeacherId(teacher.getId());
            request.setAttribute("totalAssignments", assignments.size());

            // Get recent assignments (limit to 5)
            recentAssignments = assignments.size() > 5 ? assignments.subList(0, 5) : assignments;
            request.setAttribute("recentAssignments", recentAssignments);

            // Get submission statistics
            System.out.println("DashboardServlet: Fetching submission statistics");
            try {
                pendingSubmissions = studentAssignmentDAO.findAllByStatus("not_submitted").size();
                completedSubmissions = studentAssignmentDAO.findAllByStatus("submitted").size();
            } catch (SQLException e) {
                System.out.println("DashboardServlet: Error fetching submission statistics: " + e.getMessage());
                // Continue execution even if statistics fail
            }

            request.setAttribute("pendingSubmissions", pendingSubmissions);
            request.setAttribute("completedSubmissions", completedSubmissions);

            System.out.println("DashboardServlet: Forwarding to dashboard.jsp");
            request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
            
        } catch (SQLException e) {
            System.out.println("DashboardServlet: SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("DashboardServlet: Unexpected error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 