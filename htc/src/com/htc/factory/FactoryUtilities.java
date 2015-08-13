package com.htc.factory;

import java.util.Map;
import java.util.TreeMap;

import com.htc.action.LatLngAction;
import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonElementState;
import com.htc.hibernate.pojo.HolonElementType;
import com.htc.hibernate.pojo.HolonManager;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.HolonObjectType;
import com.htc.hibernate.pojo.LatLng;
import com.htc.utilities.CommonUtilities;

public class FactoryUtilities extends CommonUtilities{
	
	private static final long serialVersionUID = 1L;

	public void sendDataToFactory(Map<Integer, String> holonObjectType_ProbabilityMap) {
		int cumulativeProbability = 0;
		for(Integer holonObjectPriority: holonObjectType_ProbabilityMap.keySet()) {
			cumulativeProbability += Integer.parseInt(holonObjectType_ProbabilityMap.get(holonObjectPriority).split(":")[1]);
		}
		//Variables to be used inside the loop for data factory declared outside the loop so that we don't have to create a new object for entity.
		HolonObjectType holonObjectType = null;
		HolonObject holonObject = null;
		HolonCoordinator holonCoordinator = null;
		HolonManager holonManager = null;
		HolonManager holonManager2 = null;
		Integer holonManagerId = null;
		Double latNE = null;
		Double lngNE = null;
		Double latSW = null;
		Double lngSW = null;
		LatLng NorthlatLng = null;
		LatLng SouthlatLng = null;
		LatLngAction latLngAct= null;
		Integer NorthlocationId = null;
		Integer SouthlocationId = null;
		LatLng NorthlatLng2 = null;
		LatLng SouthlatLng2 = null;
		Integer newHolonObjectId = null;
		
		Map<Integer, Integer> finalMapAfterFactoryAlgorithm = algorithmForPriorityProbability(holonObjectType_ProbabilityMap, cumulativeProbability);
		for(Integer holonObjectTypeId: finalMapAfterFactoryAlgorithm.keySet()) {
			holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
			System.out.println("Holon Object Type : "+holonObjectType.getName()+", No of Objects = "+finalMapAfterFactoryAlgorithm.get(holonObjectTypeId));
			int noOfObjects = finalMapAfterFactoryAlgorithm.get(holonObjectTypeId);
			while(noOfObjects > 0){
				latNE = Double.parseDouble(HolonObjectsLocationFactory.mapLocations.get(0).split("~")[0].split("!")[0]);
				lngNE = Double.parseDouble(HolonObjectsLocationFactory.mapLocations.get(0).split("~")[0].split("!")[1]);
				latSW = Double.parseDouble(HolonObjectsLocationFactory.mapLocations.get(0).split("~")[1].split("!")[0]);
				lngSW = Double.parseDouble(HolonObjectsLocationFactory.mapLocations.get(0).split("~")[1].split("!")[1]);
				HolonObjectsLocationFactory.mapLocations.remove(0);
				
				NorthlatLng = new LatLng(latNE, lngNE);
				SouthlatLng = new LatLng(latSW, lngSW);
				latLngAct= new LatLngAction();
				NorthlocationId = latLngAct.saveLocation(NorthlatLng);
				SouthlocationId = latLngAct.saveLocation(SouthlatLng);
				NorthlatLng2 = getLatLngService().findById(NorthlocationId);
				SouthlatLng2 = getLatLngService().findById(SouthlocationId);

				holonObject = new HolonObject();
				holonObject.setLatLngByNeLocation(NorthlatLng2);
				holonObject.setLatLngBySwLocation(SouthlatLng2);
				
				int holonCoordinatorId = randomNumber(1, 4); // Replace this with hibernate function to get minimum and maximum ID of holonCoordinator in DB.
				if(holonCoordinatorId!=0) {
					holonCoordinator = getHolonCoordinatorService().findById(holonCoordinatorId);
					holonObject.setHolonCoordinator(holonCoordinator);
				}
				holonObject.setHolonObjectType(holonObjectType);
				holonObject.setLineConnectedState(false);
				
				holonManager = new HolonManager();
				holonManager.setName("Holon_Manager");
				holonManagerId = getHolonManagerService().persist(holonManager);
				holonManager2 = getHolonManagerService().findById(holonManagerId);
				holonObject.setHolonManager(holonManager2);
				holonObject.setCanCommunicate(true);
				holonObject.setCreatedFactory(true);
				//Calling service method to save the object in database and saving the auto-incremented ID in an integer
				newHolonObjectId = getHolonObjectService().persist(holonObject);
				System.out.println("New HolonObject ID = "+newHolonObjectId);

				//Adding 10 Holon Elements to the newly generated Holon Object
				for(int i=0; i<10; i++) {
					Integer holonElementTypeId = randomNumber(1, 6);// Replace this with hibernate function to get minimum and maximum ID of holonElementType in DB.
					Integer holonElementStateId = randomNumber(1, 2);// Replace this with hibernate function to get minimum and maximum ID of holonElementState in DB.
					HolonElementType holonElementType = getHolonElementTypeService().findById(holonElementTypeId);
					HolonElementState holonElementState = getHolonElementStateService().findById(holonElementStateId);

					//Current capacity is a random number between maximum and minimum holon element capacity.
					Integer currentCapacity = randomNumber(holonElementType.getMinCapacity(), holonElementType.getMaxCapacity());
					
					HolonElement holonElement = new HolonElement();
					holonElement.setCurrentCapacity(currentCapacity);
					holonElement.setHolonElementState(holonElementState);
					holonElement.setHolonElementType(holonElementType);
					holonElement.setHolonObject(holonObject);
					//Calling service method to save the Element in database and saving the auto-incremented ID in an integer
					Integer newHolonElementID = getHolonElementService().persist(holonElement);
					System.out.println("NewLy Generated Holon Element ID --> "+newHolonElementID);
				}
				
				noOfObjects --;
			}
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
							Integer holonObjectTypeId = Integer.parseInt(holonObjectType_ProbabilityMap.get(holonObjectPriority).split(":")[0]);
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
								holonObjectType_ProbabilityMap.put(holonObjectPriority, holonObjectTypeId+":"+((int)(holonObjectProbability-calculatedProbability)));
								if(mapHolonObjectTypesOccurences.size() > 0 && mapHolonObjectTypesOccurences.containsKey(holonObjectTypeId)) {
									mapHolonObjectTypesOccurences.put(holonObjectTypeId, mapHolonObjectTypesOccurences.get(holonObjectTypeId)+noOfObjects);
								} else {
									mapHolonObjectTypesOccurences.put(holonObjectTypeId, noOfObjects);
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