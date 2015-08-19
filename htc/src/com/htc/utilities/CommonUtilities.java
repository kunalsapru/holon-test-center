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
import com.opensymphony.xwork2.ActionContext;

public class CommonUtilities extends AbstractAction{

	private static final long serialVersionUID = 1L;
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
	
	
	public static int getPercent(int x, int y)
	{
		float xF=new Float(x);
		float yF=new Float(y);
		float perc= ((xF/yF)*100);
		log.info("x is "+x);
		log.info("y is "+y);
		log.info("percent is "+perc);
		return (int) perc;
		
	}
	
	public static String getLineColor(int percentCap)
	{
		String color="brown";
		
		if(0<percentCap && percentCap<=20)
		{
			color = "red";
		}else if(20<percentCap && percentCap<=50)
		{
			color = "yellow";
		}else if(percentCap>50 && percentCap<100)
		{
			color ="green";
		}else if(percentCap==100)
		{
			color ="black";
		}
		
		return color;
	}
	
/*	public static float randomCapGenerator(float maxCap)
	{
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
				minimumEnergyRequired = minimumEnergyRequired+holonElement.getHolonElementType().getMinCapacity();
				maximumEnergyRequired = maximumEnergyRequired+holonElement.getHolonElementType().getMaxCapacity();
				if(holonElement.getHolonElementState().getId()==1) {
					currentProduction = currentProduction + holonElement.getCurrentCapacity();
				}
			} else {
				minimumProductionCapacity = minimumProductionCapacity + holonElement.getHolonElementType().getMinCapacity();
				maximumProductionCapacity = maximumProductionCapacity + holonElement.getHolonElementType().getMaxCapacity();
				if(holonElement.getHolonElementState().getId()==1) {
					originalEnergyRequired=originalEnergyRequired+holonElement.getCurrentCapacity();
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
		
		return holonObjectEnergyDetails;
	}
	
	public Map<String, String> getHolonEnergyDetails(HolonCoordinator holonCoordinator) {
		Map<String, String> holonEnergyDetails = new TreeMap<String, String>();
		ArrayList<HolonObject> holonObjectListByCoordinator = getHolonObjectService().findByHCoordinator(holonCoordinator);
		HolonObject holonObjectOfCoordinator = holonCoordinator.getHolonObject();
		Integer holonObjectOfCoordinatorId = holonObjectOfCoordinator.getId();
		
		//Variables to capture Holon Details
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

		noOfHolonObjects = holonObjectListByCoordinator.size();
		for(HolonObject holonObject : holonObjectListByCoordinator) {
			hoObjectIdList.add(new Integer(holonObject.getId()).toString().concat("~").concat(holonObject.getHolonObjectType().getName()));
			Map<String, Integer> holonObjectEnergyDetails = getHolonObjectEnergyDetails(holonObject);
			minimumProductionCapacityHolon = minimumProductionCapacityHolon + holonObjectEnergyDetails.get("minimumProductionCapacity");
			maximumProductionCapacityHolon = maximumProductionCapacityHolon + holonObjectEnergyDetails.get("maximumProductionCapacity");
			currentProductionHolon = currentProductionHolon + holonObjectEnergyDetails.get("currentProduction");
			minimumEnergyRequiredHolon = minimumEnergyRequiredHolon + holonObjectEnergyDetails.get("minimumEnergyRequired");
			maximumEnergyRequiredHolon = maximumEnergyRequiredHolon + holonObjectEnergyDetails.get("maximumEnergyRequired");
			currentEnergyRequiredHolon = currentEnergyRequiredHolon + holonObjectEnergyDetails.get("currentEnergyRequired");
			originalEnergyRequiredHolon = originalEnergyRequiredHolon + holonObjectEnergyDetails.get("originalEnergyRequired");
			flexibilityHolon = flexibilityHolon + holonObjectEnergyDetails.get("flexibility");
		}
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
		holonEnergyDetails.put("holonObjectList", holonObjectList.toString());
		
		return holonEnergyDetails;
	}
	
}
