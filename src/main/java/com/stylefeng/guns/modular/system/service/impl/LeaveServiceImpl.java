package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.common.persistence.model.LeaveGhl;
import com.stylefeng.guns.common.persistence.model.Module;
import com.stylefeng.guns.common.persistence.model.TreeNode;
import com.stylefeng.guns.core.util.EmilTest;
import com.stylefeng.guns.core.util.MillisUtil;
import com.stylefeng.guns.core.util.TreeUtil;
import com.stylefeng.guns.modular.system.dao.LeaveDao;
import com.stylefeng.guns.modular.system.service.ILeaveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.tree.Tree;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 考勤Dao
 *
 * @author fengshuonan
 * @Date 2018-06-19 09:22:07
 */
@Service
public class LeaveServiceImpl extends Thread implements ILeaveService {

    @Resource
    LeaveDao leaveDao;

    @Override
    @Transactional
    public int addLeave(LeaveGhl leaveGhl) {
        return leaveDao.addLeave(leaveGhl);
    }

    @Override
    @Transactional
    public void javaEmil(String name, String emil, String remark, String accout) {
        EmilTest emilTest = new EmilTest();
        emilTest.setACCOUT(accout);
        try {
            emilTest.testSendEmail(name, emil, remark);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public int adminSay(String id, String context,String state,String person) {
        try {
            leaveDao.updLeavestate(id, state);
            leaveDao.addDecision(id, context, state,person);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
            return -1;
    }

    @Override
    public void timer() {
        MillisUtil millisUtil = new MillisUtil();
        try {
            List<Date> list=millisUtil.getDates("0 15 10 ? * *");
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (true){
                        System.out.print("hello!");
                        try {
                            Thread.sleep(millisUtil.getMillis(list));
                        }catch (InterruptedException  e){
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<TreeNode> getDIgui() {
        List<TreeNode> root = new ArrayList();
        try {
            List<Module> list=leaveDao.diGui();
            List<TreeNode> treeNodeList = new ArrayList();
            //将查询出的内容放入TreeNode集合
            for (Module module : list) {
                TreeNode treeNode = new TreeNode();
                treeNode.setId(module.getId());
                treeNode.setName(module.getName());
                treeNode.setParentID(module.getPid());
                treeNodeList.add(treeNode);
            }
            System.out.println("控制层:++++++++++++++++"+treeNodeList);
            root= TreeUtil.getParentNode(treeNodeList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return root;
    }
}
