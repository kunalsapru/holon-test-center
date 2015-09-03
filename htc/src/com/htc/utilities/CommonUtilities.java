package com.htc.utilities;

/*import java.util.Random;*/
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.htc.action.AbstractAction;
import com.htc.action.PowerSwitchAction;
import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.hibernate.pojo.Supplier;
import com.opensymphony.xwork2.ActionContext;

public class CommonUtilities extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private Map<Integer, PowerLine> listOfAllConnectedPowerLines = new TreeMap<Integer, PowerLine>();
	private ArrayList<PowerLine> listOfAllNeighbouringConnectedPowerLines = new ArrayList<PowerLine>();
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
		Map<String, Integer> flexibilityAndCurrentRequirement = getFlexibilityAndCurrentEnergyRequirementOfHolonObject(holonObject, currentEnergyRequired);
		currentEnergyRequired = flexibilityAndCurrentRequirement.get("currentEnergyRequired");
		flexibility = flexibilityAndCurrentRequirement.get("flexibility");
		
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
		ArrayList<PowerLine> removeIndexListA = new ArrayList<PowerLine>();
		ArrayList<PowerLine> removeIndexListB = new ArrayList<PowerLine>();
		
		if(powerLine != null) {
			connectedPowerLines = getPowerLineService().getConnectedPowerLines(powerLine);
		}
		PowerLine powerLine2 = null;
		PowerSwitch powerSwitch = null;
		for(int i =0; i< connectedPowerLines.size();i++) {
			powerLine2 = connectedPowerLines.get(i);
			powerSwitch = getPowerSwitchService().checkSwitchStatusBetweenPowerLines(powerLine, powerLine2);
			if(powerSwitch != null){
				if(!powerSwitch.getStatus()) {
					removeIndexListA.add(powerLine2);
				}
			}
		}
		for(int i=0; i<removeIndexListA.size();i++) {
			connectedPowerLines.remove(removeIndexListA.get(i));
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
				powerSwitchTemp = getPowerSwitchService().checkSwitchStatusBetweenPowerLines(powerLine, powerLineTemp);
				if(powerSwitchTemp != null){
					if(!powerSwitchTemp.getStatus()) {
						removeIndexListB.add(powerLineTemp);
					}
				}
			}
			for(int i=0; i<removeIndexListB.size();i++) {
				tempConnectedPowerLines.remove(removeIndexListB.get(i));
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
		Holon existingHolon = null;
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
				 if(powerLine2.getHolonObject() != null && powerLine2.getHolonObject().getHolon() != null){
					 existingHolon = powerLine2.getHolonObject().getHolon();
				 }
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
				if(powerLine2.getPowerSource() != null && powerLine2.getPowerSource().getHolonCoordinator() != null
						&& powerLine2.getPowerSource().getHolonCoordinator().getHolon() != null){
					existingHolon = powerLine2.getPowerSource().getHolonCoordinator().getHolon();
					}
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
			 if(existingHolon != null) {
				 immediateHolonObject.setHolon(existingHolon);
				 } else {
					 immediateHolonObject.setHolon(randomHolon);
					 immediateHolonObject.setHolon(randomHolon);
					 }
			immediateHolonObject.setHolon(randomHolon);
			immediateHolonObject.setIsCoordinator(true);
			getHolonObjectService().merge(immediateHolonObject);
		}

	}

	public int randomNumber(int min, int max) {
		return (min + (int)(Math.random()*((max-min)+1)));
	}
	
	public ArrayList<HolonObject> getHolonObjectListByConnectedPowerLines(PowerLine powerLine, HolonObject holonObject) {
		ArrayList<HolonObject> connectedHolonObjects = new ArrayList<HolonObject>();
		Integer powerLineId = 0;
		ArrayList<PowerLine> connectedPowerLines = null;
		if(powerLine != null) {
			powerLineId = powerLine.getId();
			connectedPowerLines = new CommonUtilities().connectedPowerLines(powerLineId);
		}
		for(PowerLine powerLine2 : connectedPowerLines) {
			System.out.println("Connected Lines --> "+powerLine2.getId());
			if(powerLine2.getType().equalsIgnoreCase(ConstantValues.SUBLINE)) {
				if(powerLine2.getHolonObject() != null && powerLine2.getHolonObject().getHolon()!= null && holonObject != null &&holonObject.getHolon() != null &&
						powerLine2.getHolonObject().getHolon().getId() == holonObject.getHolon().getId()) {
					connectedHolonObjects.add(powerLine2.getHolonObject());
				}
			}
		}
		return connectedHolonObjects;
	}
	
	public ArrayList<PowerSource> getPowerSourceListByConnectedPowerLines(PowerLine powerLine, HolonObject holonObject) {
		ArrayList<PowerSource> connectedPowerSources = new ArrayList<PowerSource>();
		Integer powerLineId = powerLine.getId();
		ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineId);
		for(PowerLine powerLine2 : connectedPowerLines) {
			if(powerLine2.getType().equalsIgnoreCase(ConstantValues.POWERSUBLINE)) {
				if(powerLine2.getPowerSource().getStatus() && powerLine2.getPowerSource().getHolonCoordinator()!=null && powerLine2.getPowerSource().
						getHolonCoordinator().getHolon() != null && holonObject.getHolon() != null && powerLine2.getPowerSource().getHolonCoordinator().getHolon().getId()
						== holonObject.getHolon().getId()) {
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
	
	public boolean checkConnectivityBetweenHolonObjectAndPowerSource(HolonObject holonObject, PowerSource powerSource) {
		Integer powerLineId = getPowerLineService().getPowerLineByHolonObject(holonObject).getId();
		Integer powerSourceId = powerSource.getId();
		ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineId);
		for(PowerLine powerLine : connectedPowerLines) {
			if(powerLine.getType().equalsIgnoreCase(ConstantValues.POWERSUBLINE)) {
				if(powerLine.getPowerSource().getStatus() && powerSourceId.equals(powerLine.getPowerSource().getId())) {
					return true;
				}
			}
		}
		return false;
	}

	public Map<String, Integer> getFlexibilityAndCurrentEnergyRequirementOfHolonObject(HolonObject holonObject, Integer currentEnergyRequired) {
		Map<String, Integer> flexibilityAndCurrentEnergyRequiredMap = new TreeMap<String, Integer>();
		
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

		//Scenario from producer's perspective
		ArrayList<Supplier> supplierListForProducer = getSupplierService().getSupplierListForProducer(holonObject);
		for(Supplier supplier : supplierListForProducer) {
			if(!checkConnectivityBetweenHolonObjects(holonObject, supplier.getHolonObjectConsumer()) 
					&& supplier.getMessageStatus().equals(ConstantValues.ACCEPTED)) {
				supplier.setMessageStatus(ConstantValues.CONNECTION_RESET);
				getSupplierService().merge(supplier);
			}
			if(checkConnectivityBetweenHolonObjects(holonObject, supplier.getHolonObjectConsumer())) {
				if(supplier.getMessageStatus().equalsIgnoreCase(ConstantValues.ACCEPTED)) {
					if(flexibility >= supplier.getPowerGranted()) {
						flexibility = flexibility - supplier.getPowerGranted();
					} else {
						flexibility = 0;
					}
				}
			}
		}
		
		//Scenario from consumer's perspective
		ArrayList<Supplier> supplierListForConsumer = getSupplierService().getSupplierListForConsumer(holonObject);
		for(Supplier supplier : supplierListForConsumer) {
			if(supplier.getHolonObjectProducer() != null) {
				if(!checkConnectivityBetweenHolonObjects(holonObject, supplier.getHolonObjectProducer()) 
						&& supplier.getMessageStatus().equals(ConstantValues.ACCEPTED)) {
					supplier.setMessageStatus(ConstantValues.CONNECTION_RESET);
					getSupplierService().merge(supplier);
				}
				if(checkConnectivityBetweenHolonObjects(holonObject, supplier.getHolonObjectProducer())) {
					if(supplier.getMessageStatus().equalsIgnoreCase(ConstantValues.ACCEPTED)) {
						if(currentEnergyRequired >= supplier.getPowerGranted()) {
							currentEnergyRequired = currentEnergyRequired - supplier.getPowerGranted();
						} else {
							currentEnergyRequired = 0;
						}
					}
				}
			} else if(supplier.getPowerSource() != null) {
				if(!checkConnectivityBetweenHolonObjectAndPowerSource(holonObject, supplier.getPowerSource()) 
						&& supplier.getMessageStatus().equals(ConstantValues.ACCEPTED)) {
					supplier.setMessageStatus(ConstantValues.CONNECTION_RESET);
					getSupplierService().merge(supplier);
					PowerSource powerSource = supplier.getPowerSource();
					powerSource.setFlexibility(powerSource.getFlexibility() + supplier.getPowerGranted());
					getPowerSourceService().merge(powerSource);
				}
				if(checkConnectivityBetweenHolonObjectAndPowerSource(holonObject, supplier.getPowerSource())) {
					if(supplier.getMessageStatus().equalsIgnoreCase(ConstantValues.ACCEPTED)) {
						if(currentEnergyRequired >= supplier.getPowerGranted()) {
							currentEnergyRequired = currentEnergyRequired - supplier.getPowerGranted();
						} else {
							currentEnergyRequired = 0;
						}
					}
				}
							
			}
		}
		
		holonObject.setFlexibility(flexibility);
		getHolonObjectService().merge(holonObject);
		flexibilityAndCurrentEnergyRequiredMap.put("flexibility", flexibility);
		flexibilityAndCurrentEnergyRequiredMap.put("currentEnergyRequired", currentEnergyRequired);
		return flexibilityAndCurrentEnergyRequiredMap;
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
	
	public HolonObject getHolonCoordinatorByElectionUsingPowerLineId(PowerLine powerLine, HolonObject holonObject){
		ArrayList<HolonObject> connectedholonObject = new ArrayList<HolonObject>();
		HolonObject newCoordinator= null;
		BigDecimal coordinatorCompetency;
		BigDecimal trustValue;
		BigDecimal maxValue = new BigDecimal(0.00);
		BigDecimal currentValue ;
		MathContext mc = new MathContext(2);
		connectedholonObject = getHolonObjectListByConnectedPowerLines(powerLine, holonObject);
		//Adding own holon object to connected list for leadership election
		if(holonObject != null){
			connectedholonObject.add(holonObject);
		}
		
		for(int j=0;j<connectedholonObject.size();j++)
		{
			if(connectedholonObject.size() > 0){
				coordinatorCompetency= connectedholonObject.get(j).getCoordinatorCompetency();
				trustValue=connectedholonObject.get(j).getTrustValue();
				currentValue=coordinatorCompetency.multiply(trustValue,mc);
				if(maxValue.compareTo(currentValue) == -1){
					// -1 means 2nd value is greater
					maxValue=currentValue;
					newCoordinator= connectedholonObject.get(j);
				}
			}
			
		}
		
		for(int k=0;k<connectedholonObject.size();k++){
			
			if(connectedholonObject.size() > 0){
				if(connectedholonObject.get(k).getId() == newCoordinator.getId()){
					//Set isCoordinator true 
					newCoordinator.setIsCoordinator(true);
					getHolonObjectService().merge(newCoordinator);
				}
				else{
					HolonObject tempHolonObject = connectedholonObject.get(k);
					//Set is coordinator to false
					tempHolonObject.setIsCoordinator(false);
					getHolonObjectService().merge(tempHolonObject);
				}
			}
			
		}
		return newCoordinator;
	}
	
public ArrayList<HolonObject> getHolonCoordinatorByElectionUsingForMainLineAndSwitch(PowerLine powerLine, String status){
		

		//powerLine powerLine Obj newPowerLineID is the id of the newly created powerLine
		// Check whether there are two coordinators, If yes, start leadership Election
		ArrayList<PowerLine> connectedPowerLinesToMainLine= null;
		if(status.equalsIgnoreCase("common")) {
			connectedPowerLinesToMainLine = connectedPowerLines(powerLine.getId());
		} else {
			connectedPowerLinesToMainLine = new PowerSwitchAction().connectedPowerLines(powerLine.getId());
		}
		ArrayList<HolonObject> connectedHolonObjectsFromMainLineMultipleHolon = new ArrayList<HolonObject>(); // For holon coordinators only
		ArrayList<HolonObject> connectedHolonObjectsFromMainLine2 = new ArrayList<HolonObject>(); // For holon objects
		ArrayList<HolonObject> connectedHolonObjectsFromMainLineSingleHolon = new ArrayList<HolonObject>(); // For holon coordinators only
		
		ArrayList<HolonObject> responseArray = new ArrayList<HolonObject>();
		if(connectedPowerLinesToMainLine.size()>0){
			//Check for same holon
			for(int i=0;i< connectedPowerLinesToMainLine.size();i++ )
			{
				if(connectedPowerLinesToMainLine.get(i).getType().equalsIgnoreCase(ConstantValues.SUBLINE) 
						&& connectedPowerLinesToMainLine.get(i).getHolonObject() != null 
						&& connectedPowerLinesToMainLine.get(i).getHolonObject().getHolon() != null )
				{
					if(connectedPowerLinesToMainLine.get(i).getHolonObject().getIsCoordinator()){
						connectedHolonObjectsFromMainLineMultipleHolon.add(connectedPowerLinesToMainLine.get(i).getHolonObject());
						System.out.println("Connected  Holon Objects are::"+connectedPowerLinesToMainLine.get(i).getHolonObject().getId());
					}
				}
			}
		}
	Holon tempHolon = null;
	for(HolonObject holonObject : connectedHolonObjectsFromMainLineMultipleHolon) {
		if(tempHolon != null && tempHolon.getId() == holonObject.getHolon().getId()) {
			tempHolon = holonObject.getHolon();
		}
		if(tempHolon == null) {
			tempHolon = holonObject.getHolon();
		}
	}
	//
	for(HolonObject holonObject : connectedHolonObjectsFromMainLineMultipleHolon) {
		if(holonObject.getHolon() != null && holonObject.getHolon().getId() == tempHolon.getId()) {
			connectedHolonObjectsFromMainLineSingleHolon.add(holonObject);
		}
	}
	
	HolonObject newCoordinator=null;
	HolonObject holonCoordinator=null;
	BigDecimal maxValue = new BigDecimal(0.00);
	BigDecimal currentValue1, currentValue2 ;
		//For 2 coordinators -- connecting via mainline
		if(connectedHolonObjectsFromMainLineSingleHolon.size()>1){
			//Mainline size==2 or PowerLine with coordinator size==1
			for(int i=0;i<connectedHolonObjectsFromMainLineSingleHolon.size()-1;i++){
				if(connectedHolonObjectsFromMainLineSingleHolon.get(i).getHolon().getId() == connectedHolonObjectsFromMainLineSingleHolon.get(i+1).getHolon().getId()) {
					currentValue1= connectedHolonObjectsFromMainLineSingleHolon.get(i).getCoordinatorCompetency().multiply(connectedHolonObjectsFromMainLineSingleHolon.get(i).getTrustValue());
					currentValue2= connectedHolonObjectsFromMainLineSingleHolon.get(i+1).getCoordinatorCompetency().multiply(connectedHolonObjectsFromMainLineSingleHolon.get(i+1).getTrustValue());
					
					if(currentValue1.compareTo(currentValue2) == -1){
						newCoordinator=connectedHolonObjectsFromMainLineSingleHolon.get(i+1);
					} else {
						newCoordinator = connectedHolonObjectsFromMainLineSingleHolon.get(i);
					}
					
				}
			}
			for(int j=0;j<connectedHolonObjectsFromMainLineSingleHolon.size();j++){
				if(connectedHolonObjectsFromMainLineSingleHolon.get(j)!= null && newCoordinator!=  null ){
					if(connectedHolonObjectsFromMainLineSingleHolon.get(j).getId() != newCoordinator.getId() )
					{
						holonCoordinator=connectedHolonObjectsFromMainLineSingleHolon.get(j);
						holonCoordinator.setIsCoordinator(false);
						getHolonObjectService().merge(holonCoordinator);
						System.out.println("Others::"+holonCoordinator.getCoordinatorCompetency()+"*"+holonCoordinator.getTrustValue());
					}
					else{
						newCoordinator.setIsCoordinator(true);
						getHolonObjectService().merge(newCoordinator);
						System.out.println("New Coordinator::"+newCoordinator.getCoordinatorCompetency()+"*"+newCoordinator.getTrustValue());
					}
				}
				
			}
		}
		if(connectedHolonObjectsFromMainLineSingleHolon.size()  == 1) {
			newCoordinator = connectedHolonObjectsFromMainLineSingleHolon.get(0);
		}
		if(newCoordinator!= null && holonCoordinator == null ){
			//Case of powerLine with one coordinator
			responseArray.add(newCoordinator);
			//responseArray.add(holonCoordinator);
		}else if(newCoordinator!= null && holonCoordinator != null){
			//case of Mainline
			responseArray.add(newCoordinator);
			responseArray.add(holonCoordinator);
			
		}else if(newCoordinator== null && holonCoordinator == null && powerLine.getType().equalsIgnoreCase(ConstantValues.MAINLINE)){
			//case of line with no coordinator
			//assign coordinator
			for(PowerLine powerLine2 : connectedPowerLinesToMainLine) {
				if(powerLine2.getType().equalsIgnoreCase(ConstantValues.SUBLINE)) {
					connectedHolonObjectsFromMainLine2.add(powerLine2.getHolonObject());
				}
			}
			for(HolonObject holonObject : connectedHolonObjectsFromMainLine2) {
				currentValue1 = holonObject.getCoordinatorCompetency().multiply(holonObject.getTrustValue());
				if(currentValue1.compareTo(maxValue) == 1) {
					maxValue = currentValue1;
					newCoordinator = holonObject;
				}
			}
//			updateHolonObjectsAndPowerSources(powerLine.getId());
//			ArrayList<HolonObject> newHolonCoordinator=getHolonCoordinatorByElectionUsingForMainLineAndSwitch(powerLine);
			newCoordinator.setIsCoordinator(true);
			getHolonObjectService().merge(newCoordinator);
			responseArray.add(newCoordinator);
		}
		
		return responseArray;
	}

 
}