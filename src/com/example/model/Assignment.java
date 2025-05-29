package com.example.model;

import java.sql.Timestamp;

public class Assignment {
    private int id;
    private int teacherId;
    private String title;
    private String content;
    private Timestamp deadline;
    private Timestamp createdAt;
    private String attachmentPath;  // 作业附件路径
    private int maxScore;          // 作业满分
    private boolean allowLateSubmission;  // 是否允许迟交
    private String course;          // 所属课程
    private String submissionType;  // 提交类型（如"文本"、"文件"等）

    public Assignment() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.maxScore = 100;  // 默认满分100分
        this.allowLateSubmission = false;  // 默认不允许迟交
        this.submissionType = "text";  // 默认文本提交
    }

    public Assignment(int id, int teacherId, String title, String content, Timestamp deadline, 
                     String attachmentPath, int maxScore, boolean allowLateSubmission,
                     String course, String submissionType) {
        this.id = id;
        this.teacherId = teacherId;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.attachmentPath = attachmentPath;
        this.maxScore = maxScore;
        this.allowLateSubmission = allowLateSubmission;
        this.course = course;
        this.submissionType = submissionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
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

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public boolean isAllowLateSubmission() {
        return allowLateSubmission;
    }

    public void setAllowLateSubmission(boolean allowLateSubmission) {
        this.allowLateSubmission = allowLateSubmission;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }
}
