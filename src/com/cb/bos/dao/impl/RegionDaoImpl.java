package com.cb.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cb.bos.dao.IRegionDao;
import com.cb.bos.dao.base.impl.BaseDaoImpl;
import com.cb.bos.domain.Region;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	@Override
	public List<Region> findByQ(String q) {
		String hql = "FROM Region WHERE province LIKE ? OR city LIKE ? OR district LIKE ? OR 1=1";
		return this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%");
	}

	

}
