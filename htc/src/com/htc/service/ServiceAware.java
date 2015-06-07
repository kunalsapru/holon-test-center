package com.htc.service;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.htc.serviceImpl.CreateHolonsServiceImpl;
/**
 * The Class ServiceAware.
 * 
 */
public abstract class ServiceAware extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(ServiceAware.class);
	private CreateHolonsService createHolonsService=new CreateHolonsServiceImpl();

	public CreateHolonsService getCreateHolonsService() {
		return createHolonsService;
	}

	public void setCreateHolonsService(CreateHolonsService createHolonsService) {
		this.createHolonsService = createHolonsService;
	}

}
