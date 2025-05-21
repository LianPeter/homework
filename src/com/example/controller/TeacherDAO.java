package com.example.controller;

import java.sql.*;

public class TeacherDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/teacher";
    private static final String USER = "your_db_user";
    private static final String PASSWORD = "your_db_password";

    public boolean registerTeacher(Teacher teacher) {
        String sql = "INSERT INTO teachers (teacher_name, teacher_email, teacher_password) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, teacher.getTeacherName());
            stmt.setString(2, teacher.getTeacherEmail());
            stmt.setString(3, teacher.getTeacherPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Teacher getTeacherByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM teachers WHERE teacher_email = ? AND teacher_password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Teacher teacher = new Teacher(rs.getString("teacher_name"), rs.getString("teacher_email"), rs.getString("teacher_password"));
                return teacher;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
