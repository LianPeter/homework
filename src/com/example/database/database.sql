-- Create database
CREATE DATABASE IF NOT EXISTS homework_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE homework_db;

-- Create teachers table
CREATE TABLE IF NOT EXISTS teachers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create assignments table
CREATE TABLE IF NOT EXISTS assignments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    deadline DATETIME NOT NULL,
    teacher_id INT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

-- Create student_assignments table
CREATE TABLE IF NOT EXISTS student_assignments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    assignment_id INT NOT NULL,
    student_id VARCHAR(50) NOT NULL,
    student_name VARCHAR(50) NOT NULL,
    submission_content TEXT,
    submission_time DATETIME,
    status VARCHAR(20) DEFAULT 'not_submitted',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (assignment_id) REFERENCES assignments(id)
); 