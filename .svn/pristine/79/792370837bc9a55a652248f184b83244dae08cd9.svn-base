package com.cb.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.domain.Role;
import com.cb.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	private String ids;
	
	
	public String addRole(){
		this.roleService.save(getModel(),ids);
		return "list";
	}
	
	/**
	 * 分页显示
	 * @param ids
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		this.roleService.pageQuery(pageBean);
		String[] excludes = new String[]{"users","functions"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	/**
	 * 角色显示
	 * @param ids
	 * @throws IOException 
	 */
	public String listAjax() throws IOException{
		List<Role> list = this.roleService.findAll();
		String[] excludes = new String[]{"code","description","users","functions"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
