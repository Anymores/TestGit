package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class Module {
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String name;
	private Integer pid;
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
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	@Override
	public String toString() {
		return "Module [id=" + id + ", name=" + name + ", pid=" + pid + "]";
	}
	public Module(Integer id, String name, Integer pid) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
	}
	public Module() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
