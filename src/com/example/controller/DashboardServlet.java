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
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Get all assignments for the teacher
            List<Assignment> assignments = assignmentDAO.findByTeacherId(teacher.getId());
            request.setAttribute("totalAssignments", assignments.size());

            // Get recent assignments (limit to 5)
            List<Assignment> recentAssignments = assignments.size() > 5 ? 
                assignments.subList(0, 5) : assignments;
            request.setAttribute("recentAssignments", recentAssignments);

            // Get submission statistics
            int pendingSubmissions = studentAssignmentDAO.findAllByStatus("not_submitted").size();
            int completedSubmissions = studentAssignmentDAO.findAllByStatus("submitted").size();

            request.setAttribute("pendingSubmissions", pendingSubmissions);
            request.setAttribute("completedSubmissions", completedSubmissions);

            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error/500.jsp");
        }
    }
} 