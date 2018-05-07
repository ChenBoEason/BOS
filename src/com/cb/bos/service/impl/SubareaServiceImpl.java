package com.cb.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.dao.ISubareaDao;
import com.cb.bos.domain.Subarea;
import com.cb.bos.service.ISubAreaService;
import com.cb.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements ISubAreaService {

	@Resource
	private ISubareaDao subareaDao;

	@Override
	public void saveSubarea(Subarea model) {
		this.subareaDao.saveOrUpdate(model);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		this.subareaDao.pageQuery(pageBean);
	}

	@Override
	public List<Subarea> findAll() {
		
		return this.subareaDao.findAll();
	}

	@Override
	public List<Subarea> findListNotAssociation() {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return this.subareaDao.findByCriteria(detachedCriteria );
	}



	
}
