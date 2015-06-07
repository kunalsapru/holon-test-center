package com.htc.service;

import org.apache.log4j.Logger;

import com.htc.dao.CreateHolonsDao;
import com.htc.daoImpl.CreateHolonsDaoImpl;


/**
 * The Class AbstractService.
 * 
 */
public class AbstractService {
	static Logger log = Logger.getLogger(AbstractService.class);
	
	private CreateHolonsDao createHolonsDao=new CreateHolonsDaoImpl();

	public CreateHolonsDao getCreateHolonsDao() {
		return createHolonsDao;
	}

	public void setCreateHolonsDao(CreateHolonsDao createHolonsDao) {
		this.createHolonsDao = createHolonsDao;
	}
	
	
	

}