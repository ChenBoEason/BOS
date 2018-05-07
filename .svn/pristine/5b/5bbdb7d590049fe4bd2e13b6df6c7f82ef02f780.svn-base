package com.cb.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
*<p>Title:ProcessDefinitionAction</p>	
*<p>Description:流程部署</p>	
*@author Eason
*@Time 2017年9月3日下午9:30:43
 */
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	@Autowired
	private RepositoryService repositoryService;
	/**
	 * 查询最新版本流程定义列表查询数据
	 */
	
	public String list(){
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		//查询最新版本数据
		query.latestVersion();
		query.orderByProcessDefinitionName().desc(); //排序
		List<ProcessDefinition> list = query.list(); //执行查询
		//压栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	/**
	 * 流程部署
	 */
	private File zipFile; 
	public String deploy() throws FileNotFoundException{
		
		DeploymentBuilder deployment = repositoryService.createDeployment();
		deployment.addZipInputStream(new ZipInputStream(new FileInputStream(zipFile)));
		deployment.deploy();
		return "toList";
	}
	/**
	 * 查看流程图
	 * viewPng
	 * 
	 */
	private String id;
	public String viewpng(){
		//得到图片
		InputStream stream = repositoryService.getProcessDiagram(id);
		ActionContext.getContext().getValueStack().set("pngStream", stream);
		return "viewpng";
	}
	/**
	 * 删除工作流
	 * DELETE ACTIVITY
	 */
	public String deleteActivity(){
		String deltag = "0";
		//根据流程编号查询部署id
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		//过滤查询数据  根据id过滤
		query.processDefinitionId(id);
		//得到指定数据
		ProcessDefinition singleResult = query.singleResult();
		//得到部署id
		String deploymentId = singleResult.getDeploymentId();
		try {
			//删除部署的数据
			repositoryService.deleteDeployment(deploymentId);
		} catch (Exception e) {
			//当前要删除的流程正在使用 则不能删除
			deltag = "1";
			ActionContext.getContext().getValueStack().set("deltag", deltag);
			ProcessDefinitionQuery query2 = 
					repositoryService.createProcessDefinitionQuery();
			query2.latestVersion();
			query2.orderByProcessDefinitionName().desc();
			List<ProcessDefinition> list = query2.list();
			ActionContext.getContext().getValueStack().set("list", list);
			return "list";
		}
		return "toList";
	}
	
	
	
	public void setId(String id) {
		this.id = id;
	}
	public void setZipFile(File zipFile) {
		this.zipFile = zipFile;
	}
}
