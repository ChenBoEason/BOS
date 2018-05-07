package com.cb.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.dao.IRegionDao;
import com.cb.bos.domain.Region;
import com.cb.bos.service.IRegionService;
import com.cb.bos.utils.PageBean;
/**
 * 
*<p>Title:RegionServiceImpl</p>	
*<p>Description:区域</p>	
*@author Eason
*@Time 2017年8月23日下午5:12:07
 */
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {

	@Resource 
	//@auotwried也可以
	private IRegionDao regionDao;
	/**
	 * 批量导入数据
	 */
	@Override
	public void saveRegion(List<Region> listRegions) {
		for (Region region : listRegions) {
			regionDao.saveOrUpdate(region);
		}
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		this.regionDao.pageQuery(pageBean);
	}
	@Override
	public List<Region> findAll() {
		return this.regionDao.findAll();
	}
	@Override
	public List<Region> findByQ(String q) {
		
		return this.regionDao.findByQ(q);
	}
	/**
	 * 添加或修改
	 */
	@Override
	public void saveOrUpdate(Region model) {
		this.regionDao.saveOrUpdate(model);
	}
	@Override
	public void deleteRegions(String ids) {
		String[] id = ids.split(",");
		for (String rid : id) {
			this.regionDao.executeUpdate("region.delete", rid);
		}
	}

}
