package com.cb.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.domain.Function;
import com.cb.bos.web.action.base.BaseAction;
/**
 * 
*<p>Title:FunctionAction</p>	
*<p>Description:功能权限管理</p>	
*@author Eason
*@Time 2017年8月31日上午10:24:39
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{

	/**
	 * 权限数据分页显示
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		/**
		 * function中有一个属性叫page  
		 * modelDriven吧page数据封装到po类中的page去了分页page接收不到
		 */
		String page = getModel().getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		this.functionService.pageQuery(pageBean);
		String[] excludes = new String[]{"function","roles","functions","currentPage","pageSize","detachedCriteria"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	/**
	 * 添加权限显示父功能节点
	 * @return
	 * @throws IOException
	 */
	public String listAjax() throws IOException{
		List<Function> Allfunction = functionService.findAll();
		String[] excludes = new String[]{"function","code","description","page","generatemenu","zindex","roles","functions"};
		this.writeList2Json(Allfunction, excludes );
		return NONE;
	}
	/**
	 * 添加权限
	 */
	public String add(){
		
		Function function = getModel().getFunction();
		//如果没有父权限将function设置为null  null 和空字符串不一样
		if(function != null && function.getId().equals("")){
			getModel().setFunction(null);
		}
		this.functionService.saveOrUpdate(getModel());
		return "list";
	}
	
	/**
	 * 添加角色中  授权列表展现
	 * @throws IOException 
	 */
	public String listMenuAjax() throws IOException{
		List<Function> list = this.functionService.findAll();
		
		String[] excludes = new String[]{"function","code","description","generatemenu","zindex","roles","functions"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	/**
	 * 根据登陆人 查询对应的菜单数据（从权限表单中查）
	 * @throws IOException 
	 */
	public String findMenu() throws IOException{
		List<Function> list = functionService.findMenu();
		String[] excludes = new String[]{"roles","function","functions","description"};
		this.writeList2Json(list, excludes );
		return NONE;
	}
}
