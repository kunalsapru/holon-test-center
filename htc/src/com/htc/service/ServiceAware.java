package com.htc.service;

import com.opensymphony.xwork2.ActionSupport;
import com.htc.serviceImpl.HolonElementTypeServiceImpl;

/**
 * The Class ServiceAware.
 * 
 */
public abstract class ServiceAware extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private HolonElementTypeService holonElementTypeService = new HolonElementTypeServiceImpl();

	public HolonElementTypeService getHolonElementTypeService() {
		return holonElementTypeService;
	}

	public void setHolonElementTypeService(
			HolonElementTypeService holonElementTypeService) {
		this.holonElementTypeService = holonElementTypeService;
	}

}
