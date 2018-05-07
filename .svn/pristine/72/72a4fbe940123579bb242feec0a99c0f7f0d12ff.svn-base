package com.cb.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.cb.bos.crm.CustomerService;
import com.cb.bos.service.ISubAreaService;
import com.cb.bos.service.IWorkbillService;
import com.cb.bos.service.IWorkordermanageService;
import com.cb.bos.service.IFunctionService;
import com.cb.bos.service.INoticebillService;
import com.cb.bos.service.IRoleService;
import com.cb.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	@Autowired
	protected ISubAreaService subareaService;
	@Autowired
	protected CustomerService customerService;
	@Autowired
	protected INoticebillService noticebillService;
	@Autowired
	protected IWorkordermanageService workordermanageService;
	@Autowired
	protected IFunctionService functionService;
	@Autowired
	protected IRoleService roleService;
	@Autowired
	protected IWorkbillService workbillService;
	//模型对象
	private T model;
	@Override
	public T getModel() {
		
		return model;
	}
	//实例化model  在构造方法中动态获得实现类型，通过反射创建模型对象
	
	protected DetachedCriteria detachedCriteria = null;
	
	public BaseAction() {
	
		//获得运行时的class
		ParameterizedType parameterizedType = null;
		if(this.getClass().getGenericSuperclass() instanceof ParameterizedType){
			parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		}else{//当前为Action创建代理对象 则需要再往上找一层
			parameterizedType = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
		}
		//获得实体类型
		Class<T> Tclass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		//重构分页代码   
		detachedCriteria = DetachedCriteria.forClass(Tclass);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		try {
		//通过反射创建对象
		model = Tclass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页查询
	 */
	protected PageBean pageBean = new PageBean();
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	
	public void writePageBean2Json(PageBean pageBean, String[] excludes)
			throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
	
	public void writeList2Json(List list, String[] excludes)
			throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		String json = jsonArray.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
	public void writeObjectJson(Object object, String[] excludes)
			throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
	
	
	
	
	
}
