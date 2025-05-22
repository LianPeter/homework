package com.example.controller;

import com.example.dao.AssignmentDAO;
import com.example.dao.impl.AssignmentDAOImpl;
import com.example.model.Assignment;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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

@WebServlet("/assignments")
public class AssignmentServlet extends HttpServlet {
    private final AssignmentDAO assignmentDAO;

    public AssignmentServlet() {
        this.assignmentDAO = new AssignmentDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            response.sendRedirect("login");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String deadlineStr = request.getParameter("deadline");

        try {
            // Parse the deadline string to a Timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parsedDate = dateFormat.parse(deadlineStr);
            Timestamp deadline = new Timestamp(parsedDate.getTime());

            // Create new assignment
            Assignment assignment = new Assignment();
            assignment.setTeacherId(teacher.getId());
            assignment.setTitle(title);
            assignment.setContent(content);
            assignment.setDeadline(deadline);

            assignmentDAO.insert(assignment);
            
            response.sendRedirect("dashboard");
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format");
            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to create assignment");
            request.getRequestDispatcher("/create-assignment.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        
        if (teacher == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            request.setAttribute("assignments", assignmentDAO.findByTeacherId(teacher.getId()));
            request.getRequestDispatcher("/assignments.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to load assignments");
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }
    }
}
