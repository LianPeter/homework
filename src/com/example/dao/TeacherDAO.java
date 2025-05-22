package com.example.dao;

import com.example.model.Teacher;
import com.example.util.DatabaseUtil;

import java.sql.*;

public interface TeacherDAO {
    Teacher findByUsername(String username) throws SQLException;
    Teacher findById(int id) throws SQLException;
    boolean insert(Teacher teacher) throws SQLException;
    boolean update(Teacher teacher) throws SQLException;
    boolean delete(int id) throws SQLException;
    boolean validateLogin(String username, String password) throws SQLException;
}
