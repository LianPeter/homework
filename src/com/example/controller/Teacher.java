package com.example.controller;

public class Teacher {
    private String teacherName;
    private String teacherEmail;
    private String teacherPassword;

    public Teacher(String name, String email, String password) {
        this.teacherName = name;
        this.teacherEmail = email;
        this.teacherPassword = password;
    }

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

    public Object getTeacherId() {
        return null;
    }
}
