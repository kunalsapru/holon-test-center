package com.htc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.HolonObjectType;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.utilities.CommonUtilities;

public class HolonObjectAction extends CommonUtilities {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonObjectAction.class);
	
	public void createHolonObject(){

		try {
		Integer holonObjectTypeId = getRequest().getParameter("holonObjectType")!=null?Integer.parseInt(getRequest().getParameter("holonObjectType")):0;
		Integer holonCoordinatorId = getRequest().getParameter("holonCoordinatorId")!=null?Integer.parseInt(getRequest().getParameter("holonCoordinatorId")):0;
		Integer canCommunicate = getRequest().getParameter("canCommunicate")!=null?Integer.parseInt(getRequest().getParameter("canCommunicate")):0;
		Double latNE = getRequest().getParameter("latNE")!=null?Double.parseDouble(getRequest().getParameter("latNE")):0D;
		Double lngNE = getRequest().getParameter("lngNE")!=null?Double.parseDouble(getRequest().getParameter("lngNE")):0D;
		Double latSW = getRequest().getParameter("latSW")!=null?Double.parseDouble(getRequest().getParameter("latSW")):0D;
		Double lngSW = getRequest().getParameter("lngSW")!=null?Double.parseDouble(getRequest().getParameter("lngSW")):0D;
		
		LatLng NorthlatLng = new LatLng(latNE, lngNE);
		LatLng SouthlatLng = new LatLng(latSW, lngSW);
		Integer NorthlocationId = saveLocation(NorthlatLng);
		Integer SouthlocationId = saveLocation(SouthlatLng);
		LatLng NorthlatLng2 = getLatLngService().findById(NorthlocationId);
		LatLng SouthlatLng2 = getLatLngService().findById(SouthlocationId);
		HolonObject holonObject = new HolonObject(); // Creating HolonObject object to store values

		HolonCoordinator holonCoordinator = getHolonCoordinatorService().findById(holonCoordinatorId);
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
		if(holonCoordinatorId!=0)
		{
		new HolonCoordinatorAction().chooseCoordinator(holonCoordinatorId);				
		}
		
		
		holonObject.setLatLngByNeLocation(NorthlatLng2);
		holonObject.setLatLngBySwLocation(SouthlatLng2);
		if(holonCoordinatorId!=0)
		{
		holonObject.setHolonCoordinator(holonCoordinator);
		}
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.setLineConnectedState(false);
		holonObject.setCanCommunicate(canCommunicate==1?true:false);
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newHolonObjectID = getHolonObjectService().persist(holonObject);
		
		new HolonCoordinatorAction().chooseCoordinatorValue();		
		log.info("NewLy Generated Holon Object ID --> "+newHolonObjectID);
		HolonObject holonObject2 = getHolonObjectService().findById(newHolonObjectID);
		Integer coordHolonObjId=0;
		String hc_ne_location="";
		Integer noOfHolons=0;
		String holonColor="black";
		if(holonCoordinatorId!=0)
		{
		coordHolonObjId=holonObject2.getHolonCoordinator().getHolonObject().getId();
		hc_ne_location=holonObject2.getHolonCoordinator().getHolonObject().getLatLngByNeLocation().getLatitude()+"~"+holonObject2.getHolonCoordinator().getHolonObject().getLatLngByNeLocation().getLongitude();
		noOfHolons=getHolonObjectService().findByHCoordinator(holonObject2.getHolonCoordinator()).size();
		holonColor= holonObject2.getHolonCoordinator().getHolon().getColor();
		}
		
		boolean isCoord=false;
		if(coordHolonObjId==holonObject2.getId())
		{
			isCoord=true;
		}
		
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
		
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" occurred in action createHolonObject()");
			e.printStackTrace();
		}
	}
	
	public void getHolonObjectInfoWindow() {
		try {
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;			
			HolonObject holonObject2 = getHolonObjectService().findById(holonObjectId);
			HolonCoordinator hc= holonObject2.getHolonCoordinator();
			String holonCoordinatorName_Holon="Not Part of any Holon";
			String holonColor="black";
			Integer coordinatorHolonId=0;
			HolonObject coOrdHolon=null;
			String coOredNeLocation="";
			if(hc!=null && hc.getHolonObject() != null) {
				holonCoordinatorName_Holon = holonObject2.getHolonCoordinator().getName().concat("_"+holonObject2.getHolonCoordinator().getHolon().getName());
				holonColor= holonObject2.getHolonCoordinator().getHolon().getColor();
				coordinatorHolonId=holonObject2.getHolonCoordinator().getHolonObject().getId();
				coOrdHolon= getHolonObjectService().findById(coordinatorHolonId);			
				coOredNeLocation= coOrdHolon.getLatLngByNeLocation().getLatitude()+"~"+coOrdHolon.getLatLngByNeLocation().getLongitude();
			}
			String holonObjectTypeName = holonObject2.getHolonObjectType().getName();
			String ne_location = holonObject2.getLatLngByNeLocation().getLatitude()+"~"+holonObject2.getLatLngByNeLocation().getLongitude();
			String sw_location = holonObject2.getLatLngBySwLocation().getLatitude()+"~"+holonObject2.getLatLngBySwLocation().getLongitude();			
			String lineConnectedState = holonObject2.getLineConnectedState()==true?"Yes":"No";
			
			
			String canCommunicate = holonObject2.getCanCommunicate()==true?"Yes":"No"; 
			
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
			Integer flexibility = holonObjectEnergyDetails.get("flexibility");
			
			
			HolonObject hObject = getHolonObjectService().findById(holonObjectId);
			HolonCoordinator hCoordinator =  hObject.getHolonCoordinator();
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

			if(hCoordinator != null && holonObjectId == hCoordinator.getHolonObject().getId()) {
				Map<String, String> holonEnergyDetails = getHolonEnergyDetails(hCoordinator);
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
			System.out.println(hoResponse.toString());
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
		Integer holonCoordinatorId = getRequest().getParameter("holonCoordinatorId")!=null?Integer.parseInt(getRequest().getParameter("holonCoordinatorId")):0;
		Integer canCommunicate = getRequest().getParameter("canCommunicate")!=null?Integer.parseInt(getRequest().getParameter("canCommunicate")):0;
		
		HolonObject holonObject = getHolonObjectService().findById(hiddenHolonObjectId);
		HolonCoordinator holonCoordinator = getHolonCoordinatorService().findById(holonCoordinatorId);
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
				
		
	
		holonObject.setHolonCoordinator(holonCoordinator);
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.setCanCommunicate(canCommunicate==1?true:false);
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		getHolonObjectService().merge(holonObject);
		
		new HolonCoordinatorAction().chooseCoordinatorValue();
		HolonObject updatedHolonObject=getHolonObjectService().findById(hiddenHolonObjectId);
		
		HolonCoordinator updatedHoCo=updatedHolonObject.getHolonCoordinator();
		String holonColor="black";
		Integer coordinatorHolonId=0;
		String hc_ne_location="";
		if(updatedHoCo!=null) {
			holonColor=updatedHolonObject.getHolonCoordinator().getHolon().getColor();
			coordinatorHolonId=updatedHoCo.getHolonObject().getId();
			hc_ne_location=updatedHoCo.getHolonObject().getLatLngByNeLocation().getLatitude()+"~"+updatedHoCo.getHolonObject().getLatLngByNeLocation().getLongitude();
		}
		String ne_location = updatedHolonObject.getLatLngByNeLocation().getLatitude()+"~"+holonObject.getLatLngByNeLocation().getLongitude();
		String sw_location = updatedHolonObject.getLatLngBySwLocation().getLatitude()+"~"+holonObject.getLatLngBySwLocation().getLongitude();
		Boolean lineConnectedState = updatedHolonObject.getLineConnectedState();		
		String holonObjectTypeName = updatedHolonObject.getHolonObjectType().getName();
		String canCommunicateVal=updatedHolonObject.getCanCommunicate()==true?"Yes":"No";
		boolean isCoord=false;
		if(coordinatorHolonId==updatedHolonObject.getId()) {
			isCoord=true;
		}
		StringBuffer hoResponse = new StringBuffer();
		hoResponse.append(holonColor+"!");
		hoResponse.append(holonObject.getId()+"!");
		hoResponse.append(holonCoordinator+"!");
		hoResponse.append(holonObjectTypeName+"!");
		hoResponse.append(ne_location+"!");
		hoResponse.append(sw_location+"!");
		hoResponse.append(lineConnectedState+"!");
		hoResponse.append(coordinatorHolonId+"!");
		hoResponse.append(isCoord+"!");
		hoResponse.append(hc_ne_location+"!");
		hoResponse.append(canCommunicateVal);
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
				Integer coordHolonObjectId=0;		
			    holonObject = holonObjectList.get(i);
				ne_location = holonObject.getLatLngByNeLocation().getLatitude()+"~"+holonObject.getLatLngByNeLocation().getLongitude();
				sw_location = holonObject.getLatLngBySwLocation().getLatitude()+"~"+holonObject.getLatLngBySwLocation().getLongitude();
				holonColor ="black";
				HolonCoordinator hco=holonObject.getHolonCoordinator();
				if(hco!=null)
				{
					holonColor=hco.getHolon().getColor();
					coordHolonObjectId=hco.getHolonObject().getId();
				}
				holonObjectId=holonObject.getId();
				if(coordHolonObjectId==holonObjectId)
				{
					isCoord=true;
				}
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
		for(int i=0;i<holonElementList.size();i++)
		{
			if(holonElementList.get(i).getHolonElementType().getProducer())
			{
				hasPower=true;
				if(hasPower)
				{
					hasPowerOn=holonElementList.get(i).getHolonElementState().getId()==1?true:false;
				}
			}
		}
		productionDetails.add(hasPower);
		productionDetails.add(hasPowerOn);
		return productionDetails;
	}

	public void updateConnectedPowerLine(HolonObject holonObject,
			PowerLine powerLine) {
		powerLine.setHolonObject(holonObject);
		getPowerLineService().merge(powerLine);
		
	}
	
	public void getDetailForPowerSourceIcon()
	{
		try{
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
			
		} catch(Exception e)
		{
			log.info("Exception "+e.getMessage()+" occurred in getDetailForPowerSourceIcon()");
			e.printStackTrace();
		}
	}
	
	
	public void getDataForSupplierDetails()
	{
		try{
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;
			HolonObject hObject = getHolonObjectService().findById(holonObjectId);
			Integer powReqByHolon= getPowerRequiredByHolon(hObject);
			Map<Integer,Integer> powProducedByPowSourceMap=new HashMap<Integer,Integer>();
			Map<Integer,Integer> powProducedByHOProdMap=new HashMap<Integer,Integer>();
			HolonCoordinator hoc= hObject.getHolonCoordinator();
			ArrayList<PowerSource> pSrcList = new ArrayList<PowerSource>();
			ArrayList<HolonObject> hoList = new ArrayList<HolonObject>();
			if(hoc!=null)
			{
				pSrcList=getPowerSourceService().findByHCoordinator(hoc);
				hoList=getHolonObjectService().findByHCoordinator(hoc);
			}
			 for(int i=0;i<pSrcList.size();i++)
			 {
				 PowerSource pSrc=pSrcList.get(i);
				 if(pSrc.getStatus()==true)
				 {
					 Integer powProduced=pSrc.getCurrentProduction();
					 if(powProduced>0)
					 {
					 powProducedByPowSourceMap.put(pSrc.getId(), powProduced);
					 }
					 
				 }
			 }
			 
			 for(int i=0;i<hoList.size();i++)
			 {
				 HolonObject ho=hoList.get(i);
				 Integer pwProdByHO= getPowerProducedByHolon(ho);
				 if(pwProdByHO>0)
				 {
				 powProducedByHOProdMap.put(ho.getId(), pwProdByHO);
				 }
			 }
			StringBuffer responseStr =new StringBuffer( hObject.getLatLngByNeLocation().getLatitude()+"~"+hObject.getLatLngByNeLocation().getLongitude()+"#");
			Integer powReqValForItr= powReqByHolon;
			Set<Integer> pSrcKeySet=powProducedByPowSourceMap.keySet();
			Iterator<Integer> pSrcKeySetItr = pSrcKeySet.iterator();
			while(pSrcKeySetItr.hasNext() && powReqValForItr>0)
				{
					Integer cuKey=pSrcKeySetItr.next();
					Integer powerVal=powProducedByPowSourceMap.get(cuKey);
					powReqValForItr=powReqValForItr-powerVal;
					Integer powToAdd=0;
					if((powReqValForItr)>0)
					{
						powToAdd= powerVal;
					}
					else
					{
						powToAdd= powReqValForItr+powerVal;
					}
					
					PowerSource pSrcObj=getPowerSourceService().findById(cuKey);
					double centreLat= pSrcObj.getCenter().getLatitude();
					double centreLng=pSrcObj.getCenter().getLongitude();
					responseStr.append(cuKey+"!"+powToAdd+"!"+centreLat+"!"+centreLng+"!"+"Power Source"+"*");
				}
			Set<Integer> hProdKeySet=powProducedByHOProdMap.keySet();
			Iterator<Integer> hProdKeySetItr = hProdKeySet.iterator();
			while(hProdKeySetItr.hasNext() && powReqValForItr>0)
				{
					Integer cuKey=hProdKeySetItr.next();
					Integer powerVal=powProducedByHOProdMap.get(cuKey);
					powReqValForItr=powReqValForItr-powerVal;					
					Integer powToAdd=0;
					if((powReqValForItr)>0)
					{
						powToAdd= powerVal;
					}
					else
					{
						powToAdd= powReqValForItr+powerVal;
					}
					HolonObject hoObj=getHolonObjectService().findById(cuKey);
					double neLat= hoObj.getLatLngByNeLocation().getLatitude();
					double neLng=hoObj.getLatLngByNeLocation().getLongitude();
					responseStr.append(cuKey+"!"+powToAdd+"!"+neLat+"!"+neLng+"!"+"Holon Object"+"*");
					
				}
			System.out.println("The Abhinav Response Str for supplier Details is "+responseStr);
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(responseStr.toString());
			
		} catch(Exception e)
		{
			log.info("Exception "+e.getMessage()+" occurred in getDataForSupplierDetails()");
			e.printStackTrace();
		}
	}

	private Integer getPowerProducedByHolon(HolonObject ho) {
		ArrayList<HolonElement> heList= new ArrayList<HolonElement>();
		heList= getHolonElementService().getHolonElements(ho);
		Integer powProd=0;
		for(int i=0;i<heList.size();i++)
		{
			HolonElement helm=heList.get(i);
			if(helm.getHolonElementState().getId()==1 && helm.getHolonElementType().getProducer() ==true)
			{
				powProd=powProd+helm.getCurrentCapacity();
			}
		}
		return powProd;
	
	}

	private Integer getPowerRequiredByHolon(HolonObject hObject) {
		ArrayList<HolonElement> heList= new ArrayList<HolonElement>();
		heList= getHolonElementService().getHolonElements(hObject);
		Integer currentPow=0;
		for(int i=0;i<heList.size();i++)
		{
			HolonElement helm=heList.get(i);
			if(helm.getHolonElementState().getId()==1 && helm.getHolonElementType().getProducer()!=true)
			{
				currentPow=currentPow+helm.getCurrentCapacity();
			}
		}
		return currentPow;
	}
}


	
