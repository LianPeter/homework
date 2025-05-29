package com.example.dao.impl;

import com.example.dao.TeacherDAO;
import com.example.model.Teacher;
import com.example.util.DatabaseUtil;
import java.sql.*;

public class TeacherDAOImpl implements TeacherDAO {
    
    @Override
    public Teacher findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTeacher(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Teacher findById(int id) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTeacher(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean insert(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO teachers (username, password, name, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, teacher.getUsername());
            stmt.setString(2, teacher.getPassword());
            stmt.setString(3, teacher.getName());
            stmt.setString(4, teacher.getEmail());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Teacher teacher) throws SQLException {
        String sql = "UPDATE teachers SET username = ?, password = ?, name = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, teacher.getUsername());
            stmt.setString(2, teacher.getPassword());
            stmt.setString(3, teacher.getName());
            stmt.setString(4, teacher.getEmail());
            stmt.setInt(5, teacher.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM teachers WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean validateLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Teacher mapResultSetToTeacher(ResultSet rs) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(rs.getInt("id"));
        teacher.setUsername(rs.getString("username"));
        teacher.setPassword(rs.getString("password"));
        teacher.setName(rs.getString("name"));
        teacher.setEmail(rs.getString("email"));
        return teacher;
    }
} 