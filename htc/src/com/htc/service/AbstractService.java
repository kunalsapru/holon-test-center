package com.htc.service;

import com.htc.dao.CreateHolonsDao;
import com.htc.daoImpl.CreateHolonsDaoImpl;


/**
 * The Class AbstractService.
 * 
 */
public class AbstractService {

	private CreateHolonsDao createHolonsDao=new CreateHolonsDaoImpl();

	public CreateHolonsDao getCreateHolonsDao() {
		return createHolonsDao;
	}

	public void setCreateHolonsDao(CreateHolonsDao createHolonsDao) {
		this.createHolonsDao = createHolonsDao;
	}
	
	
	

}