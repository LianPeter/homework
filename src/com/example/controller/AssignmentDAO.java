package com.example.controller;

import java.sql.*;

public class AssignmentDAO {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_db_user";
    private static final String PASSWORD = "your_db_password";

    public void createAssignment(Assignment assignment) {
        String sql = "INSERT INTO assignments (teacher_id, title, description, due_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the SQL query
            stmt.setObject(1, assignment.getTeacherId());
            stmt.setString(2, assignment.getTitle());
            stmt.setString(3, assignment.getDescription());
            stmt.setString(4, assignment.getDueDate());

            // Execute the query
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
