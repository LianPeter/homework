package com.example.dao.impl;

import com.example.dao.StudentAssignmentDAO;
import com.example.model.StudentAssignment;
import com.example.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentAssignmentDAOImpl implements StudentAssignmentDAO {
    
    @Override
    public StudentAssignment findById(int id) throws SQLException {
        String sql = "SELECT * FROM student_assignments WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStudentAssignment(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<StudentAssignment> findByAssignmentId(int assignmentId) throws SQLException {
        List<StudentAssignment> submissions = new ArrayList<>();
        String sql = "SELECT * FROM student_assignments WHERE assignment_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, assignmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    submissions.add(mapResultSetToStudentAssignment(rs));
                }
            }
        }
        return submissions;
    }

    @Override
    public List<StudentAssignment> findByStudentId(String studentId) throws SQLException {
        List<StudentAssignment> submissions = new ArrayList<>();
        String sql = "SELECT * FROM student_assignments WHERE student_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    submissions.add(mapResultSetToStudentAssignment(rs));
                }
            }
        }
        return submissions;
    }

    @Override
    public boolean insert(StudentAssignment studentAssignment) throws SQLException {
        String sql = "INSERT INTO student_assignments (assignment_id, student_id, student_name, " +
                    "submission_content, submission_date, status, grade, feedback) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, studentAssignment.getAssignmentId());
            stmt.setString(2, studentAssignment.getStudentId());
            stmt.setString(3, studentAssignment.getStudentName());
            stmt.setString(4, studentAssignment.getSubmissionContent());
            stmt.setTimestamp(5, studentAssignment.getSubmissionTime());
            stmt.setString(6, studentAssignment.getStatus());
            stmt.setObject(7, studentAssignment.getGrade());
            stmt.setString(8, studentAssignment.getFeedback());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    studentAssignment.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    @Override
    public boolean update(StudentAssignment studentAssignment) throws SQLException {
        String sql = "UPDATE student_assignments SET submission_content = ?, submission_date = ?, " +
                    "status = ?, grade = ?, feedback = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentAssignment.getSubmissionContent());
            stmt.setTimestamp(2, studentAssignment.getSubmissionTime());
            stmt.setString(3, studentAssignment.getStatus());
            stmt.setObject(4, studentAssignment.getGrade());
            stmt.setString(5, studentAssignment.getFeedback());
            stmt.setInt(6, studentAssignment.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM student_assignments WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<StudentAssignment> findAllByStatus(String status) throws SQLException {
        List<StudentAssignment> submissions = new ArrayList<>();
        String sql = "SELECT * FROM student_assignments WHERE status = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    submissions.add(mapResultSetToStudentAssignment(rs));
                }
            }
        }
        return submissions;
    }

    private StudentAssignment mapResultSetToStudentAssignment(ResultSet rs) throws SQLException {
        StudentAssignment submission = new StudentAssignment();
        submission.setId(rs.getInt("id"));
        submission.setAssignmentId(rs.getInt("assignment_id"));
        submission.setStudentId(rs.getString("student_id"));
        submission.setStudentName(rs.getString("student_name"));
        submission.setSubmissionContent(rs.getString("submission_content"));
        submission.setSubmissionTime(rs.getTimestamp("submission_date"));
        submission.setStatus(rs.getString("status"));
        submission.setGrade(rs.getDouble("grade"));
        submission.setFeedback(rs.getString("feedback"));
        submission.setCreatedAt(rs.getTimestamp("created_at"));
        return submission;
    }
} 