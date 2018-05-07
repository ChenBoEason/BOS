package com.cb.bos.service;

import java.util.List;

import com.cb.bos.domain.Role;
import com.cb.bos.utils.PageBean;

public interface IRoleService {

	public void save(Role model, String ids);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
