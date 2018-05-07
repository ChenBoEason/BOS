package com.cb.bos.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.crm.CustomerService;
import com.cb.bos.dao.IDecidedzoneDao;
import com.cb.bos.dao.INoticebillDao;
import com.cb.bos.dao.IWorkbillDao;
import com.cb.bos.domain.Decidedzone;
import com.cb.bos.domain.Noticebill;
import com.cb.bos.domain.Workbill;
import com.cb.bos.service.INoticebillService;


/**
 * 
*<p>Title:NoticebillServiceImpl</p>	
*<p>Description:业务受理</p>	
*@author Eason
*@Time 2017年8月29日上午9:51:21
 */
@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{
	@Resource
	private INoticebillDao noticebillDao;
	@Resource
	private CustomerService customerService;
	@Resource
	private IDecidedzoneDao decedidzoneDao;
	@Resource
	private IWorkbillDao workbillDao;
	
	@Override
	public void save(Noticebill model) {
		
		//获取派件地址
		String address = model.getPickaddress();
		
		//根据取件地址查询定区id---从crm服务查询 
		String DecidedzoneId = customerService.findDecidedzoneIdByAddress(address);
		
		if(DecidedzoneId != null){
			//查询到定区id，可以自动分单
			Decidedzone decidedzone = decedidzoneDao.findById(DecidedzoneId);
			System.out.println(decidedzone.getName());
			System.out.println(decidedzone.getStaff().getId());
			model.setStaff(decidedzone.getStaff());//业务通知单关联到匹配的取派员
			model.setOrdertype("自动");//分单类型
			
			
			//创建一个取派员的工单
			Workbill workbill = new Workbill();
			workbill.setNoticebill(model);  //工单关联业务通知表
			workbill.setType("新单");     //
			workbill.setPickstate("未取件");
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单创建的时间
			workbill.setAttachbilltimes(0); //追单次数
			workbill.setRemark(model.getRemark());
			workbill.setStaff(decidedzone.getStaff());  //工单关联取派员
			
			workbillDao.save(workbill);//保存工单
			
			//调用短信平台服务，给取派员发送短信
		}else{
			model.setOrdertype("人工");
		}
		noticebillDao.save(model);//持久对象
		
	}
}
