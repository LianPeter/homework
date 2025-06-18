package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 转换类型为http
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 获取当前用户
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("teacher") != null);
        String loginURI = httpRequest.getContextPath() + "/login";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");
        boolean isRegisterRequest = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/register");
        boolean isRegisterPage = httpRequest.getRequestURI().endsWith("register.jsp");

        if (isLoggedIn && (isLoginRequest || isLoginPage || isRegisterRequest || isRegisterPage)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/dashboard");
        } else if (isLoggedIn || isLoginRequest || isLoginPage || isRegisterRequest || isRegisterPage) {

            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
    }
} 