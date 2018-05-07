package com.cb.bos.web.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cb.bos.domain.Workordermanage;
import com.cb.bos.service.IWorkordermanageService;
import com.cb.bos.utils.BosLoginUserSession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
*<p>Title:TaskAction</p>	
*<p>Description:组任务操作</p>	
*@author Eason
*@Time 2017年9月5日下午2:57:37
 */
@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IWorkordermanageService workordermanageService;
	/**
	 * 查看我的组任务
	 * @return
	 */
	public String findGroupTask(){
		//查询组任务
		TaskQuery query = taskService.createTaskQuery();
		//得到当前用户id
		String id = BosLoginUserSession.getLoginUser().getId();
		//通过当前id查询是否有组任务
		query.taskCandidateUser(id);
		System.out.println("执行了"+"  "+id+" "+query.taskCandidateUser(id));
		List<Task> list = query.list();
		System.out.println(list.size());
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	private String taskId;
	/**
	 * 查询业务数据 根据任务id
	 * @return
	 * @throws IOException 
	 */
	public String showData() throws IOException{
		Map<String, Object> variables = taskService.getVariables(taskId);
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(variables.toString());
		return NONE;
	}
	/**
	 * 拾取任务 通过传入任务ID 和  用户id
	 * @return
	 */
	public String takeTask(){
		
		String id = BosLoginUserSession.getLoginUser().getId();
		taskService.claim(taskId, id);
		return "toList";
	}
	
	/**
	 * 查询个人任务 202cb962ac59075b964b07152d234b70
	 * @return
	 */
	public String findPersonalTask(){
		TaskQuery query = taskService.createTaskQuery();
		//获取当前用户id
		String id = BosLoginUserSession.getLoginUser().getId();
		//通过id过滤任务
		query.taskAssignee(id);
		//获取当前用户的个人任务
		List<Task> list = query.list();
		System.out.println("个人任务"+list.size());
		ActionContext.getContext().getValueStack().set("list", list);
		return "listpersonTask";
	}
	
	private Integer check;
	/**
	 * 办理审核工作单任务
	 * 
	 */
	public String checkWorkOrderManage(){
		//通过任务id查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//2201 transfer:1:2004 System.out.println(task.getProcessInstanceId()+" "+task.getProcessDefinitionId());
		//根据任务对象得到流程id
		String processInstanceId = task.getProcessInstanceId();
		//根据流程id查询实例对象
		ProcessInstance processInstance = runtimeService.
				createProcessInstanceQuery().
				processInstanceId(processInstanceId).
				singleResult();
		//通过实例对象得到工作单主键
		String workorderMangerId = processInstance.getBusinessKey();
		Workordermanage workordermanage = workordermanageService.findById(workorderMangerId);
		if(check == null){
			//check为空说明该页面为列表页面  需要跳转到审核页面
			ActionContext.getContext().getValueStack().set("map", workordermanage);
			return "toCheck";
		}else{
			workordermanageService.checkWorkorderManger(taskId,check,workorderMangerId);
			return "toPersonList";
		}
		
	}
	
	/**
	 * 办理出库任务
	 * @return
	 */
	public String outStore(){
		taskService.complete(taskId);
		return "toPersonList";
	}
	/**
	 * 办理配送任务
	 * @return
	 */
	public String transferGoods(){
		taskService.complete(taskId);
		return "toPersonList";
	}
	/**
	 * 办理签收任务
	 * @return
	 */
	public String receive(){
		taskService.complete(taskId);
		return "toPersonList";
	}
	
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}
	public Integer getCheck() {
		return check;
	}
}
