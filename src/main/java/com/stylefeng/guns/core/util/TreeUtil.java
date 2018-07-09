package com.stylefeng.guns.core.util;

import com.stylefeng.guns.common.persistence.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    /**
     * 存放根节点
     * @param list 查询要递归的数据并放入TreeNode类
     * @return TreeNode
     */
    public static List<TreeNode> getParentNode(List<TreeNode> list){
        List<TreeNode> treeNodeList = new ArrayList();
        //循环所有TreeNode
        for (TreeNode treeNode : list) {
            System.out.println("工具类++++++++++++++++"+treeNode);
            //判断是否是根节点
            if (treeNode.getParentID()==0||treeNode.getParentID()==treeNode.getId()){
                //存放子节点  用getChilderNode()方法，传根节点的ID与所有TreeList集合
                treeNode.setChildList(getChilderNode(treeNode.getId(),list));
                treeNodeList.add(treeNode);
            }
        }
        return treeNodeList;
    }

    /**
     *
     * @param pid
     * @param list
     * @return
     */
    public static List<TreeNode> getChilderNode(int pid,List<TreeNode> list){
        System.out.println(pid);
        List<TreeNode> treeNodeList = new ArrayList();
        //遍历所有TreeNode集合
        for (TreeNode treeNode : list) {
            System.out.println(treeNode);
            //判断如果有TreeNode与传入的父节点Id相等，说明为这个根节点的子节点，然后递归调用自己
            if (pid==treeNode.getParentID()){
                //存放自己点，递归调用自己
                treeNode.setChildList(getChilderNode(treeNode.getId(),list));
                treeNodeList.add(treeNode);
            }
        }
        return treeNodeList;
    }

}
