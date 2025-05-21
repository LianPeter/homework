package com.example.dao;

import com.example.model.Teacher;
import com.example.util.DatabaseUtil;

import java.sql.*;

public class TeacherDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/homework_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "lianlian";

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

        try (Connection conn = DatabaseUtil.getConnection();
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

    public void ensureSchemaExists() {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            // 示例检查某个表是否存在，如果不存在则创建
            String sql = "CREATE TABLE IF NOT EXISTS teacher (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "email VARCHAR(255) NOT NULL UNIQUE," +
                    "password VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace(); // 实际项目中请记录日志
        }
    }
}
