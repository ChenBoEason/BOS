package com.cb.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.dao.IRoleDao;
import com.cb.bos.domain.Function;
import com.cb.bos.domain.Role;
import com.cb.bos.service.IRoleService;
import com.cb.bos.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService{

	@Resource
	private IRoleDao roleDao;
	@Resource
	private IdentityService identityService;

	@Override
	public void save(Role model, String ids) {
		
		this.roleDao.save(model);
		//与activity中的 组表关联起来   将角色的名字存入组中
		Group group = new GroupEntity(model.getName());
		
		identityService.saveGroup(group);
		
		String[] functionids = ids.split(",");
		for (String id : functionids) {
			Function function = new Function(id);
			//角色关联权限
			model.getFunctions().add(function);
		}
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		this.roleDao.pageQuery(pageBean);
	}

	@Override
	public List<Role> findAll() {
		
		return this.roleDao.findAll();
	}

	
	
	
}
