package com.cb.bos.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
*<p>Title:ProcessInstanceAction</p>	
*<p>Description:流程实例管理</p>	
*@author Eason
*@Time 2017年9月3日下午9:31:13
 */
@Controller
@Scope("prototype")
public class ProcessInstanceAction extends ActionSupport{
	
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	/**
	 * 运行时流程图
	 */
	public String list(){
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		query.orderByProcessInstanceId().desc(); //降序
		List<ProcessInstance> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	/**
	 * 根据流程实例id 查询对应流程变量数据
	 * @return
	 */
	private String id;
	public String findData() throws IOException{
		Map<String, Object> variables = runtimeService.getVariables(id);
		//用输出流来放到前端页面  并且进行编码设置 以防乱码
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(variables.toString());
		return NONE;
	}
	/**
	 * 根据流程实例id查询坐标， 部署id、图片名称
	 * @return
	 */
	public String showPng(){
		//1. 根据流程实例id查询流程实例对象
		ProcessInstance singleResult = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
		//2.根据流程实例对象查询流程定义id
		String processDefinitionId = singleResult.getProcessDefinitionId();
		//3. 根据流程定义id查询流程定义对象
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		//4.根据流程定义对象查询部署id 和图片名称
		deploymentId = processDefinition.getDeploymentId();
		imageName = processDefinition.getDiagramResourceName();
		/**
		 * 这样使用不利于下一个方法viewImage()获取数据
		 * 我们将deploymentId imageName设置为成员变量 可以封装可以提取
		 * ActionContext.getContext().getValueStack().set("deploymentId", deploymentId);
		ActionContext.getContext().getValueStack().set("imageName", pngName);
		 */
		//5.查询坐标
		//5.1 获得当前流程实例执行到哪一个节点
		String activityId = singleResult.getActivityId();
		//5.2 加载bpmn(xml) 文件，获得一个流程定义对象
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//5.3根据activityId 获得含有坐标信息的对象
		ActivityImpl findActivity = pd.findActivity(activityId);
		x = findActivity.getX();
		y = findActivity.getY();
		width = findActivity.getWidth();
		height = findActivity.getHeight();
		return "showPng";
	}
	
	
	/**
	 * 流程实例图展现时查询所用的属性名称  和坐标名称
	 */
	private String deploymentId;
	private String imageName;
	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;
	/**
	 * 
	 * @return
	 */
	public String viewImage(){
		InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("pngStream", inputStream);
		return "viewImage";
	}
	
	
	
	
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
	
	public void setId(String id) {
		this.id = id;
	}
}
