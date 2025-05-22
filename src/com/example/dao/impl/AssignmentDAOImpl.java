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
        String sql = "SELECT * FROM assignments WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAssignment(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding assignment by ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    @Override
    public List<Assignment> findByTeacherId(int teacherId) throws SQLException {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignments WHERE teacher_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    assignments.add(mapResultSetToAssignment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding assignments by teacher ID: " + e.getMessage());
            throw e;
        }
        return assignments;
    }

    @Override
    public List<Assignment> findAll() throws SQLException {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignments ORDER BY created_at DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                assignments.add(mapResultSetToAssignment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all assignments: " + e.getMessage());
            throw e;
        }
        return assignments;
    }

    @Override
    public boolean insert(Assignment assignment) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); // 开启事务
            
            String sql = "INSERT INTO assignments (teacher_id, title, content, deadline, created_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, assignment.getTeacherId());
                stmt.setString(2, assignment.getTitle());
                stmt.setString(3, assignment.getContent());
                stmt.setTimestamp(4, assignment.getDeadline());
                stmt.setTimestamp(5, assignment.getCreatedAt());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating assignment failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        assignment.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating assignment failed, no ID obtained.");
                    }
                }
            }
            
            conn.commit(); // 提交事务
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting assignment: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // 发生错误时回滚事务
                } catch (SQLException ex) {
                    System.err.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 恢复自动提交
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public boolean update(Assignment assignment) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);
            
            String sql = "UPDATE assignments SET title = ?, content = ?, deadline = ? WHERE id = ? AND teacher_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, assignment.getTitle());
                stmt.setString(2, assignment.getContent());
                stmt.setTimestamp(3, assignment.getDeadline());
                stmt.setInt(4, assignment.getId());
                stmt.setInt(5, assignment.getTeacherId());
                
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating assignment failed, no rows affected.");
                }
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating assignment: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);
            
            String sql = "DELETE FROM assignments WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Deleting assignment failed, no rows affected.");
                }
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting assignment: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
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