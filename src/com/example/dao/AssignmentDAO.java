package com.example.dao;

import com.example.model.Assignment;
import java.sql.SQLException;
import java.util.List;

public interface AssignmentDAO {
    Assignment findById(int id) throws SQLException;
    List<Assignment> findByTeacherId(int teacherId) throws SQLException;
    List<Assignment> findAll() throws SQLException;
    boolean insert(Assignment assignment) throws SQLException;
    boolean update(Assignment assignment) throws SQLException;
    boolean delete(int id) throws SQLException;
}
