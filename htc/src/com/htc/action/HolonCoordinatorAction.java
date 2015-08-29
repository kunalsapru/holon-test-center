package com.htc.action;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.utilities.CommonUtilities;
import com.htc.utilities.ConstantValues;

public class HolonCoordinatorAction extends CommonUtilities{

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonCoordinatorAction.class);

	public  void chooseCoordinator(Integer holonId) {
/*		holonId = holonId !=null ? holonId : 1; 
		HolonObject hCoordinator =  getHolonObjectService().getHolonObjectService().findById(holonCoordinatorId);
		PowerLine powerLine = getPowerLineService().getPowerLineByHolonObject(hCoordinator);
		ArrayList<HolonObject> hoList= getHolonObjectListByConnectedPowerLines(powerLine, hCoordinator);
		if(hoList != null) {
			log.info("The length of Holon List for this co ordinator is "+hoList.size());
		}
		HolonObject fHoObj =null;
		if(hoList!=null) {
			if(hoList.size()==0) {
				//To do write code to handle this case.
				//fHoObj= new HolonObject(hCoordinator); 
			} else if(hoList.size()==1) {
				log.info("There is only one holon Object for this coordinator");	
				fHoObj = hoList.get(0);
			} else if(hoList.size()>1) {
				fHoObj=hoList.get(0);
				log.info("There are more than one holon Object for this coordinator");			
				for (int i=1;i<hoList.size();i++) {			   
			    	int compareResult= hoList.get(i).compareTo(fHoObj);
			    	if(compareResult==1) {
			    		log.info("setting id as coordinator "+hCoordinator.getId());
			    		fHoObj=hoList.get(i);			    		
			    	}
			 	}
			}
	}
		if(fHoObj != null) {
			hCoordinator.setIsCoordinator(true);
			getHolonObjectService().merge(hCoordinator);		
			log.info("Id of Holon Object Set as coordinator is "+hCoordinator.getId());				
		} else {
			hCoordinator.setIsCoordinator(false);
			getHolonObjectService().merge(hCoordinator);
		}
*/	}
	
	
	public void updateCoordinator() {
		try {
			chooseCoordinatorValue();						
			String response = "true";
			
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getHoCoIcons() {
		try {
			ArrayList<HolonObject> holonCoordinatorList = getHolonObjectService().findAllHolonCoordinators();
			StringBuffer response = new StringBuffer();
			for(HolonObject holonCordinator : holonCoordinatorList) {
				response.append(holonCordinator.getId()+"~"+holonCordinator.getHolon().getColor()+"*");
			}
			if(response.length() > 0) {
				response = response.deleteCharAt(response.lastIndexOf("*"));
			}
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(response.toString());
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
