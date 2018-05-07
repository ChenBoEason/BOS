package com.cb.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.crm.CustomerService;
import com.cb.bos.domain.Decidedzone;
import com.cb.bos.service.IDecidedzoneService;
import com.cb.bos.web.action.base.BaseAction;

import cn.itcast.crm.domain.Customer;
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{

	@Autowired
	private IDecidedzoneService decidezoneService;
	@Resource
	private CustomerService customerService;
	/**
	 * 添加定区
	 */
	private String[] subareaid;
	public String addDecidedzone(){
		
		decidezoneService.save(getModel(),subareaid);
		return "list";
	}
	
	/**
	 * 数据分页
	 * @return
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		decidezoneService.pageQuery(pageBean);
		//有关联的类 并且需要使用时 一定要把懒加载 lazy关了
		String[] excludes = new String[]{"subareas","decidedzones"};
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}
	
	/**
	 * hessian远程调用未关联的客户信息
	 * @param subareaid
	 * @throws IOException 
	 */
	public String findnoassociationCustomers() throws IOException{
		List<Customer> list = customerService.findnoassociationCustomers();
		String[] excludes = new String[]{};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	/**
	 * 调用关联客户信息
	 * @param subareaid
	 * @throws IOException 
	 */
	
	public String findhasassociationCustomers() throws IOException{
		
		List<Customer> list = customerService.findhasassociationCustomers(this.getModel().getId());
		
		String[] excludes = new String[]{};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	/**
	 * 关联定区客户
	 */
	private Integer[] customerIds;
	public String assigncustomerstodecidedzone(){
		customerService.assignCustomersToDecidedZone(customerIds, getModel().getId());
		
		return "list";
	}
	
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	
}
