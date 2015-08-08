package com.htc.factory;

import java.util.Map;

public class FactoryUtilities {
	
	public void createHolonObjects(Map<Integer, Integer> holonObjectType_ProbabilityMap) {
		
		for(Integer holonObjectId: holonObjectType_ProbabilityMap.keySet()) {
			System.out.println(holonObjectId+":"+holonObjectType_ProbabilityMap.get(holonObjectId));
		}
	}

}
