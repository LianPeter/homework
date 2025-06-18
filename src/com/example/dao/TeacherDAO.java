package com.example.dao;

import com.example.model.Teacher;
import com.example.util.DatabaseUtil;

import java.sql.*;

/**
 * 教师数据访问接口
 * 定义了教师相关的数据库操作方法
 */
public interface TeacherDAO {
    /**
     * 根据用户名查找教师
     * @param username 用户名
     * @return 教师对象，如果未找到返回null
     * @throws SQLException 数据库操作异常
     */
    Teacher findByUsername(String username) throws SQLException;

    /**
     * 根据ID查找教师
     * @param id 教师ID
     * @return 教师对象，如果未找到返回null
     * @throws SQLException 数据库操作异常
     */
    Teacher findById(int id) throws SQLException;

    /**
     * 插入新教师记录
     * @param teacher 教师对象
     * @return 插入成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean insert(Teacher teacher) throws SQLException;

    /**
     * 更新教师信息
     * @param teacher 教师对象
     * @return 更新成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean update(Teacher teacher) throws SQLException;

    /**
     * 删除教师记录
     * @param id 教师ID
     * @return 删除成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean delete(int id) throws SQLException;

    /**
     * 验证教师登录
     * @param username 用户名
     * @param password 密码
     * @return 验证成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean validateLogin(String username, String password) throws SQLException;
}
