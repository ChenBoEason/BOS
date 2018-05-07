package com.cb.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cb.bos.dao.IFunctionDao;
import com.cb.bos.dao.base.impl.BaseDaoImpl;
import com.cb.bos.domain.Function;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{

	/**
	 * 根据用户id查询对应权限
	 * distinct 消除多张表中的重复数据  
	 * 两个左外连接 从Function表到User表 需要两次左外连接 
	 * 第一次连接到角色表 第二次角色表连接到用户表 都是多对多 中间都有各自对应的关系表
	 */
	@Override
	public List<Function> findByUserId(String id) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r" +
				" LEFT OUTER JOIN r.users u WHERE u.id = ?";
		return this.getHibernateTemplate().find(hql, id);
	}

	/**
	 * 根据用户id查询对应的功能列表
	 */
	@Override
	public List<Function> findMenuByUserId(String id) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r"+
					" LEFT OUTER JOIN r.users u "+
				"WHERE u.id = ? AND f.generatemenu = '1' ORDER BY f.zindex DESC";
		return this.getHibernateTemplate().find(hql, id);
	}

	/**
	 * 查询所有的生成目录
	 */
	@Override
	public List<Function> findAllMenu() {
		String hql = "FROM Function f WHERE f.generatemenu = '1' ORDER BY f.zindex DESC";
		return this.getHibernateTemplate().find(hql);
	}

}
