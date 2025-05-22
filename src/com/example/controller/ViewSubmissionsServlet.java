package com.example.controller;

import com.example.dao.StudentAssignmentDAO;
import com.example.dao.impl.StudentAssignmentDAOImpl;
import com.example.model.StudentAssignment;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewSubmissionsServlet extends HttpServlet {
    private final StudentAssignmentDAO studentAssignmentDAO = new StudentAssignmentDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String assignmentId = request.getParameter("assignmentId");
        String status = request.getParameter("status");

        try {
            List<StudentAssignment> submissions;
            if (assignmentId != null && !assignmentId.trim().isEmpty()) {
                submissions = studentAssignmentDAO.findByAssignmentId(Integer.parseInt(assignmentId));
                request.setAttribute("assignmentId", assignmentId);
            } else if (status != null && !status.trim().isEmpty()) {
                submissions = studentAssignmentDAO.findAllByStatus(status);
                request.setAttribute("status", status);
            } else {
                // Default: show all submissions sorted by creation date
                submissions = studentAssignmentDAO.findAllByStatus("submitted");
            }
            
            request.setAttribute("submissions", submissions);
            request.getRequestDispatcher("/view-submissions.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error/500.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid assignment ID format");
            request.getRequestDispatcher("/error/400.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        String submissionId = request.getParameter("submissionId");
        
        if (submissionId == null || submissionId.trim().isEmpty()) {
            request.setAttribute("error", "Submission ID is required");
            doGet(request, response);
            return;
        }

        try {
            StudentAssignment submission = studentAssignmentDAO.findById(Integer.parseInt(submissionId));
            if (submission == null) {
                request.setAttribute("error", "Submission not found");
                doGet(request, response);
                return;
            }

            if ("grade".equals(action)) {
                String grade = request.getParameter("grade");
                String feedback = request.getParameter("feedback");
                
                if (grade != null && !grade.trim().isEmpty()) {
                    submission.setGrade(Double.parseDouble(grade));
                }
                if (feedback != null) {
                    submission.setFeedback(feedback.trim());
                }
                submission.setStatus("graded");
                
                if (studentAssignmentDAO.update(submission)) {
                    request.setAttribute("success", "Submission graded successfully");
                } else {
                    request.setAttribute("error", "Failed to grade submission");
                }
            }
            
            doGet(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error/500.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            request.getRequestDispatcher("/error/400.jsp").forward(request, response);
        }
    }
} 