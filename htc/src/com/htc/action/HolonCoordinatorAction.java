package com.htc.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.utilities.CommonUtilities;

public class HolonCoordinatorAction extends CommonUtilities{

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonElementTypeAction.class);

	
	public void createHolonCoordinator(){}

	
	public void editHolonCoordinator(){}
	
	
	public void deleteHolonCoordinator(){}
	
	
	public void getListHolonCoordinator(){

		 
		ArrayList<HolonCoordinator> holonCoordinators = getHolonCoordinatorService().getAllHolonCoordinator();
		StringBuffer holonCoordinatorList = new StringBuffer();
		
		for(HolonCoordinator holonCoordinator:holonCoordinators){
			holonCoordinatorList.append(holonCoordinator.getId()+"-"+holonCoordinator.getName()+"  "+holonCoordinator.getHolon().getName()+"*");
			System.out.println(holonCoordinator.getHolon().getName());
		}
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		
		try {
			getResponse().getWriter().write(holonCoordinatorList.toString());
		} catch (Exception e) {
			System.out.println("Exception");
			log.debug("Exception "+e.getMessage()+" occurred in action getListHolonCoordinator()");
			e.printStackTrace();
		}
	}
	

}
