package com.cb.bos.service.impl;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.dao.IRoleDao;
import com.cb.bos.dao.IUserDao;
import com.cb.bos.domain.Role;
import com.cb.bos.domain.User;
import com.cb.bos.service.IUserService;
import com.cb.bos.utils.MD5Utils;
import com.cb.bos.utils.PageBean;

@Service  // service层的注解
@Transactional  //开启事务管理
public class UserServiceImpl implements IUserService{

	//@Autowired  //也可以用@resource
	@Resource
	private IUserDao userDao;
	@Resource
	private IRoleDao roleDao;
	@Resource
	private IdentityService identityService;
	
	@Override
	public User login(User model) {
		String password = MD5Utils.md5(model.getPassword());
		String username = model.getUsername();
		return userDao.findByUsernameAndPassword(username,password);
	}
	
	@Override
	public void updatePassword(User user) {
		userDao.updatePasswordById(user);	
	}
	
	
	@Override
	public void pageQuery(PageBean pageBean) {
		this.userDao.pageQuery(pageBean);
	}
	
	
	/**
	 * 保存一个用户，同步到activity的act_id_user、act_id_membership
	 * 
	 */
	@Override
	public void save(User model, String[] roleId) {
		model.setPassword(MD5Utils.md5(model.getPassword()));
		this.userDao.save(model);
		org.activiti.engine.identity.User actUser = new UserEntity(model.getId());
		//将用户id同步到act_id_user
		identityService.saveUser(actUser);
		for (String id : roleId) {
			Role role = roleDao.findById(id);
			model.getRoles().add(role);
			//将用户id 角色名字存入act_id_membership中
			identityService.createMembership(model.getId(), role.getName());
		}
		
	}
	

}
