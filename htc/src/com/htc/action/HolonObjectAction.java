package com.htc.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonManager;
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
		Integer holonCoordinatorId = getRequest().getParameter("holonCoordinatorId")!=null?Integer.parseInt(getRequest().getParameter("holonCoordinatorId")):0;
		Integer canCommunicate = getRequest().getParameter("canCommunicate")!=null?Integer.parseInt(getRequest().getParameter("canCommunicate")):0;
		String holonManagerName = getRequest().getParameter("holonManager")!=null?getRequest().getParameter("holonManager"):"";
		Double latNE = getRequest().getParameter("latNE")!=null?Double.parseDouble(getRequest().getParameter("latNE")):0D;
		Double lngNE = getRequest().getParameter("lngNE")!=null?Double.parseDouble(getRequest().getParameter("lngNE")):0D;
		Double latSW = getRequest().getParameter("latSW")!=null?Double.parseDouble(getRequest().getParameter("latSW")):0D;
		Double lngSW = getRequest().getParameter("lngSW")!=null?Double.parseDouble(getRequest().getParameter("lngSW")):0D;
		
		LatLng NorthlatLng = new LatLng(latNE, lngNE);
		LatLng SouthlatLng = new LatLng(latSW, lngSW);
		LatLngAction latLngAct= new LatLngAction();
		Integer NorthlocationId = latLngAct.saveLocation(NorthlatLng);
		Integer SouthlocationId = latLngAct.saveLocation(SouthlatLng);
		LatLng NorthlatLng2 = getLatLngService().findById(NorthlocationId);
		LatLng SouthlatLng2 = getLatLngService().findById(SouthlocationId);
		HolonObject holonObject = new HolonObject(); // Creating HolonObject object to store values

		HolonCoordinator holonCoordinator = getHolonCoordinatorService().findById(holonCoordinatorId);
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
		if(holonCoordinatorId!=0)
		{
		new HolonCoordinatorAction().chooseCoordinator(holonCoordinatorId);				
		}
		HolonManager holonManager = new HolonManager();
		holonManager.setName(holonManagerName);
		Integer hmId = getHolonManagerService().persist(holonManager);
		HolonManager holonManager2 = getHolonManagerService().findById(hmId);
		
		
		holonObject.setLatLngByNeLocation(NorthlatLng2);
		holonObject.setLatLngBySwLocation(SouthlatLng2);
		if(holonCoordinatorId!=0)
		{
		holonObject.setHolonCoordinator(holonCoordinator);
		}
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.setLineConnectedState(false);
		holonObject.setHolonManager(holonManager2);
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
	
	public void getHolonObjectInfoWindow()
	{
		try {
			log.error("Holon Object Id is # "+getRequest().getParameter("holonObjectId"));
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;			
			HolonObject holonObject2 = getHolonObjectService().findById(holonObjectId);
			HolonCoordinator hc= holonObject2.getHolonCoordinator();
			String holonCoordinatorName_Holon="Not Part of any Holon";
			String holonColor="black";
			Integer coordinatorHolonId=0;
			HolonObject coOrdHolon=null;
			String coOredNeLocation="";
			if(hc!=null){
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
			Integer noOfElem=0;
			Integer minEnergReq=0;
			Integer maxEnergyReq=0;
			Integer cuEnergyRequired=0;
			Integer minProdCap=0;
			Integer maxProdCap=0;
			Integer cuProd=0;
			//Variables to capture Holon Details
			Integer noOfHolonObjects=0;
			ArrayList<String> hoObjectIdList = new ArrayList<String>();
			Integer minProdCapHolon=0;
			Integer maxProdCapHolon=0;
			Integer cuProdHolon=0;
			Integer minEnergReqHolon=0;
			Integer maxEnergyReqHolon=0;
			Integer cuEnergyRequiredHolon=0;
			StringBuffer hoListString=new StringBuffer("");
			
			ArrayList<HolonElement> elmList= getHolonElementService().getHolonElements(getHolonObjectService().findById(holonObjectId));
			noOfElem=elmList.size();
			for(int i=0;i<noOfElem;i++)
			{
				
				if(elmList.get(i).getHolonElementType().getProducer())
				{
					minProdCap =minProdCap + elmList.get(i).getHolonElementType().getMinCapacity();
					maxProdCap =maxProdCap + elmList.get(i).getHolonElementType().getMaxCapacity();
					log.info("Abhinav is fool "+elmList.get(i).getHolonElementState().getName()+elmList.get(i).getCurrentCapacity()+"ram");
					if(elmList.get(i).getHolonElementState().getId()==1)
					{
						
					cuProd = cuProd + elmList.get(i).getCurrentCapacity();
					}
				}
				else
				{
					minEnergReq=minEnergReq+elmList.get(i).getHolonElementType().getMinCapacity();
					maxEnergyReq=maxEnergyReq+elmList.get(i).getHolonElementType().getMaxCapacity();
					if(elmList.get(i).getHolonElementState().getId()==1)
					{
					cuEnergyRequired=cuEnergyRequired+elmList.get(i).getCurrentCapacity();
					}
				}
			}
			
			HolonObject hObject = getHolonObjectService().findById(holonObjectId);
			HolonCoordinator hCoordinator =  hObject.getHolonCoordinator();
			if(hCoordinator != null && holonObjectId == hCoordinator.getHolonObject().getId())
			{
				ArrayList<HolonObject> hoList=getHolonObjectService().findByHCoordinator(hCoordinator);				
				noOfHolonObjects = hoList.size();
				Iterator<HolonObject> iterator = hoList.iterator(); 
				while(iterator.hasNext())
				{
					HolonObject ho=(HolonObject) iterator.next();
					hoObjectIdList.add(new Integer(ho.getId()).toString().concat("~").concat(ho.getHolonObjectType().getName()));
					ArrayList<HolonElement> elemList= getHolonElementService().getHolonElements(ho);
					for(int i=0;i<elemList.size();i++)
					{
						
						if(elemList.get(i).getHolonElementType().getProducer())
						{
							minProdCapHolon = minProdCapHolon + elemList.get(i).getHolonElementType().getMinCapacity();
							maxProdCapHolon = maxProdCapHolon + elemList.get(i).getHolonElementType().getMaxCapacity();
							if(elemList.get(i).getHolonElementState().getId()==1)
							{
							cuProdHolon =  cuProdHolon + elemList.get(i).getCurrentCapacity();
							}
						}
						else
						{
							minEnergReqHolon=minEnergReq+elemList.get(i).getHolonElementType().getMinCapacity();
							maxEnergyReqHolon=maxEnergyReq+elemList.get(i).getHolonElementType().getMaxCapacity();
							if(elemList.get(i).getHolonElementState().getId()==1)
							{
							cuEnergyRequiredHolon=cuEnergyRequired+elemList.get(i).getCurrentCapacity();
							}
						}
					}
					
				}
				log.error("The coordinator Is is "+coordinatorHolonId);
				hoObjectIdList.remove(coordinatorHolonId.toString().concat("~").concat(getHolonObjectService().findById(coordinatorHolonId).getHolonObjectType().getName()));
				Iterator<String> itr = hoObjectIdList.iterator(); 
				hoListString.append("<option value=\"Select Holon\" id= \"infoWinOpt\" selected>Select Holon Object</option>");
				while(itr.hasNext())
				{
					String valueString = itr.next();
					String hoId= valueString.split("~")[0];
					String hoObjectType =valueString.split("~")[1];
					
					hoListString.append(
							"<option value="+hoId+" id= \"infoWinOpt\">"+hoObjectType+" (Id:"+hoId+")"+"</option>"
							);
					
				}
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
			hoResponse.append(holonObject2.getHolonManager().getName()+"!");
			hoResponse.append(noOfElem+"!");
			hoResponse.append(minEnergReq+"!");
			hoResponse.append(maxEnergyReq+"!");
			hoResponse.append(cuEnergyRequired+"!");
			hoResponse.append(minProdCap+"!");
			hoResponse.append(maxProdCap+"!");
			hoResponse.append(cuProd+"!");
			hoResponse.append(noOfHolonObjects+"!");
			hoResponse.append(minEnergReqHolon+"!");
			hoResponse.append(maxEnergyReqHolon+"!");
			hoResponse.append(cuEnergyRequiredHolon+"!");
			hoResponse.append(minProdCapHolon+"!");
			hoResponse.append(maxProdCapHolon+"!");
			hoResponse.append(cuProdHolon+"!");
			hoResponse.append(hoListString+"!");
			hoResponse.append(canCommunicate+"!");
			hoResponse.append(coOredNeLocation);
				
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
		String holonManagerName = getRequest().getParameter("holonManager")!=null?getRequest().getParameter("holonManager"):"";
		
		HolonObject holonObject = getHolonObjectService().findById(hiddenHolonObjectId);
		HolonCoordinator holonCoordinator = getHolonCoordinatorService().findById(holonCoordinatorId);
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
				
		
	
		holonObject.setHolonCoordinator(holonCoordinator);
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.getHolonManager().setName(holonManagerName);
		holonObject.setCanCommunicate(canCommunicate==1?true:false);
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		getHolonObjectService().merge(holonObject);
		
		new HolonCoordinatorAction().chooseCoordinatorValue();
		HolonObject updatedHolonObject=getHolonObjectService().findById(hiddenHolonObjectId);
		
		HolonCoordinator updatedHoCo=updatedHolonObject.getHolonCoordinator();
		String holonColor="black";
		Integer coordinatorHolonId=0;
		String hc_ne_location="";
		if(updatedHoCo!=null)
		{
		holonColor=updatedHolonObject.getHolonCoordinator().getHolon().getColor();
		coordinatorHolonId=updatedHoCo.getHolonObject().getId();
		hc_ne_location=updatedHoCo.getHolonObject().getLatLngByNeLocation().getLatitude()+"~"+updatedHoCo.getHolonObject().getLatLngByNeLocation().getLongitude();
		}
		String ne_location = updatedHolonObject.getLatLngByNeLocation().getLatitude()+"~"+holonObject.getLatLngByNeLocation().getLongitude();
		String sw_location = updatedHolonObject.getLatLngBySwLocation().getLatitude()+"~"+holonObject.getLatLngBySwLocation().getLongitude();
		Boolean lineConnectedState = updatedHolonObject.getLineConnectedState();		
		String holonObjectTypeName = updatedHolonObject.getHolonObjectType().getName();
		log.info("Maaaarrrrrrrraaaaa "+updatedHolonObject.getCanCommunicate());
		String canCommunicateVal=updatedHolonObject.getCanCommunicate()==true?"Yes":"No";
		boolean isCoord=false;
		if(coordinatorHolonId==updatedHolonObject.getId())
		{
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
		hoResponse.append(holonManagerName+"!");
		hoResponse.append(canCommunicateVal);
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");		
		getResponse().getWriter().write(hoResponse.toString());
		
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action createHolonObject()");
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
		holonObject.setPowerLine(powerLine);
		getHolonObjectService().merge(holonObject);
		
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
}


	
