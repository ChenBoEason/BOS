package com.cb.bos.dao;

import com.cb.bos.dao.base.IBaseDao;
import com.cb.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	public User findByUsernameAndPassword(String username, String password);

	public void updatePasswordById(User user);

	public User findUserByUsername(String username);

}
