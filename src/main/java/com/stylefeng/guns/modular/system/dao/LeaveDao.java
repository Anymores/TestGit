package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.common.persistence.model.*;
import org.apache.ibatis.annotations.Param;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * 考勤Dao
 *
 * @author fengshuonan
 * @Date 2018-06-19 09:22:07
 */
public interface LeaveDao {
    /**
     * 根据姓名查询申请信息
     * @return LeaveGhl
     */
    List<LeaveGhl> getAllleave(@Param("name") String name);

    /**
     * 增加请假信息
     * @return int
     */
    int addLeave(LeaveGhl leaveGhl);

    /**
     * 根据ID查询详细信息
     * @param id
     * @return LeaveGhl
     */
    LeaveGhl findByid(int id);

    /**
     * 添加审核详细信息
     * @return int
     * @param DecisionLeave
     */
    int addDecision(@Param("lid") String lid,@Param("context") String context,@Param("state") String state,@Param("person") String person);

    /**
     * 修改申请状态
     * @param id
     * @return int
     */
    int updLeavestate(@Param("id") String id,@Param("state") String state);

    /**
     * 根据申请ID查询申请人
     * @param id
     * @return User
     */
    User getUserforLeaveId(@Param("id") String id);

    /**
     * 根据登陆用户ID查找用户信息
     * @param id
     * @return
     */
    User getUserforId(@Param("id") String id);

    /**
     * 根据申请ID查询申请详情
     * @param id
     * @return DecisionLeave
     */
    List<DecisionLeave> getDecsForid(@Param("id") String id);

    /**
     * 根据登陆人姓名查询提交的请假申请
     * @param name
     * @return DecisionLeave
     */
    List<LeaveGhl> getDesforName(@Param("name") String name);

    /**
     * 根据ID删除自己的申请
     * @param id
     * @return
     */
    int delMyslefLeaveforId(@Param("id") String id);

    /**
     * 获取JSON集合用于自动补全
     * @return String
     */
    List<String> getJson(@Param("name") String name);

    /**
     * 上传文件保存信息至数据库
     * @param List<FileUploadSpy>
     * @return
     */
    int uploadFiles(List<FileUploadSpy> list);

    /**
     * 查询上传记录,(根据ID)
     * @param id
     * @return
     */
    FileUploadSpy findOrbyid(@Param("id")String id);

    /**
     * 查询多有上传的的文件
     * @return
     */
    List<FileUploadSpy> getAllfiles();

    /**
     * 递归算法练习
     * @return Module
     */
    List<Module> diGui();

}
