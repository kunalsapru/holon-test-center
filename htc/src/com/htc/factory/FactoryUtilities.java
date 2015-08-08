package com.htc.factory;

import java.util.Map;
import java.util.TreeMap;

public class FactoryUtilities {
	
	public void sendDataToFactory(Map<Integer, String> holonObjectType_ProbabilityMap) {
		int cumulativeProbability = 0;
		for(Integer holonObjectPriority: holonObjectType_ProbabilityMap.keySet()) {
			System.out.println(holonObjectPriority+": [Object ID und Probability]"+holonObjectType_ProbabilityMap.get(holonObjectPriority));
			cumulativeProbability += Integer.parseInt(holonObjectType_ProbabilityMap.get(holonObjectPriority).split(":")[1]);
		}
		Map<Integer, Integer> finalMapAfterFactoryAlgorithm = algorithmForPriorityProbability(holonObjectType_ProbabilityMap, cumulativeProbability);
		for(Integer holonObjectId: finalMapAfterFactoryAlgorithm.keySet()) {
			System.out.println("Holon Object ID : "+holonObjectId+", No of Objects = "+finalMapAfterFactoryAlgorithm.get(holonObjectId));
		}
	}
	
	public Map<Integer, Integer> algorithmForPriorityProbability(Map<Integer, String> holonObjectType_ProbabilityMap, int cumulativeProbability) {
		Map<Integer, Integer> mapHolonObjectTypesOccurences = new TreeMap<Integer, Integer>();
		int mapLocationSize = HolonObjectsLocationFactory.mapLocations.size();
		
		try {
				while(mapLocationSize > 0 && cumulativeProbability > 1) {
					for(Integer holonObjectPriority : holonObjectType_ProbabilityMap.keySet()) {
						int noOfObjects = 0;
						double calculatedProbability = 0.0;
						if(mapLocationSize > 0 && cumulativeProbability > 1) {
							Integer holonObjectId = Integer.parseInt(holonObjectType_ProbabilityMap.get(holonObjectPriority).split(":")[0]);
							Integer holonObjectProbability = Integer.parseInt(holonObjectType_ProbabilityMap.get(holonObjectPriority).split(":")[1]);
							if(holonObjectPriority.equals(1)){
								if(holonObjectProbability >= 10) {
									calculatedProbability = (3/4.0)*(holonObjectProbability);
									noOfObjects = (int)(Math.ceil(calculatedProbability*(mapLocationSize)/100.0));
								} else {
									noOfObjects = (int)(Math.ceil((holonObjectProbability)*(mapLocationSize)/100.0));
									}
							} else if(holonObjectPriority.equals(2)) {
								if(holonObjectProbability >= 10) {
									calculatedProbability = (1/2.0)*(holonObjectProbability);
									noOfObjects = (int)(Math.ceil(calculatedProbability*(mapLocationSize)/100.0));
								} else {
									noOfObjects = (int)(Math.ceil((holonObjectProbability)*(mapLocationSize)/100.0));
									}
							} else if (holonObjectPriority.equals(3)) {
								if(holonObjectProbability >= 10) {
									calculatedProbability = (1/3.0)*(holonObjectProbability);
									noOfObjects = (int)(Math.ceil(calculatedProbability*(mapLocationSize)/100.0));
								} else {
									noOfObjects = (int)(Math.ceil((holonObjectProbability)*(mapLocationSize)/100.0));
									}
							} else if (holonObjectPriority.equals(4)) {
								if(holonObjectProbability >= 10) {
									calculatedProbability = (1/4.0)*(holonObjectProbability);
									noOfObjects = (int)(Math.ceil(calculatedProbability*(mapLocationSize)/100.0));
								} else {
									noOfObjects = (int)(Math.ceil((holonObjectProbability)*(mapLocationSize)/100.0));
									}
							}
							if(mapLocationSize > 0 && cumulativeProbability > 1) {
								holonObjectType_ProbabilityMap.put(holonObjectPriority, holonObjectId+":"+((int)(holonObjectProbability-calculatedProbability)));
								if(mapHolonObjectTypesOccurences.size() > 0 && mapHolonObjectTypesOccurences.containsKey(holonObjectId)) {
									mapHolonObjectTypesOccurences.put(holonObjectId, mapHolonObjectTypesOccurences.get(holonObjectId)+noOfObjects);
								} else {
									mapHolonObjectTypesOccurences.put(holonObjectId, noOfObjects);
								}
							}
							cumulativeProbability -= calculatedProbability;
							mapLocationSize -= noOfObjects;
							System.out.println("cumulativeProbability = "+cumulativeProbability);
							System.out.println("mapLocationSize = "+mapLocationSize);
							System.out.println("No of Objects = "+noOfObjects);
							System.out.println("Probability Map --> "+holonObjectType_ProbabilityMap);
							System.out.println("mapHolonObjectTypesOccurences = "+mapHolonObjectTypesOccurences);
						}
					}
					
				}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return mapHolonObjectTypesOccurences;
	}
	
	public int randomNumber(int min, int max) {
		return (min + (int)(Math.random()*((max-min)+1)));
	}

}
