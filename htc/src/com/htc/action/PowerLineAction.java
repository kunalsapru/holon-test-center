package com.htc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.utilities.CommonUtilities;
import com.htc.utilities.ConstantValues;


public class PowerLineAction extends CommonUtilities {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonElementTypeAction.class);

	public void drawPowerLine(){
		
		try{
			
			Boolean isConnected = getRequest().getParameter("isConnected")!=null?Boolean.parseBoolean(getRequest().getParameter("isConnected")):false;
			Integer maxCapacity = getRequest().getParameter("maxCapacity")!=null?Integer.parseInt(getRequest().getParameter("maxCapacity")):0;
			Integer currentCapacity = getRequest().getParameter("currentCapacity")!=null?Integer.parseInt(getRequest().getParameter("currentCapacity")):maxCapacity;
			String powerLineType = getRequest().getParameter("powerLineType")!=null?getRequest().getParameter("powerLineType"):ConstantValues.MAINLINE;
			String reasonDown = getRequest().getParameter("reasonDown")!=null?getRequest().getParameter("reasonDown"):"";
			Integer powerSourceId= getRequest().getParameter("powerSourceId")!=null?Integer.parseInt(getRequest().getParameter("powerSourceId")):1;
			Double latStart = getRequest().getParameter("latStart")!=null?Double.parseDouble(getRequest().getParameter("latStart")):0D;
			Double lngStart = getRequest().getParameter("lngStart")!=null?Double.parseDouble(getRequest().getParameter("lngStart")):0D;
			Double latEnd = getRequest().getParameter("latEnd")!=null?Double.parseDouble(getRequest().getParameter("latEnd")):0D;
			Double lngEnd = getRequest().getParameter("lngEnd")!=null?Double.parseDouble(getRequest().getParameter("lngEnd")):0D;
			
			LatLng StartLatLng = new LatLng(latStart, lngStart);
			LatLng EndLatLng = new LatLng(latEnd, lngEnd);
			
			int newStartLatLngId = getLatLngService().persist(StartLatLng);
			int newEndLatLngId = getLatLngService().persist(EndLatLng);
			
			LatLng savedStartLatLng=getLatLngService().findById(newStartLatLngId);
			LatLng savedEndLatLng=getLatLngService().findById(newEndLatLngId);
			PowerSource powerSource = getPowerSourceService().findById(powerSourceId);
			currentCapacity= (int) CommonUtilities.randomCapGenerator(maxCapacity);
			log.info("Current Capacity "+currentCapacity);
			PowerLine powerLine = new PowerLine();
			powerLine.setCurrentCapacity(currentCapacity);
			powerLine.setIsConnected(isConnected);
			powerLine.setLatLngByDestination(savedEndLatLng);
			powerLine.setLatLngBySource(savedStartLatLng);
			powerLine.setMaximumCapacity(maxCapacity);
			powerLine.setPowerSource(null);
			powerLine.setReasonDown(reasonDown);
			powerLine.setType(powerLineType);
			powerLine.setPowerSource(powerSource);
			
			Integer newPowerLineID = getPowerLineService().persist(powerLine);
			
			String color = CommonUtilities.getLineColor(CommonUtilities.getPercent(currentCapacity,maxCapacity));
			
			log.info("NewLy Generated powerLine  ID --> "+newPowerLineID);
				
			getResponse().setContentType("text/html");
			StringBuffer plResponse = new StringBuffer();
			plResponse.append(newPowerLineID+"!");
			plResponse.append(color);				
			getResponse().getWriter().write(plResponse.toString());	
			
			
			
			
		}catch(Exception e)
		{
			log.info("Exception "+e.getMessage()+" occurred in action drawPowerLine()");
			e.printStackTrace();

		}
		
		
		
	}
	
	public void showPowerLines(){
		try {
			
			ArrayList<PowerLine> powerLineList = getPowerLineService().getAllPowerLine();
			ArrayList<String> powerLineListArray = new ArrayList<String>();
			String startLocation;
			String endLocation;
			String color;
			log.info("No of PowerLines "+powerLineList.size());
			for(int i=0; i<powerLineList.size();i++){
				PowerLine  powerLine = powerLineList.get(i);
				log.info("PowerLine Id: "+powerLine.getId());
				startLocation = powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude();
				endLocation = powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude();
				color= CommonUtilities.getLineColor(CommonUtilities.getPercent(powerLine.getCurrentCapacity(),powerLine.getMaximumCapacity())); 				
				powerLineListArray.add(startLocation+"^"+endLocation+"!"+color+"!"+powerLine.getId()+"*");
			}
			
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(powerLineListArray.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action showPowerLine()");
			e.printStackTrace();
		}
	}
	
	
	
	public void getPowerLineInfo ()
	
	{		
		try {

			Integer powerLineId = getRequest().getParameter("powerLineId")!=null?Integer.parseInt(getRequest().getParameter("powerLineId")):0;
			PowerLine  powerLine = getPowerLineService().findById(powerLineId);
			log.info("PowerLine Id: "+powerLine.getId());
		
			//Calling the response function and setting the content type of response.
			StringBuffer respStr= new StringBuffer("");
			respStr.append(powerLine.isIsConnected()+"*");
			respStr.append(powerLine.getId()+"*");
			respStr.append(powerLine.getMaximumCapacity()+"*");
			respStr.append(powerLine.getCurrentCapacity()+"*");
			respStr.append(powerLine.getType()+"*");
			respStr.append(powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude()+"*");
			respStr.append(powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude());
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(respStr.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action showPowerLine()");
			e.printStackTrace();
		}
		
	}
	
	public void connectToPowerSource(HolonObject holonObject){
		
		try {
			LatLng doorOfHolon = holonObject.getLatLngByDoorLocation();
			LatLng intersection;
			HolonObjectAction holonObjectAction = new HolonObjectAction();
			PowerLine powerLine = holonObjectAction.getNearestLine(doorOfHolon);
			intersection = getIntersectionPointOnTheLine(doorOfHolon,powerLine);
			
			holonObjectAction.updateConnectedPowerLine(holonObject,powerLine);
			
			StringBuffer connectToPowerSourceResponse = new StringBuffer();
			
			connectToPowerSourceResponse.append(doorOfHolon.toString() + "!");
			connectToPowerSourceResponse.append(intersection.toString());
			
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(connectToPowerSourceResponse.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action connectToPowerSource()");
			e.printStackTrace();
		}
	}

	private LatLng getIntersectionPointOnTheLine(LatLng doorOfHolon,
			PowerLine powerLine) {
		LatLng intersectionPoint = null;
		return intersectionPoint;
	}
	
	
	public Map<String, PowerLine> createPowerLinesUponSwitchAdd(Integer powerLineId, LatLng switchLatLng2)
	{
		
		PowerLine powerLineA = getPowerLineService().findById(powerLineId);
		LatLng powerLineAEnd=switchLatLng2;
		LatLng powerLineBStart=switchLatLng2;
		LatLng powerLineBEnd=powerLineA.getLatLngByDestination();		
		powerLineA.setLatLngByDestination(powerLineAEnd);
		getPowerLineService().merge(powerLineA);
		PowerLine powerLineB = new PowerLine();
		powerLineB.setCurrentCapacity(powerLineA.getCurrentCapacity());
		powerLineB.setIsConnected(powerLineA.isIsConnected());
		powerLineB.setLatLngByDestination(powerLineBEnd);
		powerLineB.setLatLngBySource(powerLineBStart);
		powerLineB.setMaximumCapacity(powerLineA.getMaximumCapacity());
		powerLineB.setReasonDown(powerLineA.getReasonDown());
		powerLineB.setType(powerLineA.getType());
		powerLineB.setPowerSource(powerLineA.getPowerSource());		
		getPowerLineService().persist(powerLineB);
		//Update any switch connected to new Powerline
		new PowerSwitchAction().setNewPowerLineForExistingSwitch(powerLineB);		
		Map<String, PowerLine> powerLineMap = new HashMap<String, PowerLine>();
		powerLineMap.put("powerLineA", powerLineA);
		powerLineMap.put("powerLineB", powerLineB);
		
		return powerLineMap;
	}
	
	
	public void updatePowerLine()
	{
		try {
			String startLocation;
			String endLocation;
			String color;
			Integer powerLineId = getRequest().getParameter("powerLineId")!=null?Integer.parseInt(getRequest().getParameter("powerLineId")):0;
			PowerLine  powerLine =  getPowerLineService().findById(powerLineId);
			log.info("PowerLine Id: "+powerLine.getId());
			startLocation = powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude();
			endLocation = powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude();
			color= CommonUtilities.getLineColor(CommonUtilities.getPercent(powerLine.getCurrentCapacity(),powerLine.getMaximumCapacity())); 				
			String resp=startLocation+"^"+endLocation+"!"+color+"!"+powerLine.getId()+"!"+powerLine.getMaximumCapacity();			
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(resp);
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action showPowerLine()");
			e.printStackTrace();
		}
	}
	
	public void editPowerLine()
	{
		try {
			Integer maxCapacity = getRequest().getParameter("maxCapacity")!=null?Integer.parseInt(getRequest().getParameter("maxCapacity")):0;
			Integer powerLineId = getRequest().getParameter("powerLineId")!=null?Integer.parseInt(getRequest().getParameter("powerLineId")):0;
			PowerLine  powerLine =  getPowerLineService().findById(powerLineId);
			powerLine.setMaximumCapacity(maxCapacity);
			powerLine.setCurrentCapacity((int) CommonUtilities.randomCapGenerator(maxCapacity));
			getPowerLineService().merge(powerLine);
			String color=CommonUtilities.getLineColor(CommonUtilities.getPercent(powerLine.getCurrentCapacity(),powerLine.getMaximumCapacity()));
			
			
			StringBuffer respStr= new StringBuffer("");
			respStr.append(powerLine.isIsConnected()+"*");
			respStr.append(powerLine.getId()+"*");
			respStr.append(powerLine.getMaximumCapacity()+"*");
			respStr.append(powerLine.getCurrentCapacity()+"*");
			respStr.append(powerLine.getType()+"*");
			respStr.append(powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude()+"*");
			respStr.append(powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude()+"*");
			respStr.append(color);
			String infoString="<b>Connected: </b>"+powerLine.isIsConnected()+".<br>"+
					"<b>PowerLine Id: </b>"+powerLine.getId() +".<br>"+
					"<b>Maximum Capacity: </b>"+powerLine.getMaximumCapacity()+".<br>"+
					"<b>Current Capacity: </b>"+powerLine.getCurrentCapacity()+".<br>"+
					"<b>PowerLine Type: </b>"+powerLine.getType()+".<br>"+
					"<b>Start Location: </b>"+powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude()+".<br>"+
					"<b>End Location: </b>"+powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude()+".<br>"+
					"<span class='button' id='editPowerLineObject'><i class='fa fa-pencil-square-o'></i>&nbsp;&nbsp;Edit Power Line</span>";			
			String resp=infoString+"*"+color;		
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		getResponse().getWriter().write(resp);
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action showPowerLine()");
			e.printStackTrace();
		}
	}
}