package com.htc.utilities;

/*import java.util.Random;*/
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.htc.action.AbstractAction;
import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.hibernate.pojo.PowerSwitch;
import com.opensymphony.xwork2.ActionContext;

public class CommonUtilities extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private Map<Integer, PowerLine> listOfAllConnectedPowerLines = new TreeMap<Integer, PowerLine>();
	ArrayList<PowerLine> listOfAllNeighbouringConnectedPowerLines = new ArrayList<PowerLine>();
	static Logger log = Logger.getLogger(CommonUtilities.class);
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected HttpSession httpSession;

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpSession getHttpSession() {
		return getRequest().getSession();
	}
	
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	
	
	public static int getPercent(int x, int y) {
		float xF=new Float(x);
		float yF=new Float(y);
		float perc= ((xF/yF)*100);
		log.info("x is "+x);
		log.info("y is "+y);
		log.info("percent is "+perc);
		return (int) perc;
	}
	
	public static String getLineColor(int percentCap) {
		String color="brown";
		if(0<percentCap && percentCap<=20) {
			color = "red";
		} else if(20<percentCap && percentCap<=50) {
			color = "yellow";
		} else if(percentCap>50 && percentCap<100) {
			color ="green";
		} else if(percentCap==100) {
			color ="black";
		}
		return color;
	}
	
