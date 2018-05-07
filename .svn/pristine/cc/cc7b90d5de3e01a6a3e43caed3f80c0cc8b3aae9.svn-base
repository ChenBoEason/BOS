package com.cb.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.domain.Staff;
import com.cb.bos.service.IStaffService;
import com.cb.bos.utils.PageBean;
import com.cb.bos.web.action.base.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 
*<p>Title:StaffAction</p>	
*<p>Description:派取员</p>	
*@author Eason
*@Time 2017年8月17日下午3:38:53
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	private static final long serialVersionUID = 1L;
	
	//注入service
	@Autowired
	private IStaffService staffService;
	/**
	 * 添加派取员
	 * @return
	 */
	public String add(){
		staffService.save(this.getModel());
		return "list";
	}
	/**
	 * 分页查询
	 */
	private int page;//当前页码
	private int rows;//每页的记录数
	public String pageQuery() throws IOException{
		
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
//-----------------------------设置条件-----------------------------------------------------
		
			String name = getModel().getName();
			String tel = getModel().getTelephone();
			
			if(StringUtils.isNotBlank(name)){
				
				detachedCriteria.add(Restrictions.like("name", "%"+name+"%"));
			}
			if(StringUtils.isNotBlank(tel)){
				detachedCriteria.add(Restrictions.like("telephone", "%"+tel+"%"));
			}
			
		
		
		pageBean.setDetachedCriteria(detachedCriteria);
//----------------------------------------------------------------------------------		
		staffService.pageQuery(pageBean);
		//将pagebean转化为json数据  bean对象用object  如果是数组之类的用Array
		//如果想把多余的用不到的参数删除 不返回页面 用该方法
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"currentPage","pageSize","detachedCriteria","decidedzones"});
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig); 
		
//		JSONObject jsonObject = JSONObject.fromObject(pageBean);
		String json = jsonObject.toString();
		
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);;
		return NONE;
	}
	/**
	 * 批量删除取派员信息
	 * @return
	 */
	private String ids;
	public String delete(){
		staffService.deleteById(ids);
		return "list";
	}
	
	public String edit(){
		this.staffService.update(this.getModel());
		return "list";
	}
//	513 824 1996 0529 5728   13708 160849
	/**
	 * 查询没有作废的取派员 通过json数据返回
	 * @return
	 * @throws IOException 
	 */
	public String listAjax() throws IOException{
		List<Staff> list = staffService.findByNotDelete();
		//可以消除来只剩ID 和name
		String[] excludes = new String[]{"decidedzones","standard"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	/**
	 * 还原作废的操作员
	 * @return
	 */
	public String restore(){
		staffService.restore(ids);
		return "list";
	}
	

	public void setIds(String ids) {
		this.ids = ids;
	} 
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
}
