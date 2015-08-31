package com.htc.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.HolonObjectType;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.utilities.CommonUtilities;

public class HolonObjectAction extends CommonUtilities {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonObjectAction.class);
	
	public void createHolonObject(){
		try {
		Integer holonObjectTypeId = getRequest().getParameter("holonObjectType")!=null?Integer.parseInt(getRequest().getParameter("holonObjectType")):0;
		Integer canCommunicate = getRequest().getParameter("canCommunicate")!=null?Integer.parseInt(getRequest().getParameter("canCommunicate")):0;
		Double latNE = getRequest().getParameter("latNE")!=null?Double.parseDouble(getRequest().getParameter("latNE")):0D;
		Double lngNE = getRequest().getParameter("lngNE")!=null?Double.parseDouble(getRequest().getParameter("lngNE")):0D;
		Double latSW = getRequest().getParameter("latSW")!=null?Double.parseDouble(getRequest().getParameter("latSW")):0D;
		Double lngSW = getRequest().getParameter("lngSW")!=null?Double.parseDouble(getRequest().getParameter("lngSW")):0D;
		BigDecimal coordinatorCompetency = new BigDecimal(Math.random());
		BigDecimal trustValue= new BigDecimal(Math.random());
		
		
		LatLng NorthlatLng = new LatLng(latNE, lngNE);
		LatLng SouthlatLng = new LatLng(latSW, lngSW);
		Integer NorthlocationId = saveLocation(NorthlatLng);
		Integer SouthlocationId = saveLocation(SouthlatLng);
		LatLng NorthlatLng2 = getLatLngService().findById(NorthlocationId);
		LatLng SouthlatLng2 = getLatLngService().findById(SouthlocationId);
		HolonObject holonObject = new HolonObject(); // Creating HolonObject object to store values

		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
		holonObject.setLatLngByNeLocation(NorthlatLng2);
		holonObject.setLatLngBySwLocation(SouthlatLng2);
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.setLineConnectedState(false);
		holonObject.setCanCommunicate(canCommunicate==1?true:false);
		holonObject.setFlexibility(0);
		holonObject.setIsCoordinator(false);
		holonObject.setCoordinatorCompetency(coordinatorCompetency);
		holonObject.setTrustValue(trustValue);
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newHolonObjectID = getHolonObjectService().persist(holonObject);
		new HolonCoordinatorAction().chooseCoordinatorValue();
		log.info("NewLy Generated Holon Object ID --> "+newHolonObjectID);
		HolonObject holonObject2 = getHolonObjectService().findById(newHolonObjectID);
		String hc_ne_location="";
		Integer noOfHolons=0;
		String holonColor="black";
		boolean isCoord=false;
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		StringBuffer hoResponse = new StringBuffer();
		hoResponse.append(holonObject2.getId()+"!");
		hoResponse.append(isCoord+"!");
		hoResponse.append(hc_ne_location+"!");
		hoResponse.append(noOfHolons.toString()+"!");
		hoResponse.append(holonColor);	
		
		log.info(hoResponse.toString());
		getResponse().getWriter().write(hoResponse.toString());
		System.out.println("Values are------>"+coordinatorCompetency+"*********"+trustValue);
		
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" occurred in action createHolonObject()");
			e.printStackTrace();
		}
	}
	
	public void getHolonObjectInfoWindow() {
		try {
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;			
			HolonObject holonObject2 = getHolonObjectService().findById(holonObjectId);
			String holonCoordinatorName_Holon="Not Part of any Holon";
			String holonColor="black";
			HolonObject hc = null;
			if(holonObject2.getHolon() != null) {
				PowerLine immediatePowerLine = getPowerLineService().getPowerLineByHolonObject(holonObject2); 
				hc = findConnectedHolonCoordinatorByHolon (holonObject2.getHolon(), immediatePowerLine);
			}
			Integer coordinatorHolonId=0;
			String coOredNeLocation="";
			if(hc!=null) {
				if(checkConnectivityBetweenHolonObjects(holonObject2, hc)) {
					holonCoordinatorName_Holon = holonObject2.getHolon().getName();
					holonColor = holonObject2.getHolon().getColor();
					coordinatorHolonId = hc.getId();
					coOredNeLocation= hc.getLatLngByNeLocation().getLatitude()+"~"+hc.getLatLngByNeLocation().getLongitude();
				} else {
					coordinatorHolonId = -1;
				}
			}
			String holonObjectTypeName = holonObject2.getHolonObjectType().getName();
			String ne_location = holonObject2.getLatLngByNeLocation().getLatitude()+"~"+holonObject2.getLatLngByNeLocation().getLongitude();
			String sw_location = holonObject2.getLatLngBySwLocation().getLatitude()+"~"+holonObject2.getLatLngBySwLocation().getLongitude();			
			String lineConnectedState = holonObject2.getLineConnectedState()==true?"Yes":"No";
			String canCommunicate = holonObject2.getCanCommunicate()==true?"Yes":"No"; 
			Integer flexibility = holonObject2.getFlexibility();
			HolonObject holonObject = getHolonObjectService().findById(holonObjectId);
			Map<String, Integer> holonObjectEnergyDetails = getHolonObjectEnergyDetails(holonObject);
			Integer noOfHolonElements = holonObjectEnergyDetails.get("noOfHolonElements");
			Integer minimumEnergyRequired = holonObjectEnergyDetails.get("minimumEnergyRequired");
			Integer maximumEnergyRequired = holonObjectEnergyDetails.get("maximumEnergyRequired");
			Integer originalEnergyRequired = holonObjectEnergyDetails.get("originalEnergyRequired");
			Integer minimumProductionCapacity = holonObjectEnergyDetails.get("minimumProductionCapacity");
			Integer maximumProductionCapacity = holonObjectEnergyDetails.get("maximumProductionCapacity");
			Integer currentProduction = holonObjectEnergyDetails.get("currentProduction");
			Integer currentEnergyRequired = holonObjectEnergyDetails.get("currentEnergyRequired");
			//Variables to capture Holon Details
			String noOfHolonObjects = "0";
			String minimumProductionCapacityHolon = "0";
			String maximumProductionCapacityHolon = "0";
			String currentProductionHolon = "0";
			String minimumEnergyRequiredHolon = "0";
			String maximumEnergyRequiredHolon = "0";
			String currentEnergyRequiredHolon = "0";
			String holonObjectList = "";
			String originalEnergyRequiredHolon = "0";
			String flexibilityHolon = "0";
			if(hc != null && holonObjectId == hc.getId()) {
				Map<String, String> holonEnergyDetails = getHolonEnergyDetails(hc);
				noOfHolonObjects = holonEnergyDetails.get("noOfHolonObjects");
				minimumProductionCapacityHolon = holonEnergyDetails.get("minimumProductionCapacityHolon");
				maximumProductionCapacityHolon = holonEnergyDetails.get("maximumProductionCapacityHolon");
				currentProductionHolon = holonEnergyDetails.get("currentProductionHolon");
				minimumEnergyRequiredHolon = holonEnergyDetails.get("minimumEnergyRequiredHolon");
				maximumEnergyRequiredHolon = holonEnergyDetails.get("maximumEnergyRequiredHolon");
				currentEnergyRequiredHolon = holonEnergyDetails.get("currentEnergyRequiredHolon");
				originalEnergyRequiredHolon = holonEnergyDetails.get("originalEnergyRequiredHolon");
				flexibilityHolon = holonEnergyDetails.get("flexibilityHolon");
				holonObjectList = holonEnergyDetails.get("holonObjectList");
			}
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			StringBuffer hoResponse = new StringBuffer();
			hoResponse.append(holonObject2.getId()+"!");
			hoResponse.append(holonCoordinatorName_Holon+"!");
			hoResponse.append(holonObjectTypeName+"!");
			hoResponse.append(ne_location+"!");
			hoResponse.append(sw_location+"!");
			hoResponse.append(lineConnectedState+"!");
			hoResponse.append(holonColor+"!");
			hoResponse.append(coordinatorHolonId+"!");
			hoResponse.append(noOfHolonElements+"!");
			hoResponse.append(minimumEnergyRequired+"!");
			hoResponse.append(maximumEnergyRequired+"!");
			hoResponse.append(originalEnergyRequired+"!");
			hoResponse.append(minimumProductionCapacity+"!");
			hoResponse.append(maximumProductionCapacity+"!");
			hoResponse.append(currentProduction+"!");
			hoResponse.append(noOfHolonObjects+"!");
			hoResponse.append(minimumEnergyRequiredHolon+"!");
			hoResponse.append(maximumEnergyRequiredHolon+"!");
			hoResponse.append(currentEnergyRequiredHolon+"!");
			hoResponse.append(minimumProductionCapacityHolon+"!");
			hoResponse.append(maximumProductionCapacityHolon+"!");
			hoResponse.append(currentProductionHolon+"!");
			hoResponse.append(holonObjectList+"!");
			hoResponse.append(canCommunicate+"!");
			hoResponse.append(coOredNeLocation+"!");
			boolean createdFromFactory = holonObject2.getCreatedFactory() != null ? holonObject2.getCreatedFactory() : false;
			if(createdFromFactory) {
				hoResponse.append("Yes!");
			} else {
				hoResponse.append("No!");
			}
			hoResponse.append(currentEnergyRequired+"!");
			hoResponse.append(flexibility+"!");
			hoResponse.append(originalEnergyRequiredHolon+"!");
			hoResponse.append(flexibilityHolon);
			getResponse().getWriter().write(hoResponse.toString());
			} catch (Exception e) {
				log.info("Exception "+e.getMessage()+" occurred in action createHolonObject()");
				e.printStackTrace();
			}
	}
	
	public void editHolonObject(){
		try {
		Integer holonObjectTypeId = getRequest().getParameter("holonObjectType")!=null?Integer.parseInt(getRequest().getParameter("holonObjectType")):0;
		Integer hiddenHolonObjectId = getRequest().getParameter("hiddenHolonObjectId")!=null?Integer.parseInt(getRequest().getParameter("hiddenHolonObjectId")):0;
		//Integer holonCoordinatorId = getRequest().getParameter("holonCoordinatorId")!=null?Integer.parseInt(getRequest().getParameter("holonCoordinatorId")):0;
		Integer canCommunicate = getRequest().getParameter("canCommunicate")!=null?Integer.parseInt(getRequest().getParameter("canCommunicate")):0;
		HolonObject holonObject = getHolonObjectService().findById(hiddenHolonObjectId);
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.setCanCommunicate(canCommunicate==1?true:false);
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		getHolonObjectService().merge(holonObject);
		new HolonCoordinatorAction().chooseCoordinatorValue();
		HolonObject updatedHolonObject = getHolonObjectService().findById(hiddenHolonObjectId);
		PowerLine powerLine = getPowerLineService().getPowerLineByHolonObject(updatedHolonObject);
		HolonObject updatedHoCo = findConnectedHolonCoordinatorByHolon(updatedHolonObject.getHolon(), powerLine);
		String holonColor="black";
		if(updatedHoCo!=null) {
			holonColor=updatedHoCo.getHolon().getColor();
		}
		StringBuffer hoResponse = new StringBuffer();
		hoResponse.append(holonColor+"!");
		hoResponse.append(holonObject.getId());
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");		
		getResponse().getWriter().write(hoResponse.toString());
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action HolonObject()");
			e.printStackTrace();
		}
	}
	
	public void showHolonObjects(){
		try {			
			ArrayList<String> hoListArray = new ArrayList<String>();
			HolonObject holonObject = null;
			new HolonCoordinatorAction().chooseCoordinatorValue();
			ArrayList<HolonObject> holonObjectList = getHolonObjectService().getAllHolonObject();
			for(int i=0; i<holonObjectList.size();i++){
				boolean isCoord=false;
				String ne_location;
				String sw_location;
				String holonColor;
				Integer holonObjectId;
			    holonObject = holonObjectList.get(i);
				ne_location = holonObject.getLatLngByNeLocation().getLatitude()+"~"+holonObject.getLatLngByNeLocation().getLongitude();
				sw_location = holonObject.getLatLngBySwLocation().getLatitude()+"~"+holonObject.getLatLngBySwLocation().getLongitude();
				holonColor ="black";
				PowerLine powerLine = getPowerLineService().getPowerLineByHolonObject(holonObject);
				HolonObject hco = null;
				if(holonObject.getHolon() != null) {
					hco = findConnectedHolonCoordinatorByHolon(holonObject.getHolon(), powerLine);
				}
				if(hco!=null) {
					holonColor=hco.getHolon().getColor();
				}
				holonObjectId = holonObject.getId();
				isCoord = holonObject.getIsCoordinator();
				log.info("The Color of the Holon is "+holonColor);
				hoListArray.add(holonObjectId+"#"+holonColor+"#"+ne_location+"^"+sw_location+"#"+isCoord+"*");
			}
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(hoListArray.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action showHolonObjects()");
			e.printStackTrace();
		}
	}

	private List<Boolean> getHolonPowerProductionDetails(Integer holonObjectId) {
		List<Boolean> productionDetails = new ArrayList<Boolean>();
		List<HolonElement> holonElementList = getHolonElementService().getHolonElements(getHolonObjectService().findById(holonObjectId));
		boolean hasPower=false;
		boolean hasPowerOn=false;
		for(int i=0;i<holonElementList.size();i++) {
			if(holonElementList.get(i).getHolonElementType().getProducer()) {
				hasPower=true;
				if(hasPower) {
					hasPowerOn=holonElementList.get(i).getHolonElementState().getId()==1?true:false;
				}
			}
		}
		productionDetails.add(hasPower);
		productionDetails.add(hasPowerOn);
		return productionDetails;
	}

	public void updateConnectedPowerLine(HolonObject holonObject, PowerLine powerLine) {
		powerLine.setHolonObject(holonObject);
		getPowerLineService().merge(powerLine);
	}
	
	public void getDetailForPowerSourceIcon() {
		try {
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;
			HolonObject hObject = getHolonObjectService().findById(holonObjectId);
			Double neLocationLat= hObject.getLatLngByNeLocation().getLatitude();
			Double swLocationLng=hObject.getLatLngBySwLocation().getLongitude();
			boolean hasPower= getHolonPowerProductionDetails(holonObjectId).get(0);
			boolean hasPowerOn= getHolonPowerProductionDetails(holonObjectId).get(1);
			log.info("The Value of hasPower is "+ hasPower+" for "+holonObjectId);
			log.info("The Value of hasPowerOn is "+ hasPowerOn+" for "+holonObjectId);
			String responseVal =hasPower+"*"+hasPowerOn+"*"+neLocationLat+"*"+swLocationLng;
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(responseVal.toString());
			
		} catch(Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in getDetailForPowerSourceIcon()");
			e.printStackTrace();
		}
	}
	
	
	public void getDataForSupplierDetails() {
		try {
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;
			HolonObject holonObject = getHolonObjectService().findById(holonObjectId);
			StringBuffer responseStr =new StringBuffer(holonObject.getLatLngByNeLocation().getLatitude()+"~"+holonObject.getLatLngByNeLocation().getLongitude()+"#");

			getResponse().setContentType("text/html");
			getResponse().getWriter().write(responseStr.toString());
		} catch(Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in getDataForSupplierDetails()");
			e.printStackTrace();
		}
	}

	public void getConnectedStatusForHolons() {
		try {
			Integer firstHolonObject = getRequest().getParameter("firstHolonObject")!=null?Integer.parseInt(getRequest().getParameter("firstHolonObject")):0;
			Integer secondHolonObject = getRequest().getParameter("secondHolonObject")!=null?Integer.parseInt(getRequest().getParameter("secondHolonObject")):0;
			HolonObject holonFirst = getHolonObjectService().findById(firstHolonObject);
			HolonObject holonSecond = getHolonObjectService().findById(secondHolonObject);
			boolean response = checkConnectivityBetweenHolonObjects(holonFirst,holonSecond);
			String responseStr="Failure";
			if(response) {
				responseStr="Success"; 
			}
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(responseStr);
		}
		catch(Exception e) {
			System.out.println("Exception occured at getConnectedStatusForHolons");
		}
	}
}