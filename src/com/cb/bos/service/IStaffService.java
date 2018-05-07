package com.cb.bos.service;

import java.util.List;

import com.cb.bos.domain.Staff;
import com.cb.bos.utils.PageBean;

public interface IStaffService {

	public void save(Staff model);

	public void pageQuery(PageBean pageBean);

	public void deleteById(String ids);

	public void update(Staff model);

	public List<Staff> findByNotDelete();

	public void restore(String ids);

	

}
