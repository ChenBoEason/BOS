package com.cb.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cb.bos.dao.IUserDao;
import com.cb.bos.dao.base.impl.BaseDaoImpl;
import com.cb.bos.domain.User;
@Repository //默认单例  要想多例加@scope（"prototype"）
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		
		String hql = "FROM User u WHERE u.password = ? AND u.username = ?";
		
		List<User> list = this.getHibernateTemplate().find(hql,password,username);
		
		if(!list.isEmpty() && list.size()>0){
			return list.get(0);
		}
		return null;
		
		
		
	}

	@Override
	public void updatePasswordById(User user) {
		
		this.getHibernateTemplate().update(user);
		
	}

	@Override
	public User findUserByUsername(String username) {
		String hql = "FROM User u WHERE u.username = ?";
		
		List<User> list = this.getHibernateTemplate().find(hql,username);
		
		if(!list.isEmpty() && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	

}
