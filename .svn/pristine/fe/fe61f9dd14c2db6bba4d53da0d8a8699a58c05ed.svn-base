package com.cb.bos.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
*<p>Title:User</p>	
*<p>Description:</p>	
*@author Eason
*@Time 2017年8月14日下午10:10:01
 */

public class User implements Serializable {

	// Fields

	private String id;
	private String username;
	private String password;
	private Double salary;
	private Date birthday;
	private String gender;
	private String station;
	private String telephone;
	private String remark;
	private Set<Role> roles = new HashSet<Role>(0);
	private Set<Noticebill> noticebills = new HashSet<Noticebill>(0);
	// Constructors

	
	/**
	 * 由于页面显示birthday为 [object Object]因为json转化为这样形式 
	 * 所以我们自己构造
	 */
	public String getFormartBirthday(){
		if(birthday != null){
			return new SimpleDateFormat("yyyy-MM-dd").format(birthday);
		}else {
			return "未提交";
		}
	}
	/**
	 * 角色显示
	 */
	public String getRoleNames(){
		String names = "";
		if(roles.size() > 0){
			for(Role role: roles){
				names += role.getName() + " ";
			}
		}
		return names;
		
	}
	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	/** full constructor */
	public User(String id, String username, String password, Double salary,
			Date birthday, String gender, String station, String telephone,
			String remark) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.salary = salary;
		this.birthday = birthday;
		this.gender = gender;
		this.station = station;
		this.telephone = telephone;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Noticebill> getNoticebills() {
		return noticebills;
	}

	public void setNoticebills(Set<Noticebill> noticebills) {
		this.noticebills = noticebills;
	}

	



}