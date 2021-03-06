package com.cb.bos.web.action;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.crm.CustomerService;
import com.cb.bos.domain.Noticebill;
import com.cb.bos.domain.User;
import com.cb.bos.utils.BosLoginUserSession;
import com.cb.bos.web.action.base.BaseAction;

import cn.itcast.crm.domain.Customer;
/**
 * 
*<p>Title:NoticebillAction</p>	
*<p>Description:业务受理</p>	
*@author Eason
*@Time 2017年8月28日下午7:46:44
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{

	
	/**
	 * 通过输入电话号码自动查询数据显示回页面
	 * @return
	 * @throws IOException
	 */
	public String findCustomerByTelephone() throws IOException{
		
		Customer customer = customerService.findCustomerByNumber(getModel().getTelephone());
		String[] excludes = new String[]{};
		this.writeObjectJson(customer, excludes);
		return NONE;
	}
	/**
	 * 保存业务新单， 尝试自动分单
	 */
	public String add(){
		User user = BosLoginUserSession.getLoginUser();
		
		getModel().setUser(user);
		noticebillService.save(getModel());
		return "addUI";
	}
}
