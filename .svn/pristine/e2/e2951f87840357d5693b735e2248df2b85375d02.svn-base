package com.cb.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.dao.IDecidedzoneDao;
import com.cb.bos.dao.ISubareaDao;
import com.cb.bos.domain.Decidedzone;
import com.cb.bos.domain.Subarea;
import com.cb.bos.service.IDecidedzoneService;
import com.cb.bos.utils.PageBean;
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {

	@Resource
	private IDecidedzoneDao decidedzoneDao;
	@Resource
	private ISubareaDao subareaDao;

	/**
	 * 添加定区
	 */
	@Override
	public void save(Decidedzone model, String[] subareaid) {
		this.decidedzoneDao.save(model);
		
		for (String subareaId : subareaid) {
			Subarea subarea = this.subareaDao.findById(subareaId);
			subarea.setDecidedzone(model);
		}
	}

	/**
	 * 数据分页
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		this.decidedzoneDao.pageQuery(pageBean);
	}
}
