package com.stylefeng.guns.common.persistence.model;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2018-06-19
 */
@TableName("leave_ghl")
public class LeaveGhl extends Model<LeaveGhl> {

    private static final long serialVersionUID = 1L;

	//@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String name;
	private String phone;
	private Date startTime;
	private Date endTime;
    /**
     * 请假事由
     */
	private String remark;
    /**
     * 0未审核1审核不通过2审核通过
     */
	private String pass;
    /**
     * 0未通过1批准
     */
	private String tg;
    /**
     * 0未通过1批准
     */
	private String tgs;


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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTg() {
		return tg;
	}

	public void setTg(String tg) {
		this.tg = tg;
	}

	public String getTgs() {
		return tgs;
	}

	public void setTgs(String tgs) {
		this.tgs = tgs;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "LeaveGhl{" +
			"id=" + id +
			", name=" + name +
			", phone=" + phone +
			", startTime=" + startTime +
			", endTime=" + endTime +
			", remark=" + remark +
			", pass=" + pass +
			", tg=" + tg +
			", tgs=" + tgs +
			"}";
	}
}
