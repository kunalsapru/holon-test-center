package com.htc.action;

import com.htc.pojo.HolonObject;
import com.htc.pojo.PowerSwitch;

public class PowerSwitchAction {

	public void SwitchOn(HolonObject holonObject, PowerSwitch powerSwitch){
		if(powerSwitch.getBelongsTo() == holonObject.getId()){
			powerSwitch.setStatus(true);
		}
	}
	
	public void SwitchOff(HolonObject holonObject, PowerSwitch powerSwitch){
		if(powerSwitch.getBelongsTo() == holonObject.getId()){
			powerSwitch.setStatus(false);
		}
	}
}
