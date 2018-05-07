package com.cb.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.domain.Workordermanage;
import com.cb.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
*<p>Title:WorkordermanageAction</p>	
*<p>Description:工作单</p>	
*@author Eason
*@Time 2017年8月29日下午9:39:21
 */
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage>{

	/**
	 * 添加工作单
	 * @return
	 * @throws IOException
	 */
	public String add() throws IOException{
		String flag = "1";
		try {
			workordermanageService.save(getModel());
		} catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	/**
	 * 查询未启动的工作单 start = '0';
	 * 
	 */
	public String list(){
		List<Workordermanage> list = workordermanageService.findBystart();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	/**
	 * 启动配送流程
	 * @return
	 */
	public String start(){
		this.workordermanageService.start(getModel().getId());
		return "toList";
	}
}
