package com.example.dao;

import com.example.model.StudentAssignment;
import java.sql.SQLException;
import java.util.List;

public interface StudentAssignmentDAO {
    StudentAssignment findById(int id) throws SQLException;
    List<StudentAssignment> findByAssignmentId(int assignmentId) throws SQLException;
    List<StudentAssignment> findByStudentId(String studentId) throws SQLException;
    boolean insert(StudentAssignment studentAssignment) throws SQLException;
    boolean update(StudentAssignment studentAssignment) throws SQLException;
    boolean delete(int id) throws SQLException;
    List<StudentAssignment> findAllByStatus(String status) throws SQLException;
} 