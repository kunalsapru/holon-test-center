package com.htc.service;

import com.opensymphony.xwork2.ActionSupport;
import com.htc.serviceImpl.HolonCoordinatorServiceImpl;
import com.htc.serviceImpl.HolonElementServiceImpl;
import com.htc.serviceImpl.HolonElementStateServiceImpl;
import com.htc.serviceImpl.HolonElementTypeServiceImpl;
import com.htc.serviceImpl.HolonManagerServiceImpl;
import com.htc.serviceImpl.HolonObjectServiceImpl;
import com.htc.serviceImpl.HolonObjectTypeServiceImpl;
import com.htc.serviceImpl.HolonServiceImpl;
import com.htc.serviceImpl.LatLngServiceImpl;

/**
 * The Class ServiceAware.
 * 
 */
public abstract class ServiceAware extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private HolonService holonService = new HolonServiceImpl();
	private HolonCoordinatorService holonCoordinatorService = new HolonCoordinatorServiceImpl();
	private HolonElementService holonElementService = new HolonElementServiceImpl();
	private HolonElementStateService holonElementStateService = new HolonElementStateServiceImpl();
	private HolonElementTypeService holonElementTypeService = new HolonElementTypeServiceImpl();
	private HolonManagerService holonManagerService = new HolonManagerServiceImpl();
	private HolonObjectService holonObjectService = new HolonObjectServiceImpl();
	private HolonObjectTypeService holonObjectTypeService = new HolonObjectTypeServiceImpl();
	private LatLngService latLngService = new LatLngServiceImpl();
	
	public HolonService getHolonService() {
		return holonService;
	}
	public void setHolonService(HolonService holonService) {
		this.holonService = holonService;
	}
	public HolonCoordinatorService getHolonCoordinatorService() {
		return holonCoordinatorService;
	}
	public void setHolonCoordinatorService(
			HolonCoordinatorService holonCoordinatorService) {
		this.holonCoordinatorService = holonCoordinatorService;
	}
	public HolonElementService getHolonElementService() {
		return holonElementService;
	}
	public void setHolonElementService(HolonElementService holonElementService) {
		this.holonElementService = holonElementService;
	}
	public HolonElementStateService getHolonElementStateService() {
		return holonElementStateService;
	}
	public void setHolonElementStateService(
			HolonElementStateService holonElementStateService) {
		this.holonElementStateService = holonElementStateService;
	}
	public HolonElementTypeService getHolonElementTypeService() {
		return holonElementTypeService;
	}
	public void setHolonElementTypeService(
			HolonElementTypeService holonElementTypeService) {
		this.holonElementTypeService = holonElementTypeService;
	}
	public HolonManagerService getHolonManagerService() {
		return holonManagerService;
	}
	public void setHolonManagerService(HolonManagerService holonManagerService) {
		this.holonManagerService = holonManagerService;
	}
	public HolonObjectService getHolonObjectService() {
		return holonObjectService;
	}
	public void setHolonObjectService(HolonObjectService holonObjectService) {
		this.holonObjectService = holonObjectService;
	}
	public HolonObjectTypeService getHolonObjectTypeService() {
		return holonObjectTypeService;
	}
	public void setHolonObjectTypeService(
			HolonObjectTypeService holonObjectTypeService) {
		this.holonObjectTypeService = holonObjectTypeService;
	}
	public LatLngService getLatLngService() {
		return latLngService;
	}
	public void setLatLngService(LatLngService latLngService) {
		this.latLngService = latLngService;
	}
	
}
