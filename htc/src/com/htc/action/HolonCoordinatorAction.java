package com.htc.action;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.utilities.CommonUtilities;
import com.htc.utilities.ConstantValues;

public class HolonCoordinatorAction extends CommonUtilities{

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonCoordinatorAction.class);

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


	public  void chooseCoordinator(Integer holonCoordinatorId) {
		holonCoordinatorId = holonCoordinatorId !=null ? holonCoordinatorId : 1; 
		log.info("Abhinav is fool "+holonCoordinatorId);
		HolonCoordinator hCoordinator= getHolonCoordinatorService().findById(holonCoordinatorId);
		
		ArrayList<HolonObject> hoList= getHolonObjectService().findByHCoordinator(hCoordinator);
		if(hoList != null){
		log.info("The length of Holon List for this co ordinator is "+hoList.size());
		}
		HolonObject fHoObj =null;
		if(hoList!=null){
			if(hoList.size()==0)
			{
				//To do write code to handle this case.
				//fHoObj= new HolonObject(hCoordinator); 
			}
			else if(hoList.size()==1)
			{
			log.info("There is only one holon Object for this coordinator");	
			fHoObj = hoList.get(0);
			
			}else if(hoList.size()>1)
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
			}
	}
		if(fHoObj != null) {
			hCoordinator.setHolonObject(fHoObj);
			getHolonCoordinatorService().merge(hCoordinator);		
			log.info("Id of Holon Set as coordinator is "+hCoordinator.getHolonObject().getId());				
		}
	}
	
	
	public void updateCoordinator()
	{
		try {
			
			chooseCoordinatorValue();						
			String response = "true";
			
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(response);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void getHoCoIcons()
	{

		try {
			HolonObject hoCoObBlue=getHolonCoordinatorService().findById(ConstantValues.HOLON_CO_BLUE).getHolonObject();
			HolonObject hoCoObRed=getHolonCoordinatorService().findById(ConstantValues.HOLON_CO_RED).getHolonObject();
			HolonObject hoCoObYellow=getHolonCoordinatorService().findById(ConstantValues.HOLON_CO_YELLOW).getHolonObject();
			HolonObject hoCoObGreen=getHolonCoordinatorService().findById(ConstantValues.HOLON_CO_GREEN).getHolonObject();
			Integer noOfBlue=getHolonObjectService().findByHCoordinator(getHolonCoordinatorService().findById(ConstantValues.HOLON_CO_BLUE)).size();
			Integer noOfGreen=getHolonObjectService().findByHCoordinator(getHolonCoordinatorService().findById(ConstantValues.HOLON_CO_GREEN)).size();
			Integer noOfYellow=getHolonObjectService().findByHCoordinator(getHolonCoordinatorService().findById(ConstantValues.HOLON_CO_YELLOW)).size();
			Integer noOfRed=getHolonObjectService().findByHCoordinator(getHolonCoordinatorService().findById(ConstantValues.HOLON_CO_RED)).size();			
			Integer hoCoObIdBlue=0;
			Integer hoCoObIdGreen=0;
			Integer hoCoObIdYellow=0;
			Integer hoCoObIdRed=0;
			if(hoCoObBlue!=null && noOfBlue!=0)
			{
				hoCoObIdBlue=hoCoObBlue.getId();
			}
			if(hoCoObRed!=null && noOfRed!=0)
			{
				hoCoObIdRed=hoCoObRed.getId();
			}
			if(hoCoObYellow!=null && noOfYellow!=0)
			{
				hoCoObIdYellow=hoCoObYellow.getId();
			}
			if(hoCoObGreen!=null && noOfGreen!=0)
			{
				hoCoObIdGreen=hoCoObGreen.getId();
			}
			
			String response = hoCoObIdBlue+"*"+hoCoObIdGreen+"*"+hoCoObIdYellow+"*"+hoCoObIdRed;
			
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(response);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	
		
	}


	public void chooseCoordinatorValue() {
		chooseCoordinator(ConstantValues.HOLON_CO_BLUE);
		chooseCoordinator(ConstantValues.HOLON_CO_GREEN);
		chooseCoordinator(ConstantValues.HOLON_CO_YELLOW);
		chooseCoordinator(ConstantValues.HOLON_CO_RED);
		
	}

}
