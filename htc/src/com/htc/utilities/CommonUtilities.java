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
import com.htc.hibernate.pojo.HolonCoordinator;
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
		Integer flexibility = 0;
		Integer currentEnergyRequired = 0;
		
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
		flexibility = currentProduction;
		if(originalEnergyRequired > 0) {
			if(currentProduction > 0 && currentProduction <= originalEnergyRequired) {
				currentEnergyRequired = originalEnergyRequired - currentProduction;
				flexibility = 0;
			} else if(currentProduction > 0 && currentProduction > originalEnergyRequired) {
				currentEnergyRequired = 0;
				flexibility = currentProduction - originalEnergyRequired;
			}
		}

		holonObjectEnergyDetails.put("noOfHolonElements", holonElementList.size());
		holonObjectEnergyDetails.put("minimumEnergyRequired", minimumEnergyRequired);
		holonObjectEnergyDetails.put("maximumEnergyRequired", maximumEnergyRequired);
		holonObjectEnergyDetails.put("originalEnergyRequired", originalEnergyRequired);
		holonObjectEnergyDetails.put("minimumProductionCapacity", minimumProductionCapacity);
		holonObjectEnergyDetails.put("maximumProductionCapacity", maximumProductionCapacity);
		holonObjectEnergyDetails.put("currentProduction", currentProduction);
		holonObjectEnergyDetails.put("currentEnergyRequired", currentEnergyRequired);
		holonObjectEnergyDetails.put("flexibility", flexibility);
		holonObject.setFlexibility(flexibility);
		getHolonObjectService().merge(holonObject);
		
		return holonObjectEnergyDetails;
	}
	
	public Map<String, String> getHolonEnergyDetails(HolonCoordinator holonCoordinator) {

		Map<String, String> holonEnergyDetails = new TreeMap<String, String>();
		PowerLine powerLine = getPowerLineService().getPowerLineByHolonObject(holonCoordinator.getHolonObject());
		ArrayList<HolonObject> holonObjectListByConnectedPowerLines = getHolonObjectListByConnectedPowerLines(powerLine);
		HolonObject holonObjectOfCoordinator = holonCoordinator.getHolonObject();
		Integer holonObjectOfCoordinatorId = holonObjectOfCoordinator.getId();
		
		Integer noOfHolonObjects = 0;
		ArrayList<String> hoObjectIdList = new ArrayList<String>();
		StringBuffer holonObjectList=new StringBuffer("");
		Integer minimumProductionCapacityHolon = 0;
		Integer maximumProductionCapacityHolon = 0;
		Integer currentProductionHolon = 0;
		Integer minimumEnergyRequiredHolon = 0;
		Integer maximumEnergyRequiredHolon = 0;
		Integer currentEnergyRequiredHolon = 0;
		Integer originalEnergyRequiredHolon = 0;
		Integer flexibilityHolon = 0;
		noOfHolonObjects = holonObjectListByConnectedPowerLines.size();
		Map<String, Integer> holonObjectEnergyDetails = null;
		for(HolonObject holonObject : holonObjectListByConnectedPowerLines) {
			hoObjectIdList.add(new Integer(holonObject.getId()).toString().concat("~").concat(holonObject.getHolonObjectType().getName()));
			holonObjectEnergyDetails = getHolonObjectEnergyDetails(holonObject);
			minimumProductionCapacityHolon = minimumProductionCapacityHolon + holonObjectEnergyDetails.get("minimumProductionCapacity");
			maximumProductionCapacityHolon = maximumProductionCapacityHolon + holonObjectEnergyDetails.get("maximumProductionCapacity");
			currentProductionHolon = currentProductionHolon + holonObjectEnergyDetails.get("currentProduction");
			minimumEnergyRequiredHolon = minimumEnergyRequiredHolon + holonObjectEnergyDetails.get("minimumEnergyRequired");
			maximumEnergyRequiredHolon = maximumEnergyRequiredHolon + holonObjectEnergyDetails.get("maximumEnergyRequired");
			currentEnergyRequiredHolon = currentEnergyRequiredHolon + holonObjectEnergyDetails.get("currentEnergyRequired");
			originalEnergyRequiredHolon = originalEnergyRequiredHolon + holonObjectEnergyDetails.get("originalEnergyRequired");
			flexibilityHolon = flexibilityHolon + holonObject.getFlexibility();
		}

/*		ArrayList<PowerSource> powerSourceListByHolonCoordinator = getPowerSourceService().findByHolonCoordinator(holonCoordinator);
		for(PowerSource powerSource : powerSourceListByHolonCoordinator) {
			//Include code for Power Source production and flexibility
		}
*/		
		String holonObjectTypeName = holonObjectOfCoordinator.getHolonObjectType().getName();
		hoObjectIdList.remove(holonObjectOfCoordinatorId.toString().concat("~").concat(holonObjectTypeName));
		holonObjectList.append("<option value=\"Select Holon\" id= \"infoWinOpt\" selected>Select Holon Object</option>");
		
		for(String valueString : hoObjectIdList) {
			String holonObjectId= valueString.split("~")[0];
			String hoObjectType =valueString.split("~")[1];
			holonObjectList.append("<option value="+holonObjectId+" id= \"infoWinOpt\">"+hoObjectType+" (Id:"+holonObjectId+")"+"</option>");
		}
		
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
		PowerLine powerLine = getPowerLineService().findById(powerLineId);
		ArrayList<PowerLine> connectedPowerLines = getPowerLineService().getConnectedPowerLines(powerLine);
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
		if(powerLineType.equals(ConstantValues.SUBLINE)) {
			immediateHolonObject = powerLine.getHolonObject();
		} else if(powerLineType.equals(ConstantValues.POWERSUBLINE)) {
			immediatePowerSource = powerLine.getPowerSource();
		}

		ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineId);
		for(PowerLine powerLine2 : connectedPowerLines) {
			powerLineType = powerLine2.getType();
			if(powerLineType.equals(ConstantValues.SUBLINE)) {
				
				//Condition to set holon coordinator for the newly joined holon object
				if(immediateHolonObject != null && immediateHolonObject.getHolonCoordinator() == null) {
					if(powerLine2.getHolonObject().getHolonCoordinator() != null) {
						immediateHolonObject.setHolonCoordinator(powerLine2.getHolonObject().getHolonCoordinator());
						getHolonObjectService().merge(immediateHolonObject);
					}
				}
				//Condition to set holon coordinator for the newly joined power source
				if(immediatePowerSource != null && immediatePowerSource.getHolonCoordinator() == null) {
					if(powerLine2.getHolonObject().getHolonCoordinator() != null) {
						immediatePowerSource.setHolonCoordinator(powerLine2.getHolonObject().getHolonCoordinator());
						getPowerSourceService().merge(immediatePowerSource);
					}
				}
				
			} else if(powerLineType.equals(ConstantValues.POWERSUBLINE)) {
				
				//Condition to set holon coordinator for the newly joined holon object
				if(immediateHolonObject != null && immediateHolonObject.getHolonCoordinator() == null) {
					if(powerLine2.getPowerSource().getHolonCoordinator() != null) {
						immediateHolonObject.setHolonCoordinator(powerLine2.getPowerSource().getHolonCoordinator());
						if(powerLine2.getPowerSource().getHolonCoordinator().getHolonObject() == null) {
							powerLine2.getPowerSource().getHolonCoordinator().setHolonObject(immediateHolonObject);
						}
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
		
		/*Checking whether a holon coordinator has been assigned to the newly joined holon object or not. 
		 * If not, set a random holon and make this the new holon coordinator of that holon.*/
		if(immediateHolonObject != null && immediateHolonObject.getHolonCoordinator() == null) {
			Integer randomHolonCoordinatorId = randomNumber(1, 4);
			HolonCoordinator randomHolonCoordinator = getHolonCoordinatorService().findById(randomHolonCoordinatorId);
			immediateHolonObject.setHolonCoordinator(randomHolonCoordinator);
			getHolonObjectService().merge(immediateHolonObject);
			randomHolonCoordinator.setHolonObject(immediateHolonObject);
			getHolonCoordinatorService().merge(randomHolonCoordinator);
		}

		/*Checking whether a holon coordinator has been assigned to the newly joined holon object or not. If not, set a random holon.*/
		if(immediatePowerSource != null && immediatePowerSource.getHolonCoordinator() == null) {
			Integer randomHolonCoordinatorId = randomNumber(1, 4);
			HolonCoordinator randomHolonCoordinator = getHolonCoordinatorService().findById(randomHolonCoordinatorId);
			immediatePowerSource.setHolonCoordinator(randomHolonCoordinator);
			getPowerSourceService().merge(immediatePowerSource);
		}

	}

	public int randomNumber(int min, int max) {
		return (min + (int)(Math.random()*((max-min)+1)));
	}
	
	public ArrayList<HolonObject> getHolonObjectListByConnectedPowerLines(PowerLine powerLine) {
		ArrayList<HolonObject> connectedHolonObjects = new ArrayList<HolonObject>();
		Integer powerLineId = powerLine.getId();
		ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineId);
		for(PowerLine powerLine2 : connectedPowerLines) {
			if(powerLine2.getType().equalsIgnoreCase(ConstantValues.SUBLINE)) {
				connectedHolonObjects.add(powerLine2.getHolonObject());
			}
		}
		return connectedHolonObjects;
	}

}