package com.cb.bos.web.action;

import java.io.IOException;
import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.domain.User;
import com.cb.bos.service.IUserService;
import com.cb.bos.utils.MD5Utils;
import com.cb.bos.web.action.base.BaseAction;
/**
 * 
*<p>Title:UserAction</p>	
*<p>Description: 表现层注解开发</p>	
*@author Eason
*@Time 2017年8月15日下午2:57:03
 */
@Controller("userAction")  /**
							里面值任意  这个值为任意  
							在struts.xml文件配置时如果不写默认类名首字母小写*/
@Scope("prototype")   //实现多例化  struts  多例化来
public class UserAction extends BaseAction<User> {
	private static final long serialVersionUID = 1L;

	@Resource
	private IUserService userService;
	
		
	private String checkcode;
	
	/**
	 * 使用shiro提供的方法进行认证
	 * @return
	 */
	public String login(){
		
		//获得生成的验证码
		String codeKey = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		
		//先判断验证码是否正确
		if (checkcode.equals(codeKey) && StringUtils.isNotBlank(codeKey)) {
			//获得当前用户对象
			Subject subject = SecurityUtils.getSubject();//状态为“未认证”
			String password = getModel().getPassword();
			password = MD5Utils.md5(password);
			//构造一个用户密码令牌
			AuthenticationToken token = 
					new UsernamePasswordToken(getModel().getUsername(), password);
			try {
				subject.login(token);
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				this.addActionError(this.getText("用户名不存在"));
				return "login";
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionError(this.getText("用户名或者密码错误"));
				return "login";
			}
			//获得认证信息对象 中存储的User对象
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("user", user);

			return "home";
		}else{
			//登陆失败  设置错误提示信息，跳转到登陆页面
			this.addActionError("验证码错误");
			return "login";
		}
	}
	/**
	 * 添加用户
	 * 接收角色id
	 * @return
	 */
	
	private String[] roleId;
	public String addUser(){
		this.userService.save(getModel(),roleId);
		return "list";
	}
	public String login_back(){
		
		//获得生成的验证码
		String codeKey = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		
			//先判断验证码是否正确
			if (checkcode.equals(codeKey) && StringUtils.isNotBlank(codeKey)) {
				User user = userService.login(this.getModel());
				
				if(user != null){
					//将user放入session中 跳转到系统首页
					ServletActionContext.getRequest().getSession().setAttribute("user", user);
					return "home";
				}else{
					//登陆失败  设置错误提示信息，跳转到登陆页面
					this.addActionError("用户或密码错误");
					return "login";
				}
			}else{
				//登陆失败  设置错误提示信息，跳转到登陆页面
				this.addActionError("验证码错误");
				return "login";
			}
		
		
		
		
	}
	
	/**
	 * 用户退出登陆
	 * @return
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	/**
	 * 修改当前用户密码
	 * @return
	 * @throws IOException 
	 */
	public String editPassword() throws IOException{
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		
		//新密码
		String newPassword = this.getModel().getPassword();
		
		newPassword = MD5Utils.md5(newPassword);
		user.setPassword(newPassword);
		
		String flag = "1";
		try{
			userService.updatePassword(user);
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
		}catch (Exception e) {
			//修改密码失败
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		//ServletActionContext.getResponse().getWriter().print(flag);
		ServletActionContext.getResponse().getWriter().print(flag);
		
		return NONE;
	}
	
	/**
	 * 用户分页显示
	 * @return
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		this.userService.pageQuery(pageBean);
		String[] excludes = new String[]{"roles","noticebills"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	public void setRoleId(String[] roleId) {
		this.roleId = roleId;
	}
	
	
}
