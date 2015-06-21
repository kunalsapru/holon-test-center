package com.htc.service;

import com.htc.dao.HolonCoordinatorDao;
import com.htc.dao.HolonDao;
import com.htc.dao.HolonElementDao;
import com.htc.dao.HolonElementStateDao;
import com.htc.dao.HolonElementTypeDao;
import com.htc.dao.HolonManagerDao;
import com.htc.dao.HolonObjectDao;
import com.htc.dao.HolonObjectTypeDao;
import com.htc.dao.LatLngDao;
import com.htc.daoImpl.HolonCoordinatorDaoImpl;
import com.htc.daoImpl.HolonDaoImpl;
import com.htc.daoImpl.HolonElementDaoImpl;
import com.htc.daoImpl.HolonElementStateDaoImpl;
import com.htc.daoImpl.HolonElementTypeDaoImpl;
import com.htc.daoImpl.HolonManagerDaoImpl;
import com.htc.daoImpl.HolonObjectDaoImpl;
import com.htc.daoImpl.HolonObjectTypeDaoImpl;
import com.htc.daoImpl.LatLngDaoImpl;

/**
 * The Class AbstractService.
 * 
 */
public class AbstractService {
	
	private HolonDao holonDao = new HolonDaoImpl();
	private HolonCoordinatorDao holonCoordinatorDao = new HolonCoordinatorDaoImpl();
	private HolonElementDao holonElementDao = new HolonElementDaoImpl();
	private HolonElementStateDao holonElementStateDao = new HolonElementStateDaoImpl() ;
	private HolonElementTypeDao holonElementTypeDao = new HolonElementTypeDaoImpl() ;
	private HolonManagerDao holonManagerDao = new HolonManagerDaoImpl();
	private HolonObjectDao holonObjectDao = new HolonObjectDaoImpl();
	private HolonObjectTypeDao holonObjectTypeDao = new HolonObjectTypeDaoImpl() ;
	private LatLngDao latLngDao = new LatLngDaoImpl();

	public HolonDao getHolonDao() {
		return holonDao;
	}
	public void setHolonDao(HolonDao holonDao) {
		this.holonDao = holonDao;
	}
	public HolonCoordinatorDao getHolonCoordinatorDao() {
		return holonCoordinatorDao;
	}
	public void setHolonCoordinatorDao(HolonCoordinatorDao holonCoordinatorDao) {
		this.holonCoordinatorDao = holonCoordinatorDao;
	}
	public HolonElementDao getHolonElementDao() {
		return holonElementDao;
	}
	public void setHolonElementDao(HolonElementDao holonElementDao) {
		this.holonElementDao = holonElementDao;
	}
	public HolonElementStateDao getHolonElementStateDao() {
		return holonElementStateDao;
	}
	public void setHolonElementStateDao(HolonElementStateDao holonElementStateDao) {
		this.holonElementStateDao = holonElementStateDao;
	}
	public HolonElementTypeDao getHolonElementTypeDao() {
		return holonElementTypeDao;
	}
	public void setHolonElementTypeDao(HolonElementTypeDao holonElementTypeDao) {
		this.holonElementTypeDao = holonElementTypeDao;
	}
	public HolonManagerDao getHolonManagerDao() {
		return holonManagerDao;
	}
	public void setHolonManagerDao(HolonManagerDao holonManagerDao) {
		this.holonManagerDao = holonManagerDao;
	}
	public HolonObjectDao getHolonObjectDao() {
		return holonObjectDao;
	}
	public void setHolonObjectDao(HolonObjectDao holonObjectDao) {
		this.holonObjectDao = holonObjectDao;
	}
	public HolonObjectTypeDao getHolonObjectTypeDao() {
		return holonObjectTypeDao;
	}
	public void setHolonObjectTypeDao(HolonObjectTypeDao holonObjectTypeDao) {
		this.holonObjectTypeDao = holonObjectTypeDao;
	}
	public LatLngDao getLatLngDao() {
		return latLngDao;
	}
	public void setLatLngDao(LatLngDao latLngDao) {
		this.latLngDao = latLngDao;
	}
	
}