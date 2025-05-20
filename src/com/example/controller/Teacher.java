package com.example.controller;

public class Teacher {
    private String teacherName;
    private String teacherEmail;
    private String teacherPassword;

    // Constructor
    public Teacher(String name, String email, String password) {
        this.teacherName = name;
        this.teacherEmail = email;
        this.teacherPassword = password;
    }

    // Getters and Setters
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    // Assuming teacher_id will be fetched from the database
    public Object getTeacherId() {
        return null; // Normally, this would be fetched from the database
    }
}
