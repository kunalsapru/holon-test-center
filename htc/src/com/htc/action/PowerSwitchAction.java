package com.htc.action;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.utilities.CommonUtilities;


public class PowerSwitchAction extends CommonUtilities {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(PowerSwitchAction.class);

//	public void SwitchOn(HolonObject holonObject, PowerSwitch powerSwitch){
//		if(powerSwitch.getBelongsTo() == holonObject.getId()){
//			powerSwitch.setStatus(true);
//		}
//	}
//	
//	public void SwitchOff(HolonObject holonObject, PowerSwitch powerSwitch){
//		if(powerSwitch.getBelongsTo() == holonObject.getId()){
//			powerSwitch.setStatus(false);
//		}
//	}
	
	public void addPowerSwitchForHolonObject(HolonObject holonObject){
		PowerSwitch powerSwitch=new PowerSwitch();
		powerSwitch.setHolonObject(holonObject);
		powerSwitch.setPowerLine(null);
		powerSwitch.setLatLng(holonObject.getLatLngByDoorLocation());
		powerSwitch.setStatus(true);
		
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newPowerSwitchID = getPowerSwitchService().persist(powerSwitch);
		System.out.println("NewLy Generated PowerSwitch  ID --> "+newPowerSwitchID);

	}
	
	public void createPowerSwitch(){
		
	try{
		
		PowerSwitch powerSwitch= new PowerSwitch();
		log.info("Power Line Id "+getRequest().getParameter("powerLineId"));
		Integer powerLineId= getRequest().getParameter("powerLineId")!=null?Integer.parseInt(getRequest().getParameter("powerLineId")):0;
		Double latSwitch = getRequest().getParameter("switchPositionLat")!=null?Double.parseDouble(getRequest().getParameter("switchPositionLat")):0D;
		Double lngSwitch = getRequest().getParameter("switchPositionLng")!=null?Double.parseDouble(getRequest().getParameter("switchPositionLng")):0D;
		LatLng switchLatLng = new LatLng(latSwitch, lngSwitch);
		Integer switchlocationId = getLatLngService().persist(switchLatLng);
		LatLng switchLatLng2 = getLatLngService().findById(switchlocationId);
		PowerLine powerLine = new PowerLine(powerLineId);
		powerSwitch.setLatLng(switchLatLng2);
		powerSwitch.setPowerLine(powerLine);
		powerSwitch.setStatus(true);
		Integer newPowerSwitchId= getPowerSwitchService().persist(powerSwitch);
		System.out.println("---------------------->>>"+newPowerSwitchId);
		getResponse().setContentType("text/html");
		getResponse().getWriter().write(newPowerSwitchId.toString());
		
		
	}
	catch(Exception e){
		System.out.println("Exception is::"+ e);
	}
			
	}
	
	public void getListPowerSwitch(){
		try{
			ArrayList<PowerSwitch> powerSwitchList = getPowerSwitchService().getAllPowerSwitch();
			ArrayList<String> swListArray = new ArrayList<String>();
			PowerSwitch powerSwitch= null;
			for(int i=0;i<powerSwitchList.size();i++)
			{
				powerSwitch=powerSwitchList.get(i);
				Double switchLatitude= powerSwitch.getLatLng().getLatitude();
				Double switchLongitude= powerSwitch.getLatLng().getLongitude();
				Integer switchId = powerSwitch.getId();
				Integer powerId = powerSwitch.getPowerLine().getId();
				swListArray.add(switchLatitude+"^"+switchLongitude+"^"+switchId+"^"+powerId+"*");
				
			}
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(swListArray.toString());
		}
		catch(Exception e){
			System.out.println("Exception in getListPowerSwitch");
		}
	}
}
