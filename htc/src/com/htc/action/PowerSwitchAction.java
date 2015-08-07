package com.htc.action;


import java.util.ArrayList;
import java.util.Map;

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
		powerSwitch.setPowerLineA(null);
		powerSwitch.setStatus(true);
		
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newPowerSwitchID = getPowerSwitchService().persist(powerSwitch);
		log.info("NewLy Generated PowerSwitch  ID --> "+newPowerSwitchID);

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
		
		Map<String, PowerLine> powerLineMap = new PowerLineAction().createPowerLinesUponSwitchAdd(powerLineId,switchLatLng2);
		PowerLine powerLineA = powerLineMap.get("powerLineA");
		PowerLine powerLineB = powerLineMap.get("powerLineB");
		
		powerSwitch.setLatLng(switchLatLng2);
		powerSwitch.setPowerLineA(powerLineA);
		powerSwitch.setPowerLineB(powerLineB);
		powerSwitch.setStatus(true);
		Integer newPowerSwitchId= getPowerSwitchService().persist(powerSwitch);
		String resp= newPowerSwitchId.toString()+"*"+powerLineA.getId().toString()+"*"+powerLineB.getId();
		
		System.out.println("---------------------->>>"+newPowerSwitchId);
		getResponse().setContentType("text/html");
		getResponse().getWriter().write(resp);
		
		
	}
	catch(Exception e){
		log.info("Exception is::"+ e);
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
				boolean statusBool=powerSwitch.getStatus();
				int status=0;
				if(statusBool){
				status=1;
				}
				swListArray.add(switchLatitude+"^"+switchLongitude+"^"+switchId+"^"+status+"*");
				
			}
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(swListArray.toString());
		}
		catch(Exception e){
			log.info("Exception in getListPowerSwitch");
		}
	}


	public void getSwitchInfo()
	{
	
	try{
		Integer powerSwitchId= getRequest().getParameter("powerSwitchId")!=null?Integer.parseInt(getRequest().getParameter("powerSwitchId")):0;
		PowerSwitch powerSwitch = getPowerSwitchService().findById(powerSwitchId);
		Double switchLatitude= powerSwitch.getLatLng().getLatitude();
		Double switchLongitude= powerSwitch.getLatLng().getLongitude();
		Integer switchId = powerSwitch.getId();
		Integer powerLineAId = powerSwitch.getPowerLineA().getId();
		Integer powerLineBId = powerSwitch.getPowerLineB().getId();
		boolean statusBool=powerSwitch.getStatus();
		int status=0;
		if(statusBool){
			status=1;
			}
		String contentString=switchLatitude+"^"+switchLongitude+"^"+switchId+"^"+powerLineAId+"^"+powerLineBId+"^"+status;		
		getResponse().setContentType("text/html");
		getResponse().getWriter().write(contentString);
	}
	catch(Exception e){
		log.info("Exception in getListPowerSwitch");
	}
	
	}
	
	
	public void powerSwitchOnOff(){
		try{
		log.info("powerSwitchOnOff start ");
		Integer newSwitchStatus= getRequest().getParameter("newSwitchStatus")!=null?Integer.parseInt(getRequest().getParameter("newSwitchStatus")):0;
		Integer powerSwitchId= getRequest().getParameter("powerSwitchId")!=null?Integer.parseInt(getRequest().getParameter("powerSwitchId")):0;
		log.info("powerSwitchOnOff Data Base Call start ");
		int result=getPowerSwitchService().changeSwitchStatus(powerSwitchId, newSwitchStatus);
		//PowerSwitch pwSwitch = getPowerSwitchService().findById(powerSwitchId);
		log.info("No of rows updated "+result);
		getResponse().setContentType("text/html");
		getResponse().getWriter().write(newSwitchStatus.toString());
		}
		catch(Exception e){
		log.info("Exception in powerSwitchOnOff ");
		}
	}

	public void setNewPowerLineForExistingSwitch(PowerLine powerLineB) {
		ArrayList<PowerSwitch> powerSwitchList = getPowerSwitchService().getAllPowerSwitch();
		PowerSwitch powerSwitch= null;
		
		for(int i=0;i<powerSwitchList.size();i++)
		{
			PowerSwitch pSwitch=powerSwitchList.get(i);
			LatLng switchLocation= pSwitch.getLatLng();
			if(switchLocation.equals(powerLineB.getLatLngByDestination()))
			{
				powerSwitch =pSwitch;
				break;
			}				
		}
		
		if(powerSwitch!=null)
		{
			powerSwitch.setPowerLineA(powerLineB);
			getPowerSwitchService().merge(powerSwitch);
		}
		
	}
}

