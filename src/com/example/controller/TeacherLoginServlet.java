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

/**
 * 教师登录Servlet
 * 处理教师登录相关的请求
 */
public class TeacherLoginServlet extends HttpServlet {
    private final TeacherDAO teacherDAO = new TeacherDAOImpl();

    /**
     * 处理GET请求
     * 如果用户已登录，重定向到仪表板页面
     * 否则显示登录页面
     */
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

    /**
     * 处理POST请求
     * 验证用户登录信息
     * 登录成功则创建会话并重定向到仪表板
     * 登录失败则返回错误信息
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            System.out.println("正在尝试登录，用户名: " + username);
            
            // 验证输入参数
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                request.setAttribute("error", "用户名和密码不能为空");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }

            // 验证登录
            if (teacherDAO.validateLogin(username, password)) {
                System.out.println("登录验证成功，用户名: " + username);
                Teacher teacher = teacherDAO.findByUsername(username);
                
                // 检查是否成功获取教师信息
                if (teacher == null) {
                    System.out.println("错误：登录验证成功但未找到教师信息");
                    request.setAttribute("error", "系统错误，请重试");
                    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    return;
                }
                
                // 创建会话并设置教师信息
                HttpSession session = request.getSession();
                session.setAttribute("teacher", teacher);
                System.out.println("会话已创建，教师信息已设置");
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                System.out.println("登录验证失败，用户名: " + username);
                request.setAttribute("error", "用户名或密码错误");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            // 处理数据库异常
            System.out.println("登录过程中发生SQL异常: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "数据库错误: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } catch (Exception e) {
            // 处理其他异常
            System.out.println("登录过程中发生未知错误: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "发生未知错误: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}


