-- Create database
CREATE DATABASE IF NOT EXISTS homework_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE homework_db;

-- Create teacher table
CREATE TABLE IF NOT EXISTS teacher (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create assignment table
CREATE TABLE IF NOT EXISTS assignment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    teacher_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    deadline DATETIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (teacher_id) REFERENCES teacher(id)
);

-- Create student_assignment table
CREATE TABLE IF NOT EXISTS student_assignment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    assignment_id INT NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    student_id VARCHAR(50) NOT NULL,
    submission_content TEXT,
    submission_date DATETIME,
    status ENUM('not_submitted', 'submitted', 'graded') DEFAULT 'not_submitted',
    grade DECIMAL(5,2),
    feedback TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (assignment_id) REFERENCES assignment(id)
);

-- Create indexes
CREATE INDEX idx_teacher_username ON teacher(username);
CREATE INDEX idx_assignment_teacher ON assignment(teacher_id);
CREATE INDEX idx_student_assignment_status ON student_assignment(status); 