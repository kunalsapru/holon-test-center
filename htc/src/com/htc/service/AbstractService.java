package com.htc.service;

import com.htc.dao.HolonElementTypeDao;
import com.htc.daoImpl.HolonElementTypeDaoImpl;


/**
 * The Class AbstractService.
 * 
 */
public class AbstractService {
	
	private HolonElementTypeDao holonElementTypeDao = new HolonElementTypeDaoImpl() ;

	public HolonElementTypeDao getHolonElementTypeDao() {
		return holonElementTypeDao;
	}

	public void setHolonElementTypeDao(HolonElementTypeDao holonElementTypeDao) {
		this.holonElementTypeDao = holonElementTypeDao;
	}



}