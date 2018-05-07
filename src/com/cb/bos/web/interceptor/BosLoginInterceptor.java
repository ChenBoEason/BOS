package com.cb.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.cb.bos.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 
*<p>Title:BosLoginInterceptor</p>	
*<p>Description: 自定义登陆拦截器</p>	
*@author Eason
*@Time 2017年8月16日上午10:52:13
*这里继承MethodFilterInterceptor 只需要去实现doIntercept就行了 同时还可以指定不拦截的方法
 */
public class BosLoginInterceptor extends MethodFilterInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		
		if(user == null){
			return "login";
		}
		
		return invocation.invoke();
	}

}
