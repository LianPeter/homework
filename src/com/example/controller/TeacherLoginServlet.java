package com.example.controller;

import com.example.dao.TeacherDAO;
import com.example.dao.impl.TeacherDAOImpl;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

public class TeacherLoginServlet extends HttpServlet {
    private final TeacherDAO teacherDAO = new TeacherDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacher") != null) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if (teacherDAO.validateLogin(username, password)) {
                Teacher teacher = teacherDAO.findByUsername(username);
                HttpSession session = request.getSession();
                session.setAttribute("teacher", teacher);
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}


