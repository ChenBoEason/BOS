package com.cb.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.dao.IStaffDao;
import com.cb.bos.domain.Staff;
import com.cb.bos.service.IStaffService;
import com.cb.bos.utils.PageBean;

/**
 * 
*<p>Title:StaffServiceImpl</p>	
*<p>Description:派取员</p>	
*@author Eason
*@Time 2017年8月18日下午4:40:25
 */
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

	@Autowired
	private IStaffDao staffDao;
	
	/**
	 * 新增取派员
	 */
	@Override
	public void save(Staff model) {
		staffDao.save(model);
		
	}
	/**
	 * 分页查询加条件
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		this.staffDao.pageQuery(pageBean);
		
	}
	/**
	 * 通过ID作废派取员
	 */
	@Override
	public void deleteById(String ids) {
		String[] staffId = ids.split(",");
		for(String id:staffId){
			staffDao.executeUpdate("staff.delete", id);
		}
	}
	/**
	 * 修改更新派取员信息
	 */
	@Override
	public void update(Staff model) {
		Staff staff = this.staffDao.findById(model.getId());
		
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		staff.setStandard(model.getStandard());
		staff.setHaspda(model.getHaspda());
		
	}
	/**
	 * 查询没有作废的取派员  deltag 为 0
	 */
	@Override
	public List<Staff> findByNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		//eq 代表等于  en代表不等于
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		return this.staffDao.findByCriteria(detachedCriteria);
	}
	@Override
	public void restore(String ids) {
		String[] staffid = ids.split(",");
		for (String id : staffid) {
			staffDao.executeUpdate("staff.restore", id);
		}
	}

}
