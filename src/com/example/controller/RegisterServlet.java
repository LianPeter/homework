package com.example.controller;

import com.example.dao.TeacherDAO;
import com.example.dao.impl.TeacherDAOImpl;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

// 注册功能
public class RegisterServlet extends HttpServlet {
    private TeacherDAO teacherDAO;

    @Override
    public void init() throws ServletException {
        teacherDAO = new TeacherDAOImpl();
    }

    // 显示注册表单
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    // 处理注册表单
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            if (teacherDAO.findByUsername(username) != null) {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            // 创建一个新teacher
            Teacher teacher = new Teacher();
            teacher.setUsername(username);
            teacher.setName(name);
            teacher.setEmail(email);
            teacher.setPassword(password);

            if (teacherDAO.insert(teacher)) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("error", "Registration failed");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}