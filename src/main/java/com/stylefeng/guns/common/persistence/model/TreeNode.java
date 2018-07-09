package com.stylefeng.guns.common.persistence.model;

import java.util.List;

public class TreeNode {
    public Integer id;
    public String name;
    public List<TreeNode> childList;
    public Integer parentID;


    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", childList=" + childList +
                ", parentID=" + parentID +
                '}';
    }

    public TreeNode() {
        super();
    }
}
