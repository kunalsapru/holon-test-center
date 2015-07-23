package com.htc.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.utilities.CommonUtilities;

public class HolonCoordinatorAction extends CommonUtilities{

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonCoordinatorAction.class);

	
	public void createHolonCoordinator(){
		try{
		Double latNE = getRequest().getParameter("latNE")!=null?Double.parseDouble(getRequest().getParameter("latNE")):0D;
		Double lngNE = getRequest().getParameter("lngNE")!=null?Double.parseDouble(getRequest().getParameter("lngNE")):0D;
		//Double latSW = getRequest().getParameter("latSW")!=null?Double.parseDouble(getRequest().getParameter("latSW")):0D;
		//Double lngSW = getRequest().getParameter("lngSW")!=null?Double.parseDouble(getRequest().getParameter("lngSW")):0D;
		Integer coordinatorOfHolon = getRequest().getParameter("coordinatorHolon")!=null?Integer.parseInt(getRequest().getParameter("coordinatorHolon")):0;
		String nameCoordinator= getRequest().getParameter("nameCoordinator")!=null?getRequest().getParameter("nameCoordinator"):"";
		LatLng NeLatLng = new LatLng(latNE, lngNE);
		//LatLng SeLatLng = new LatLng(latNE, lngNE);
		Integer NeLocationId = getLatLngService().persist(NeLatLng);
		//Integer SeLocationId = getLatLngService().persist(SeLatLng);
		@SuppressWarnings("unused")
		LatLng NeLatLng2 = getLatLngService().findById(NeLocationId);
		//LatLng SeLatLng2 = getLatLngService().findById(SeLocationId);
		Holon holon = new Holon(coordinatorOfHolon);
		HolonCoordinator holonCoordinator = new HolonCoordinator();
		holonCoordinator.setName(nameCoordinator);
		holonCoordinator.setHolon(holon);
		Integer newHolonCoordinatortID = getHolonCoordinatorService().persist(holonCoordinator);
		getResponse().setContentType("text/html");
		getResponse().getWriter().write(newHolonCoordinatortID);
		}
		catch(Exception e){
			log.debug("Exception "+e.getMessage()+" occurred in action createHolonObject()");
			e.printStackTrace();
		}

	}

	
	public void editHolonCoordinator(){}
	
	
	public void deleteHolonCoordinator(){}
	
	
	public void getListHolonCoordinator(){

		 
		ArrayList<HolonCoordinator> holonCoordinators = getHolonCoordinatorService().getAllHolonCoordinator();
		StringBuffer holonCoordinatorList = new StringBuffer();
		
		for(HolonCoordinator holonCoordinator:holonCoordinators){
			holonCoordinatorList.append(holonCoordinator.getId()+"-"+holonCoordinator.getName()+"  "+holonCoordinator.getHolon().getName()+"*");
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


	public synchronized void chooseCoordinator(Integer holonCoordinatorId) {
		log.info("Abhinav is fool "+holonCoordinatorId);
		HolonCoordinator hCoordinator= getHolonCoordinatorService().findById(holonCoordinatorId);
		
		ArrayList<HolonObject> hoList= getHolonObjectService().findByHCoordinator(hCoordinator);
		if(hoList != null){
		log.info("The length of Holon List for this co ordinator is "+hoList.size());
		}
		HolonObject fHoObj =null;
		if(hoList!=null && hoList.size()==1)
		{
			log.info("There is only one holon Object for this coordinator");	
			fHoObj = hoList.get(0);
			
		}else if(hoList!=null && hoList.size()>1)
		{
			fHoObj=hoList.get(0);
			log.info("There are more than one holon Object for this coordinator");			
			for (int i=1;i<hoList.size();i++) {			   
			    	int compareResult= hoList.get(i).compareTo(fHoObj);
			    	if(compareResult==1)
			    	{
			    		log.info("setting id as coordinator "+hCoordinator.getHolonObject().getId());
			    		fHoObj=hoList.get(i);			    		
			    	}
			 	}
			log.info("Id of Holon Set as coordinator is "+hCoordinator.getHolonObject().getId());				
		}
		if(fHoObj != null)
		{
		hCoordinator.setHolonObject(fHoObj);
		getHolonCoordinatorService().merge(hCoordinator);		
		}
	}
	

}
