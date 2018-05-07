package com.cb.bos.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.dao.IWorkordermanageDao;
import com.cb.bos.domain.Workordermanage;
import com.cb.bos.service.IWorkordermanageService;

@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService{

	@Resource
	private IWorkordermanageDao workordermanageDao;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private HistoryService historyService;
	
	@Override
	public void save(Workordermanage model) {
		model.setUpdatetime(new Date());
		workordermanageDao.save(model);
	}

	/**
	 * 查询工作单为0的数据
	 */
	@Override
	public List<Workordermanage> findBystart() {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Workordermanage.class);
		detachedCriteria.add(Restrictions.eq("start", "0"));
		return this.workordermanageDao.findByCriteria(detachedCriteria );
	}

	/**
	 * 启动流程实例，设置流程变量，修改工作单中的start为1
	 */
	@Override
	public void start(String id) {
		Workordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1"); //已启动
		
		String processDefinitionKey = "transfer"; //流程定义key
		String businessKey = id;//业务主键-----等于业务表（工作单主键值）---让工作流框架找到业务数据
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("业务数据", workordermanage);
		System.out.println("执行了；");
		runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
	}

	@Override
	public Workordermanage findById(String workorderMangerId) {
		
		return this.workordermanageDao.findById(workorderMangerId);
	}

	@Override
	public void checkWorkorderManger(String taskId, Integer check, String workorderMangerId) {
		//如果审核不通过修改工作单start为0  重新回到启动流程配送页面 
		Workordermanage workordermanage = workordermanageDao.findById(workorderMangerId);
		//根据任务id查询任务实例
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		//根据任务对象查询流程id
		String processInstanceId = task.getProcessInstanceId();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("check", check);
		//办理审核工作任务
		taskService.complete(taskId, map);
		if(check == 0){
			//审核不通过
			workordermanage.setStart("0");
			//删除历史流程数据
			historyService.deleteHistoricProcessInstance(processInstanceId);
		}
	}
}
