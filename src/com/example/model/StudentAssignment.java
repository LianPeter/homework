package com.example.model;

import java.sql.Timestamp;

public class StudentAssignment {
    private int id;
    private int assignmentId;
    private String studentId;
    private String studentName;
    private String submissionContent;
    private String attachmentPath;    // 提交文件路径
    private Timestamp submissionTime;
    private String status;           // "not_submitted", "submitted", "late_submitted", "graded"
    private Double grade;
    private String feedback;
    private int attemptCount;        // 提交次数
    private boolean isOriginal;      // 是否原创
    private Double similarityScore;  // 相似度评分
    private Timestamp lastModified;  // 最后修改时间
    private Timestamp createdAt;

    public StudentAssignment() {}

    public StudentAssignment(int id, int assignmentId, String studentId, String studentName, 
                           String submissionContent, String attachmentPath, Timestamp submissionTime, 
                           String status, Double grade, String feedback, int attemptCount, 
                           boolean isOriginal, Double similarityScore, Timestamp lastModified, 
                           Timestamp createdAt) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.submissionContent = submissionContent;
        this.attachmentPath = attachmentPath;
        this.submissionTime = submissionTime;
        this.status = status;
        this.grade = grade;
        this.feedback = feedback;
        this.attemptCount = attemptCount;
        this.isOriginal = isOriginal;
        this.similarityScore = similarityScore;
        this.lastModified = lastModified;
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

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
    }

    public Double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(Double similarityScore) {
        this.similarityScore = similarityScore;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    // 新增方法：检查是否迟交
    public boolean isLateSubmission() {
        return "late_submitted".equals(status);
    }

    // 新增方法：更新提交状态
    public void updateSubmissionStatus(boolean isLate) {
        this.status = isLate ? "late_submitted" : "submitted";
        this.attemptCount++;
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

    // 新增方法：评分
    public void grade(double grade, String feedback) {
        this.grade = grade;
        this.feedback = feedback;
        this.status = "graded";
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

    // 新增方法：检查是否已评分
    public boolean isGraded() {
        return "graded".equals(status);
    }
} 