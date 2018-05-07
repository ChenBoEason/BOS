package com.cb.bos.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cb.bos.dao.IFunctionDao;
import com.cb.bos.dao.IUserDao;
import com.cb.bos.domain.Function;
import com.cb.bos.domain.User;

public class BOSRealm extends AuthorizingRealm{
	@Resource
	private IUserDao userDao;
	@Resource
	private IFunctionDao functionDao;
	/**
	 * 认证方法
	 * 
	 * shiro认证方式  通过用户名去数据库查数据 查出来将密码给token  token进行比对
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
			throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername(); //从令牌中获得用户名
		
		User user = userDao.findUserByUsername(username);
		
		if(user == null){
			return null;
		}else{
			
			String password = user.getPassword();
			
			/**
			 * 第一个参数  签名 程序可以在任意位置获取当前放入的对象 最好放user对象这样可以随时取出  
			 * 第二个参数固定放数据库取出的password 
			 * 第三个 当前realm 的名称   BOSRealm  == this.getClass().getSimpleName()
			 */
			//创建简单信息认证对象
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, this.getClass().getSimpleName());
			return info; //返回给安全管理器 由安全器负责比对数据库中查询出的密码和提交的密码
		}
	}
	
	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//为当前用户授予staff权限info.addStringPermission("staff"); 
		//info.addRole("staff"); 为当前用户授予角色
		//根据当前用户登陆用户查询 其对应权限  授权
		User user = (User) principals.getPrimaryPrincipal();
		List<Function> list = null;
		if(user.getUsername().equals("eason")){
			//当前用户是超级管理员 查询所有权限 为用户授权
			list = functionDao.findAll();
		}else{
			list = functionDao.findByUserId(user.getId());
		}
		for (Function function : list) {
			
			info.addStringPermission(function.getCode());
		}
		return info;
	}

	

}
