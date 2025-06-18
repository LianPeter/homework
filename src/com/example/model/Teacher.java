package com.example.model;

/**
 * 教师实体类
 * 用于存储教师的基本信息
 */
public class Teacher {
    private int id;          // 教师ID
    private String username; // 用户名
    private String password; // 密码
    private String name;     // 教师姓名
    private String email;    // 电子邮箱

    /**
     * 默认构造函数
     */
    public Teacher() {}

    /**
     * 带参数的构造函数
     * @param id 教师ID
     * @param username 用户名
     * @param password 密码
     * @param name 教师姓名
     * @param email 电子邮箱
     */
    public Teacher(int id, String username, String password, String name, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
