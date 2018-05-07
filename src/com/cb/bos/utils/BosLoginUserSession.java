package com.cb.bos.utils;

import org.apache.struts2.ServletActionContext;

import com.cb.bos.domain.User;

public class BosLoginUserSession {

	public static User getLoginUser(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute("user");
	}
}
