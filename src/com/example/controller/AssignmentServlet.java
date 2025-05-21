package com.example.controller;

import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/assignments")
public class AssignmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");

        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");

        AssignmentDAO assignmentDAO = new AssignmentDAO();
        assignmentDAO.createAssignment(new Assignment(teacher.getTeacherId(), title, description, dueDate));

        response.sendRedirect("dashboard.jsp");
    }
}