/*	public static float randomCapGenerator(float maxCap) {
		float finalX=0.0f;
		float minX = 00.0f;
		float maxX = maxCap;
		Random rand = new Random();
		finalX = rand.nextFloat() * (maxX - minX) + minX;
		return finalX;
	}
*/	

	public Map<String, Integer> getHolonObjectEnergyDetails(HolonObject holonObject) {
		Map<String, Integer> holonObjectEnergyDetails = new TreeMap<String, Integer>();
		Integer minimumEnergyRequired = 0;
		Integer maximumEnergyRequired = 0;
		Integer originalEnergyRequired = 0;
		Integer minimumProductionCapacity = 0;
		Integer maximumProductionCapacity = 0;
		Integer currentProduction=0;
		Integer currentEnergyRequired = 0;
		Integer flexibility = 0;

		ArrayList<HolonElement> holonElementList= getHolonElementService().getHolonElements(holonObject);
		for(HolonElement holonElement : holonElementList) {
			if(holonElement.getHolonElementType().getProducer()) {
				minimumProductionCapacity = minimumProductionCapacity + holonElement.getHolonElementType().getMinCapacity();
				maximumProductionCapacity = maximumProductionCapacity + holonElement.getHolonElementType().getMaxCapacity();
				if(holonElement.getHolonElementState().getId()==1) {
					currentProduction = currentProduction + holonElement.getCurrentCapacity();
				}
			} else {
				minimumEnergyRequired = minimumEnergyRequired+holonElement.getHolonElementType().getMinCapacity();
				maximumEnergyRequired = maximumEnergyRequired+holonElement.getHolonElementType().getMaxCapacity();
				if(holonElement.getHolonElementState().getId()==1) {
					originalEnergyRequired = originalEnergyRequired+holonElement.getCurrentCapacity();
				}
			}
		}
		currentEnergyRequired = originalEnergyRequired;
		if(originalEnergyRequired > 0) {
			if(currentProduction > 0 && currentProduction <= originalEnergyRequired) {
				currentEnergyRequired = originalEnergyRequired - currentProduction;
			} else if(currentProduction > 0 && currentProduction > originalEnergyRequired) {
				currentEnergyRequired = 0;
			}
		}
		flexibility = setFlexibilityOfHolonObject(holonObject);
		holonObjectEnergyDetails.put("noOfHolonElements", holonElementList.size());
		holonObjectEnergyDetails.put("minimumEnergyRequired", minimumEnergyRequired);
		holonObjectEnergyDetails.put("maximumEnergyRequired", maximumEnergyRequired);
		holonObjectEnergyDetails.put("originalEnergyRequired", originalEnergyRequired);
		holonObjectEnergyDetails.put("minimumProductionCapacity", minimumProductionCapacity);
		holonObjectEnergyDetails.put("maximumProductionCapacity", maximumProductionCapacity);
		holonObjectEnergyDetails.put("currentProduction", currentProduction);
		holonObjectEnergyDetails.put("currentEnergyRequired", currentEnergyRequired);
		holonObjectEnergyDetails.put("flexibility", flexibility);
		
		return holonObjectEnergyDetails;
	}
	
	public Map<String, String> getHolonEnergyDetails(HolonObject holonCoordinator) {

		Map<String, String> holonEnergyDetails = new TreeMap<String, String>();
		PowerLine powerLine = getPowerLineService().getPowerLineByHolonObject(holonCoordinator);
		ArrayList<HolonObject> holonObjectListByConnectedPowerLines = getHolonObjectListByConnectedPowerLines(powerLine, holonCoordinator);
		Integer noOfHolonObjects = 0;
		StringBuffer holonObjectList=new StringBuffer("");
		Integer minimumProductionCapacityHolon = 0;
		Integer maximumProductionCapacityHolon = 0;
		Integer currentProductionHolon = 0;
		Integer minimumEnergyRequiredHolon = 0;
		Integer maximumEnergyRequiredHolon = 0;
		Integer currentEnergyRequiredHolon = 0;
		Integer originalEnergyRequiredHolon = 0;
		Integer flexibilityHolon = 0;
		Integer flexibilityPowerSource = 0;
		Integer minimumProductionCapacityPowerSource = 0;
		Integer maximumProductionCapacityPowerSource = 0;
		Integer currentProductionPowerSource = 0;
		
		noOfHolonObjects = holonObjectListByConnectedPowerLines.size();
		Map<String, Integer> holonObjectEnergyDetails = null;
		holonObjectList.append("<option value=\"Select Holon\" id= \"infoWinOpt\" selected>Select Holon Object</option>");
		for(HolonObject holonObject : holonObjectListByConnectedPowerLines) {
			holonObjectEnergyDetails = getHolonObjectEnergyDetails(holonObject);
			minimumProductionCapacityHolon = minimumProductionCapacityHolon + holonObjectEnergyDetails.get("minimumProductionCapacity");
			maximumProductionCapacityHolon = maximumProductionCapacityHolon + holonObjectEnergyDetails.get("maximumProductionCapacity");
			currentProductionHolon = currentProductionHolon + holonObjectEnergyDetails.get("currentProduction");
			minimumEnergyRequiredHolon = minimumEnergyRequiredHolon + holonObjectEnergyDetails.get("minimumEnergyRequired");
			maximumEnergyRequiredHolon = maximumEnergyRequiredHolon + holonObjectEnergyDetails.get("maximumEnergyRequired");
			currentEnergyRequiredHolon = currentEnergyRequiredHolon + holonObjectEnergyDetails.get("currentEnergyRequired");
			if(holonObjectEnergyDetails.get("currentEnergyRequired") > 0) {
				holonObjectList.append("<option value="+holonObject.getId()+" id= \"infoWinOpt\">"+holonObject.getHolonObjectType().getName()+
						" (Id:"+holonObject.getId()+") - Requires "+holonObjectEnergyDetails.get("currentEnergyRequired")+" energy</option>");
			} else {
				holonObjectList.append("<option value="+holonObject.getId()+" id= \"infoWinOpt\">"+holonObject.getHolonObjectType().getName()+
						" (Id:"+holonObject.getId()+")"+"</option>");
			}

			originalEnergyRequiredHolon = originalEnergyRequiredHolon + holonObjectEnergyDetails.get("originalEnergyRequired");
			flexibilityHolon = flexibilityHolon + holonObject.getFlexibility();
		}

/*		ArrayList<PowerSource> powerSourceListByHolonCoordinator = getPowerSourceService().findByHolonCoordinator(holonCoordinator);
		for(PowerSource powerSource : powerSourceListByHolonCoordinator) {
			//Include code for Power Source production and flexibility
		}
*/		
		
		//Code to get connected Power Sources
		
		ArrayList<PowerSource> listConnectedPowerSources = getPowerSourceListByConnectedPowerLines(powerLine, holonCoordinator);
		for(PowerSource powerSource : listConnectedPowerSources) {
			flexibilityPowerSource = flexibilityPowerSource + powerSource.getFlexibility();
			minimumProductionCapacityPowerSource = minimumProductionCapacityPowerSource + powerSource.getMinProduction();
			maximumProductionCapacityPowerSource = maximumProductionCapacityPowerSource + powerSource.getMaxProduction();
			currentProductionPowerSource = currentProductionPowerSource + powerSource.getCurrentProduction();
		}
		//Adding power source details to the holon.
		flexibilityHolon = flexibilityHolon + flexibilityPowerSource;
		minimumProductionCapacityHolon = minimumProductionCapacityHolon + minimumProductionCapacityPowerSource;
		maximumProductionCapacityHolon = maximumProductionCapacityHolon + maximumProductionCapacityPowerSource;
		currentProductionHolon = currentProductionHolon + currentProductionPowerSource;
		
		holonEnergyDetails.put("noOfHolonObjects", noOfHolonObjects.toString());
		holonEnergyDetails.put("minimumProductionCapacityHolon", minimumProductionCapacityHolon.toString());
		holonEnergyDetails.put("maximumProductionCapacityHolon", maximumProductionCapacityHolon.toString());
		holonEnergyDetails.put("currentProductionHolon", currentProductionHolon.toString());
		holonEnergyDetails.put("minimumEnergyRequiredHolon", minimumEnergyRequiredHolon.toString());
		holonEnergyDetails.put("maximumEnergyRequiredHolon", maximumEnergyRequiredHolon.toString());
		holonEnergyDetails.put("currentEnergyRequiredHolon", currentEnergyRequiredHolon.toString());
		holonEnergyDetails.put("originalEnergyRequiredHolon", originalEnergyRequiredHolon.toString());
		holonEnergyDetails.put("flexibilityHolon", flexibilityHolon.toString());
		holonEnergyDetails.put("holonObjectList", holonObjectList.toString());
		
		return holonEnergyDetails;
	}
	
	public boolean checkConnectedStatusForLine(ArrayList<PowerLine> connectedPowerLines, PowerLine powerLine) {
		if(powerLine.getType().equals(ConstantValues.SUBLINE) || powerLine.getType().equals(ConstantValues.POWERSUBLINE)) {
			return true;
		} else {
			for(PowerLine powerLine2 : connectedPowerLines) {
				if(powerLine2.getType().equals(ConstantValues.SUBLINE) ||powerLine2.getType().equals(ConstantValues.POWERSUBLINE) ) {
					return true;
				}
			}
		}
		return false;
	}

	public Integer saveLocation(LatLng locationToSave) {
		Integer locationid=0;
		Double lat =locationToSave.getLatitude();
		Double lng =locationToSave.getLongitude();
		ArrayList<LatLng> locationList=getLatLngService().findByLocation(lat,lng);
		log.info("Size of latLng Object list is "+locationList);
	
		if(locationList.size()==0) {
			log.info("Location is not in database ");
			locationid=getLatLngService().persist(locationToSave);
		} else {
			log.info("Location is already in database ");
			LatLng location = locationList.get(0);
			locationid=location.getId();
		}
		return locationid;
	}

	public ArrayList<PowerLine> connectedPowerLines(Integer powerLineId) {
		PowerLine powerLine = null;
		if(powerLineId.compareTo(0) > 0 ){
			powerLine = getPowerLineService().findById(powerLineId);
		}
		ArrayList<PowerLine> connectedPowerLines = new ArrayList<PowerLine>();
		if(powerLine != null) {
			connectedPowerLines = getPowerLineService().getConnectedPowerLines(powerLine);
		}
		log.info("Selected Power Line --> "+powerLineId);
		for(PowerLine powerLine2 : connectedPowerLines) {
			log.info("Connected Line --> "+powerLine2.getId());
		}
		PowerLine powerLine2 = null;
		PowerSwitch powerSwitch = null;
		for(int i =0; i< connectedPowerLines.size();i++) {
			powerLine2 = connectedPowerLines.get(i);
			powerSwitch = getPowerSwitchService().checkSwitchStatusBetweenPowerLines(powerLine, powerLine2);
			if(powerSwitch != null){
				if(!powerSwitch.getStatus()) {
					log.info("Connected Line to be removed --> "+powerLine2.getId());
					connectedPowerLines.remove(i);
				}
			}
		}
		for(PowerLine powerLine3 : connectedPowerLines) {
			if(!(listOfAllConnectedPowerLines.containsKey(powerLine3.getId()))) {
				listOfAllConnectedPowerLines.put(powerLine3.getId(), powerLine3);
				listOfAllNeighbouringConnectedPowerLines.add(powerLine3);
			}
		}
		for(PowerLine powerLine3 : connectedPowerLines) {
			ArrayList<PowerLine> tempConnectedPowerLines = getPowerLineService().getConnectedPowerLines(powerLine3);

			PowerLine powerLineTemp = null;
			PowerSwitch powerSwitchTemp = null;
			for(int i =0; i< tempConnectedPowerLines.size();i++) {
				powerLineTemp = tempConnectedPowerLines.get(i);
				powerSwitchTemp = getPowerSwitchService().checkSwitchStatusBetweenPowerLines(powerLine, powerLine2);
				if(powerSwitchTemp != null){
					if(!powerSwitchTemp.getStatus()) {
						log.info("Connected Line to be removed --> "+powerLineTemp.getId());
						tempConnectedPowerLines.remove(i);
					}
				}
			}
			
			for(PowerLine powerLine4 : tempConnectedPowerLines) {
				if(!(listOfAllConnectedPowerLines.containsKey(powerLine4.getId()))) {
					connectedPowerLines(powerLine3.getId());//Recursive call to get list of neighbors of neighbor
				}
			}
		}
		return listOfAllNeighbouringConnectedPowerLines;
	}
	
	/*
	 * This function can be called whenever:
	 * 1 - Holon Object is added to a main line.
	 * 2 - Power Source is added to a main line.
	 * 3 - Switch is toggled.
	 * 4 - Power source is toggled.
	 * 5 - Power source is edited.
	 * 6 - Holon Element is deleted from a holon object.
	 * 7 - Holon Element is edited in such a way that its current capacity is less than the previous current capacity.
	 * */	
	public void updateHolonObjectsAndPowerSources(Integer powerLineId) {
		PowerLine powerLine = getPowerLineService().findById(powerLineId);
		String powerLineType = powerLine.getType();
		PowerSource immediatePowerSource = null;
		HolonObject immediateHolonObject = null;
		Integer immediateHolonObjectId = 0;
		if(powerLineType.equals(ConstantValues.SUBLINE)) {
			immediateHolonObject = powerLine.getHolonObject();
			immediateHolonObjectId = immediateHolonObject.getId();
		} else if(powerLineType.equals(ConstantValues.POWERSUBLINE)) {
			immediatePowerSource = powerLine.getPowerSource();
		}
		ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineId);
		for(PowerLine powerLine2 : connectedPowerLines) {
			powerLineType = powerLine2.getType();
			if(powerLineType.equals(ConstantValues.SUBLINE)) {
				//Condition to set holon coordinator for the newly joined holon object
				if(immediateHolonObject != null && immediateHolonObject.getIsCoordinator() == false) {
					if(powerLine2.getHolonObject().getIsCoordinator().booleanValue() == true) {
						immediateHolonObject.setIsCoordinator(false);
						immediateHolonObject.setHolon(powerLine2.getHolonObject().getHolon());
						getHolonObjectService().merge(immediateHolonObject);
					}
				}
				//Condition to set holon coordinator for the newly joined power source
				if(immediatePowerSource != null && immediatePowerSource.getHolonCoordinator() == null) {
					if(powerLine2.getHolonObject().getIsCoordinator().booleanValue() == true) {
						immediatePowerSource.setHolonCoordinator(powerLine2.getHolonObject());
						getPowerSourceService().merge(immediatePowerSource);
					}
				}
				
			} else if(powerLineType.equals(ConstantValues.POWERSUBLINE)) {
				//Condition to set holon coordinator for the newly joined holon object
				if(immediateHolonObject != null && immediateHolonObject.getIsCoordinator().booleanValue() == false) {
					if(powerLine2.getPowerSource().getHolonCoordinator() != null) {
						immediateHolonObject.setIsCoordinator(false);
						immediateHolonObject.setHolon(powerLine2.getPowerSource().getHolonCoordinator().getHolon());
						getHolonObjectService().merge(immediateHolonObject);
					}
				}
				//Condition to set holon coordinator for the newly joined power source
				if(immediatePowerSource != null && immediatePowerSource.getHolonCoordinator() == null) {
					if(powerLine2.getPowerSource().getHolonCoordinator() != null) {
						immediatePowerSource.setHolonCoordinator(powerLine2.getPowerSource().getHolonCoordinator());
						getPowerSourceService().merge(immediatePowerSource);
					}
				}
				
			} 
			
		}//End of parent for loop
		
		immediateHolonObject = getHolonObjectService().findById(immediateHolonObjectId);
		/*Checking whether a holon coordinator has been assigned to the newly joined holon object or not. 
		 * If not, set a random holon and make this the new holon coordinator of that holon.*/
		if(immediateHolonObject != null && immediateHolonObject.getHolon() == null) {
			Integer randomHolonId = randomNumber(1, 4);
			Holon randomHolon = getHolonService().findById(randomHolonId);
			immediateHolonObject.setHolon(randomHolon);
			immediateHolonObject.setIsCoordinator(true);
			getHolonObjectService().merge(immediateHolonObject);
		}

	}

	public int randomNumber(int min, int max) {
		return (min + (int)(Math.random()*((max-min)+1)));
	}
	
	public ArrayList<HolonObject> getHolonObjectListByConnectedPowerLines(PowerLine powerLine, HolonObject holonCoordinator) {
		ArrayList<HolonObject> connectedHolonObjects = new ArrayList<HolonObject>();
		Integer powerLineId = 0;
		ArrayList<PowerLine> connectedPowerLines = null;
		if(powerLine != null) {
			powerLineId = powerLine.getId();
			connectedPowerLines = connectedPowerLines(powerLineId);
		}
		for(PowerLine powerLine2 : connectedPowerLines) {
			if(powerLine2.getType().equalsIgnoreCase(ConstantValues.SUBLINE)) {
				if(powerLine2.getHolonObject() != null && powerLine2.getHolonObject().getHolon()!= null && powerLine2.getHolonObject().getHolon().getId() 
						== holonCoordinator.getHolon().getId()) {
					connectedHolonObjects.add(powerLine2.getHolonObject());
				}
			}
		}
		return connectedHolonObjects;
	}
	
	public ArrayList<PowerSource> getPowerSourceListByConnectedPowerLines(PowerLine powerLine, HolonObject holonCoordinator) {
		ArrayList<PowerSource> connectedPowerSources = new ArrayList<PowerSource>();
		Integer powerLineId = powerLine.getId();
		ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineId);
		for(PowerLine powerLine2 : connectedPowerLines) {
			if(powerLine2.getType().equalsIgnoreCase(ConstantValues.POWERSUBLINE)) {
				if(powerLine2.getPowerSource().getHolonCoordinator()!=null && powerLine2.getPowerSource().
						getHolonCoordinator().getHolon().getId() == holonCoordinator.getHolon().getId()) {
					connectedPowerSources.add(powerLine2.getPowerSource());
				}
			}
		}
		return connectedPowerSources;
	}
	

	public boolean checkConnectivityBetweenHolonObjects(HolonObject holonObjectA, HolonObject holonObjectB) {
		Integer powerLineIdA = getPowerLineService().getPowerLineByHolonObject(holonObjectA).getId();
		Integer holonObjectBId = holonObjectB.getId();
		ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineIdA);
		for(PowerLine powerLine2 : connectedPowerLines) {
			if(powerLine2.getType().equalsIgnoreCase(ConstantValues.SUBLINE)) {
				if(holonObjectBId.equals(powerLine2.getHolonObject().getId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Integer setFlexibilityOfHolonObject(HolonObject holonObject) {
		Integer originalEnergyRequired = 0;
		Integer currentProduction=0;
		Integer flexibility = 0;
		ArrayList<HolonElement> holonElementList= getHolonElementService().getHolonElements(holonObject);
		for(HolonElement holonElement : holonElementList) {
			if(holonElement.getHolonElementType().getProducer()) {
				if(holonElement.getHolonElementState().getId()==1) {
					currentProduction = currentProduction + holonElement.getCurrentCapacity();
				}
			} else {
				if(holonElement.getHolonElementState().getId()==1) {
					originalEnergyRequired = originalEnergyRequired+holonElement.getCurrentCapacity();
				}
			}
		}
		flexibility = currentProduction;
		if(originalEnergyRequired > 0) {
			if(currentProduction > 0 && currentProduction > originalEnergyRequired) {
				flexibility = currentProduction - originalEnergyRequired;
			} else if (currentProduction > 0 && currentProduction <= originalEnergyRequired) {
				flexibility = 0;
			}
		}
		holonObject.setFlexibility(flexibility);
		getHolonObjectService().merge(holonObject);
		
		return flexibility;
	}

	public HolonObject findConnectedHolonCoordinatorByHolon(Holon holon, PowerLine powerLine) {
		HolonObject holonCoordinator = null;
		Integer powerLineId = powerLine.getId();
		ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineId);
		for(PowerLine powerLine2 : connectedPowerLines) {
			if(powerLine2.getType().equalsIgnoreCase(ConstantValues.SUBLINE)) {
				HolonObject tempHolonObject = powerLine2.getHolonObject();
				if(tempHolonObject != null && tempHolonObject.getHolon() != null && tempHolonObject.getHolon().getId() == holon.getId() 
						&& tempHolonObject.getIsCoordinator() == true ) {
					holonCoordinator = tempHolonObject;
					return holonCoordinator;
				}
			}
		}
		return holonCoordinator;
	}

}