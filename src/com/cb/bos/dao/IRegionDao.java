package com.cb.bos.dao;

import java.util.List;

import com.cb.bos.dao.base.IBaseDao;
import com.cb.bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region>{

	public List<Region> findByQ(String q);

}
