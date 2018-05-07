package com.cb.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cb.bos.utils.PageBean;

/**
 * 
*<p>Title:IBaseDao</p>	
*<p>Description:抽取持久层通用方法</p>	
*@author Eason
*@Time 2017年8月14日下午10:27:10
* @param <T>
 */
public interface IBaseDao<T> {

	public void save(T t);
	
	public void delete(T t);
	
	public void update(T t);
	
	public void saveOrUpdate(T t);
	
	public T findById(Serializable id);
	
	public List<T> findAll();
	//通用条件加分页查询
	public void pageQuery(PageBean pageBean);
	//提供通用修改方法
	public void executeUpdate(String queryName,Object ...objects);
	
	//提供通用条件查询只包含条件
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
}
