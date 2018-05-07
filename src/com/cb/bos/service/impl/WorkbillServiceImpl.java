package com.cb.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cb.bos.dao.IWorkbillDao;
import com.cb.bos.service.IWorkbillService;

@Service
@Transactional
public class WorkbillServiceImpl implements IWorkbillService {
	
	@Resource
	private IWorkbillDao workbillDao;
}
