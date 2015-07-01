package com.htc.action;


import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.utilities.CommonUtilities;


public class PowerSwitchAction extends CommonUtilities {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		powerSwitch.setHolonObjectId((holonObject.getId()));
		powerSwitch.setPowerLineId(0);
		powerSwitch.setLatLng(holonObject.getLatLng());
		powerSwitch.setStatus(true);
		
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newPowerSwitchID = getPowerSwitchService().persist(powerSwitch);
		System.out.println("NewLy Generated PowerSwitch  ID --> "+newPowerSwitchID);

	}
	
	public void addPowerSwitchForPowerLine(PowerLine powerLine){
		PowerSwitch powerSwitch=new PowerSwitch();
		powerSwitch.setPowerLineId((powerLine.getId()));
		powerSwitch.setHolonObjectId(0);
		powerSwitch.setStatus(true);
		
		Integer newPowerSwitchID = getPowerSwitchService().persist(powerSwitch);
		System.out.println("NewLy Generated PowerSwitch  ID --> "+newPowerSwitchID);
			
	}
}
