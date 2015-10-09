package com.htc.action;

import org.apache.log4j.Logger;
import com.htc.utilities.CommonUtilities;

public class ResetDatabaseAction extends CommonUtilities {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(ResetDatabaseAction.class);
	
	public void resetDatabase() {
		String resetFlag = "false"; 
		try {
			//Sequence to reset database
			//supplier --> powerSwitch --> powerLine --> powerSource --> disaster --> holonElement --> holonObject --> latLng 
			int deleteResponseSupplier = getSupplierService().deleteAllSuppliers();
			int deleteResponsePowerSwitch = getPowerSwitchService().deleteAllPowerSwitches();
			int deleteResponsePowerLine = getPowerLineService().deleteAllPowerLines();
			int deleteResponsePowerSource = getPowerSourceService().deleteAllPowerSources();
			int deleteResponseDisaster = getDisasterService().deleteAllDisasters();
			int deleteResponseHolonElement = getHolonElementService().deleteAllHolonElements();
			int deleteResponseHolonObject = getHolonObjectService().deleteAllHolonObjects();
			int deleteResponseLatLng = getLatLngService().deleteAllLatLngs();
			
			if(deleteResponseSupplier == 0 && deleteResponsePowerSwitch == 0 && deleteResponsePowerSource == 0 
					&& deleteResponsePowerLine == 0 && deleteResponseDisaster == 0 && deleteResponseHolonElement == 0 && deleteResponseHolonObject == 0
					&& deleteResponseLatLng == 0) {
				resetFlag = "resetAlreadyCompleted";
			} else if(deleteResponseSupplier >= 0 && deleteResponsePowerSwitch >= 0 && deleteResponsePowerSource >= 0 
					&& deleteResponsePowerLine >= 0 && deleteResponseDisaster >= 0 && deleteResponseHolonElement >= 0 && deleteResponseHolonObject >= 0
					&& deleteResponseLatLng >= 0) {
				resetFlag = "true";	
			}
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(resetFlag);
		} catch(Exception e) {
			log.info("Exception in resetDatabase()");
		}
	}
		
}
