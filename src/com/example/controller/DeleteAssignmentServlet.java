package com.example.controller;

import com.example.dao.AssignmentDAO;
import com.example.dao.impl.AssignmentDAOImpl;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteAssignmentServlet extends HttpServlet {
    private AssignmentDAO assignmentDAO;

    @Override
    public void init() throws ServletException {
        assignmentDAO = new AssignmentDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DeleteAssignmentServlet: Starting delete process");
        
        // Get the assignment ID from the request
        String assignmentId = request.getParameter("id");
        if (assignmentId == null || assignmentId.trim().isEmpty()) {
            System.out.println("DeleteAssignmentServlet: No assignment ID provided");
            request.setAttribute("error", "Assignment ID is required");
            response.sendRedirect("assignments");
            return;
        }

        try {
            // Get the teacher from the session
            HttpSession session = request.getSession(false);
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            
            if (teacher == null) {
                System.out.println("DeleteAssignmentServlet: No valid session found");
                response.sendRedirect("login");
                return;
            }

            // Delete the assignment
            boolean deleted = assignmentDAO.delete(Integer.parseInt(assignmentId));

            if (deleted) {
                System.out.println("DeleteAssignmentServlet: Assignment " + assignmentId + " deleted successfully");
                request.getSession().setAttribute("success", "Assignment deleted successfully");
            } else {
                System.out.println("DeleteAssignmentServlet: Failed to delete assignment " + assignmentId);
                request.getSession().setAttribute("error", "Failed to delete assignment");
            }
        } catch (NumberFormatException e) {
            System.out.println("DeleteAssignmentServlet: Invalid assignment ID format: " + assignmentId);
            e.printStackTrace();
            request.getSession().setAttribute("error", "Invalid assignment ID format");
        } catch (SQLException e) {
            System.out.println("DeleteAssignmentServlet: Database error: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("DeleteAssignmentServlet: Error deleting assignment: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error deleting assignment: " + e.getMessage());
        }

        // Redirect back to the assignments page
        response.sendRedirect("assignments");
    }
} 