-- 创建数据库
CREATE DATABASE IF NOT EXISTS homework_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建用户并授予权限
CREATE USER IF NOT EXISTS 'homework_user'@'localhost' IDENTIFIED BY 'Homework@123456';
GRANT ALL PRIVILEGES ON homework_db.* TO 'homework_user'@'localhost';
FLUSH PRIVILEGES;

USE homework_db;

-- 创建老师表
CREATE TABLE IF NOT EXISTS teachers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建作业表
CREATE TABLE IF NOT EXISTS assignments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    teacher_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    deadline TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);

-- 创建学生作业表
CREATE TABLE IF NOT EXISTS student_assignments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    assignment_id INT NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    student_id VARCHAR(50) NOT NULL,
    submission_content TEXT,
    submission_date TIMESTAMP NULL,
    status ENUM('not_submitted', 'submitted', 'graded') DEFAULT 'not_submitted',
    grade DECIMAL(5,2),
    feedback TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (assignment_id) REFERENCES assignments(id)
);

-- 索引
CREATE INDEX idx_teacher_username ON teachers(username);
CREATE INDEX idx_assignment_teacher ON assignments(teacher_id);
CREATE INDEX idx_student_assignment_status ON student_assignments(status);

-- 初始账户
INSERT INTO teachers (username, password, name, email) 
VALUES ('admin', '123456', 'Administrator', 'admin@example.com'); 