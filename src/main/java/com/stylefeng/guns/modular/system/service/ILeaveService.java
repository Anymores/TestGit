package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.common.persistence.model.LeaveGhl;
import com.stylefeng.guns.common.persistence.model.Module;
import com.stylefeng.guns.common.persistence.model.TreeNode;

import java.util.List;

/**
 * 考勤Service
 *
 * @author fengshuonan
 * @Date 2018-06-19 09:22:07
 */
public interface ILeaveService {

    /**
     * 新增加请假申请
     * @param leaveGhl
     * @param name 登陆人姓名
     * @return int
     */
    int addLeave(LeaveGhl leaveGhl);

    /**
     *发送邮箱
     * @param name 发送人姓名
     * @param emil 接受邮箱
     * @param remark 新建内容
     * @param accout 发送人邮箱
     */
    void javaEmil(String name,String emil,String remark,String accout);

    /**
     * 管理员通过审核添加审核详细信息
     * @param id
     * @param context
     * @return
     */
    int adminSay(String id,String context,String state,String person);

    /**
     * 定时任务
     */
    void timer();

    /**
     * 递归练习
     * @retur Modelen
     */
    List<TreeNode> getDIgui();
}
