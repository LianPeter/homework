package com.example.dao;

import com.example.model.Assignment;
import java.sql.SQLException;
import java.util.List;

/**
 * 作业数据访问接口
 * 定义了作业相关的数据库操作方法
 */
public interface AssignmentDAO {
    /**
     * 根据ID查找作业
     * @param id 作业ID
     * @return 作业对象，如果未找到返回null
     * @throws SQLException 数据库操作异常
     */
    Assignment findById(int id) throws SQLException;

    /**
     * 根据教师ID查找作业列表
     * @param teacherId 教师ID
     * @return 作业列表
     * @throws SQLException 数据库操作异常
     */
    List<Assignment> findByTeacherId(int teacherId) throws SQLException;

    /**
     * 查找所有作业
     * @return 所有作业的列表
     * @throws SQLException 数据库操作异常
     */
    List<Assignment> findAll() throws SQLException;

    /**
     * 插入新作业
     * @param assignment 作业对象
     * @return 插入成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean insert(Assignment assignment) throws SQLException;

    /**
     * 更新作业信息
     * @param assignment 作业对象
     * @return 更新成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean update(Assignment assignment) throws SQLException;

    /**
     * 删除作业
     * @param id 作业ID
     * @return 删除成功返回true，否则返回false
     * @throws SQLException 数据库操作异常
     */
    boolean delete(int id) throws SQLException;
}
