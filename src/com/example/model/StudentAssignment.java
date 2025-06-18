package com.example.model;

import java.sql.Timestamp;

/**
 * 学生作业实体类
 * 用于存储学生提交的作业信息，包括提交内容、评分、反馈等
 */
public class StudentAssignment {
    private int id;                      // 提交记录ID
    private int assignmentId;            // 作业ID
    private String studentId;            // 学生ID
    private String studentName;          // 学生姓名
    private String submissionContent;    // 提交内容
    private String attachmentPath;       // 提交文件路径
    private Timestamp submissionTime;    // 提交时间
    private String status;               // 状态：未提交、已提交、迟交、已评分
    private Double grade;                // 分数
    private String feedback;             // 教师反馈
    private int attemptCount;            // 提交次数
    private boolean isOriginal;          // 是否原创
    private Double similarityScore;      // 相似度评分
    private Timestamp lastModified;      // 最后修改时间
    private Timestamp createdAt;         // 创建时间

    /**
     * 默认构造函数
     */
    public StudentAssignment() {}

    /**
     * 带参数的构造函数
     * @param id 提交记录ID
     * @param assignmentId 作业ID
     * @param studentId 学生ID
     * @param studentName 学生姓名
     * @param submissionContent 提交内容
     * @param attachmentPath 附件路径
     * @param submissionTime 提交时间
     * @param status 状态
     * @param grade 分数
     * @param feedback 反馈
     * @param attemptCount 提交次数
     * @param isOriginal 是否原创
     * @param similarityScore 相似度评分
     * @param lastModified 最后修改时间
     * @param createdAt 创建时间
     */
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

    /**
     * 检查是否为迟交
     * @return 如果是迟交返回true，否则返回false
     */
    public boolean isLateSubmission() {
        return "late_submitted".equals(status);
    }

    /**
     * 更新提交状态
     * @param isLate 是否为迟交
     */
    public void updateSubmissionStatus(boolean isLate) {
        this.status = isLate ? "late_submitted" : "submitted";
        this.attemptCount++;
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

    /**
     * 评分方法
     * @param grade 分数
     * @param feedback 反馈意见
     */
    public void grade(double grade, String feedback) {
        this.grade = grade;
        this.feedback = feedback;
        this.status = "graded";
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

    /**
     * 检查是否已评分
     * @return 如果已评分返回true，否则返回false
     */
    public boolean isGraded() {
        return "graded".equals(status);
    }
} 