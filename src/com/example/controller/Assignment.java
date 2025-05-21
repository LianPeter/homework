package com.example.controller;

public class Assignment {
    private Object teacherId;
    private String title;
    private String description;
    private String dueDate;

    public Assignment(Object teacherId, String title, String description, String dueDate) {
        this.teacherId = teacherId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Object getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Object teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
