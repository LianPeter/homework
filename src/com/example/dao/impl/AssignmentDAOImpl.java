package com.example.dao.impl;

import com.example.dao.AssignmentDAO;
import com.example.model.Assignment;
import com.example.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAOImpl implements AssignmentDAO {
    
    @Override
    public Assignment findById(int id) throws SQLException {
        String sql = "SELECT * FROM assignment WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAssignment(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Assignment> findByTeacherId(int teacherId) throws SQLException {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignment WHERE teacher_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    assignments.add(mapResultSetToAssignment(rs));
                }
            }
        }
        return assignments;
    }

    @Override
    public List<Assignment> findAll() throws SQLException {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignment ORDER BY created_at DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                assignments.add(mapResultSetToAssignment(rs));
            }
        }
        return assignments;
    }

    @Override
    public boolean insert(Assignment assignment) throws SQLException {
        String sql = "INSERT INTO assignment (teacher_id, title, content, deadline, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, assignment.getTeacherId());
            stmt.setString(2, assignment.getTitle());
            stmt.setString(3, assignment.getContent());
            stmt.setTimestamp(4, assignment.getDeadline());
            stmt.setTimestamp(5, assignment.getCreatedAt());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    assignment.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    @Override
    public boolean update(Assignment assignment) throws SQLException {
        String sql = "UPDATE assignment SET title = ?, content = ?, deadline = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, assignment.getTitle());
            stmt.setString(2, assignment.getContent());
            stmt.setTimestamp(3, assignment.getDeadline());
            stmt.setInt(4, assignment.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM assignment WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Assignment mapResultSetToAssignment(ResultSet rs) throws SQLException {
        Assignment assignment = new Assignment();
        assignment.setId(rs.getInt("id"));
        assignment.setTeacherId(rs.getInt("teacher_id"));
        assignment.setTitle(rs.getString("title"));
        assignment.setContent(rs.getString("content"));
        assignment.setDeadline(rs.getTimestamp("deadline"));
        assignment.setCreatedAt(rs.getTimestamp("created_at"));
        return assignment;
    }
} 