package com.cb.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cb.bos.dao.base.IBaseDao;
import com.cb.bos.utils.PageBean;


public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	private Class<?> TClass;
	//通过注解方式进行依赖注入  设置sessionFactory 如果不设置getHibernateTemplate()为空
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	//T  在编译时 它只是变量。 在运行时才可以获得具体的类型
	public BaseDaoImpl() {
		//获得运行时的类型  ，BaseDaoImpl<T> 被参数化的类型
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得实际参数值，获得它的所有  此时只有一个
		TClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
	}
	@Override
	public void save(T t) {
		this.getHibernateTemplate().save(t);
		
	}

	@Override
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
		
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
		
	}

	@Override
	public T findById(Serializable id) {
		
		return (T) this.getHibernateTemplate().get(TClass, id);
	}

	@Override
	public List<T> findAll() {
		String hql = "FROM " + TClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		int page = pageBean.getCurrentPage();
		int rows = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		//总数据量 ---- select count(*) from bs_staff
		//改变hibernate框架发出的sql形式
		//
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		//取第一个  也只有一个数字
		Long total = list.get(0);
		//设置总数据量
		pageBean.setTotal(total.intValue());
		//将SQL的形式改为select * from bs_staff
		detachedCriteria.setProjection(null);
		//重置表和映射关系
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		
		//查询分页数据
		int firstResult = (page - 1) * rows;
		int maxResult = rows;
		
		List list2 = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResult);
		pageBean.setRows(list2);
	}

	/**
	 * 通用的更新方法  queryName 在。hbm.xml中有响应的查询语句ID为该名字
	 * objects 传过来的参数  可以多个也可以单个
	 */
	@Override
	public void executeUpdate(String queryName, Object... objects) {
		//从本地线程获得session对象
		Session session = this.getSession();
		//使用命名查询语句获得一个查询对象
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		//为HQL中的？号赋值
		for(Object object: objects){
			query.setParameter(i++, object);
		}
		//执行更新
		query.executeUpdate();
		
	}

	@Override
	public void saveOrUpdate(T t) {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	
}
