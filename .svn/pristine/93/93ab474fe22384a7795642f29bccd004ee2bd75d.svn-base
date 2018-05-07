package com.cb.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.dao.IFunctionDao;
import com.cb.bos.domain.Function;
import com.cb.bos.domain.User;
import com.cb.bos.service.IFunctionService;
import com.cb.bos.utils.BosLoginUserSession;
import com.cb.bos.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService{

	@Resource
	private IFunctionDao functionDao;

	@Override
	public void pageQuery(PageBean pageBean) {
		this.functionDao.pageQuery(pageBean);
	}

	@Override
	public List<Function> findAll() {
		
		return this.functionDao.findAll();
	}

	@Override
	public void saveOrUpdate(Function function) {
		this.functionDao.save(function);
	}
	/**
	 * 查询菜单
	 */
	@Override
	public List<Function> findMenu() {
		//先判断用户是否为超级管理员
		User user = BosLoginUserSession.getLoginUser();
		//存放所有菜单列表
		List<Function> list = null;
		if(user.getUsername().equals("eason")){
			//查询所有的生成目录
			list = functionDao.findAllMenu();
		}else{
			//根据用户不同查询用户功能权限
			list = functionDao.findMenuByUserId(user.getId());
		}
		return list;
	}
}
