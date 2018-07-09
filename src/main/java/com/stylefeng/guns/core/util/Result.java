package com.stylefeng.guns.core.util;

import com.google.gson.Gson;



public class Result {
	public boolean success;
	public String message;
	public String remark;
	public static String toClient(boolean suc,Object msg){
		Result r = new Result();
		r.success =suc;
		r.message = msg.toString();
		r.remark="";
		Gson t = new Gson();
		return t.toJson(r);
	}
	public static String toClient(boolean suc,Object msg,String remark){
		Result r = new Result();
		r.success =suc;
		r.message = msg.toString();
		r.remark=remark;
		Gson t = new Gson();
		return t.toJson(r);
	}	
}
