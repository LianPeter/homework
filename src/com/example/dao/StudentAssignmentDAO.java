package com.example.dao;

import com.example.model.StudentAssignment;

import java.sql.SQLException;
import java.util.List;

/**
 * 学生作业数据访问接口
 * 定义了学生作业相关的数据库操作方法
 */
public interface StudentAssignmentDAO {
    /**
     * 根据ID查找学生作业
     *
     * @param id 学生作业ID
     * @return 学生作业对象，如果未找到返回null
     * @throws SQLException 数据库操作异常
     */
    StudentAssignment findById(int id) throws SQLException;

    /**
     * 根据作业ID查找学生作业列表
     *
     * @param assignmentId 作业ID
     * @return 学生作业列表
     * @throws SQLException 数据库操作异常
     */
    List<StudentAssignment> findByAssignmentId(int assignmentId) throws SQLException;

    /**
     * 根据学生ID查找学生作业列表
     *
     * @param studentId 学生ID
     * @return 学生作业列表
     * @throws SQLException 数据库操作异常
     */
    List<StudentAssignment> findByStudentId(String studentId) throws SQLException;

    /**
     * 插入新学生作业记录
     *
     * @param studentAssignment 学生作业对象
     * @return 插入成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean insert(StudentAssignment studentAssignment) throws SQLException;

    /**
     * 更新学生作业信息
     *
     * @param studentAssignment 学生作业对象
     * @return 更新成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean update(StudentAssignment studentAssignment) throws SQLException;

    /**
     * 删除学生作业记录
     *
     * @param id 学生作业ID
     * @return 删除成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean delete(int id) throws SQLException;

    /**
     * 根据状态查找学生作业列表
     *
     * @param status 状态（如：未提交、已提交、迟交、已评分）
     * @return 学生作业列表
     * @throws SQLException 数据库操作异常
     */
    List<StudentAssignment> findAllByStatus(String status) throws SQLException;
} 