package com.htc.service;

import java.util.ArrayList;
import com.htc.hibernate.pojo.PowerSwitch;



	public interface PowerSwitchService {
		public Integer persist(PowerSwitch transientInstance);
		public PowerSwitch merge(PowerSwitch detachedInstance);
		public PowerSwitch findById(int powerSwitchId);
		public boolean delete(PowerSwitch persistentInstance);
		public ArrayList<PowerSwitch> getAllPowerSwitch();		
	
}
