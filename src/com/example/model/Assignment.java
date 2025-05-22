package com.example.model;

import java.sql.Timestamp;

public class Assignment {
    private int id;
    private int teacherId;
    private String title;
    private String content;
    private Timestamp deadline;
    private Timestamp createdAt;

    public Assignment() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Assignment(int id, int teacherId, String title, String content, Timestamp deadline) {
        this.id = id;
        this.teacherId = teacherId;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.createdAt = new Timestamp(System.currentTimeMillis());
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
}
