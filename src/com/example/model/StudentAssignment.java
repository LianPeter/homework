package com.example.model;

import java.sql.Timestamp;

public class StudentAssignment {
    private int id;
    private int assignmentId;
    private String studentId;
    private String studentName;
    private String submissionContent;
    private Timestamp submissionTime;
    private String status; // "not_submitted", "submitted", "graded"
    private Double grade;
    private String feedback;
    private Timestamp createdAt;

    public StudentAssignment() {}

    public StudentAssignment(int id, int assignmentId, String studentId, String studentName, 
                           String submissionContent, Timestamp submissionTime, String status,
                           Double grade, String feedback, Timestamp createdAt) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.submissionContent = submissionContent;
        this.submissionTime = submissionTime;
        this.status = status;
        this.grade = grade;
        this.feedback = feedback;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubmissionContent() {
        return submissionContent;
    }

    public void setSubmissionContent(String submissionContent) {
        this.submissionContent = submissionContent;
    }

    public Timestamp getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Timestamp submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
} 