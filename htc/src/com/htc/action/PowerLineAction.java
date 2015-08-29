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
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonElementTypeAction.class);

	public void drawPowerLine() {
		try {
			Boolean isConnected = getRequest().getParameter("isConnected")!=null?Boolean.parseBoolean(getRequest().getParameter("isConnected")):false;
			Integer maxCapacity = getRequest().getParameter("maxCapacity")!=null?Integer.parseInt(getRequest().getParameter("maxCapacity")):0;
			String powerLineType = getRequest().getParameter("powerLineType")!=null?getRequest().getParameter("powerLineType"):ConstantValues.MAINLINE;
			String reasonDown = getRequest().getParameter("reasonDown")!=null?getRequest().getParameter("reasonDown"):"";
			Double latStart = getRequest().getParameter("latStart")!=null?Double.parseDouble(getRequest().getParameter("latStart")):0D;
			Double lngStart = getRequest().getParameter("lngStart")!=null?Double.parseDouble(getRequest().getParameter("lngStart")):0D;
			Double latEnd = getRequest().getParameter("latEnd")!=null?Double.parseDouble(getRequest().getParameter("latEnd")):0D;
			Double lngEnd = getRequest().getParameter("lngEnd")!=null?Double.parseDouble(getRequest().getParameter("lngEnd")):0D;
			Integer powerLineForSubLine = getRequest().getParameter("powerLineForSubLine")!=null && getRequest().getParameter("powerLineForSubLine")!=""?
					Integer.parseInt(getRequest().getParameter("powerLineForSubLine")):0;
			Integer subLineHolonObjId=0;
			LatLng StartLatLng = new LatLng(latStart, lngStart);
			LatLng EndLatLng = new LatLng(latEnd, lngEnd);
			int newStartLatLngId = saveLocation(StartLatLng);
			int newEndLatLngId = saveLocation(EndLatLng);
			LatLng savedStartLatLng=getLatLngService().findById(newStartLatLngId);
			LatLng savedEndLatLng=getLatLngService().findById(newEndLatLngId);
			int currentCapacity= 0;
			PowerLine powerLine = new PowerLine();
			powerLine.setCurrentCapacity(currentCapacity);
			powerLine.setIsConnected(isConnected);
			powerLine.setLatLngByDestination(savedEndLatLng);
			powerLine.setLatLngBySource(savedStartLatLng);
			powerLine.setMaximumCapacity(maxCapacity);
			powerLine.setReasonDown(reasonDown);
			powerLine.setType(powerLineType);
			PowerLine powerLineA = null, powerLineB = null;
			String colorOfHolonObject="";
			if(powerLineType.equals(ConstantValues.SUBLINE)) {
				subLineHolonObjId=getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;
				powerLine.setHolonObject(getHolonObjectService().findById(subLineHolonObjId));
				/*This code is not required as per new DB changes. Recursive reference of power line has now been removed.
				 * powerLine.setPowerLine(getPowerLineService().findById(powerLineIdForsubLine));*/
				Map<String, PowerLine> powerLineMap = splitPowerLineByLocation(powerLineForSubLine,savedEndLatLng);
				powerLineA = powerLineMap.get("powerLineA");
				powerLineB = powerLineMap.get("powerLineB");
						
			} else if(powerLineType.equals(ConstantValues.POWERSUBLINE)) {
				subLineHolonObjId=getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;
				powerLine.setPowerSource(getPowerSourceService().findById(subLineHolonObjId));
				/*This code is not required as per new DB changes. Recursive reference of power line has now been removed.
				 * powerLine.setPowerLine(getPowerLineService().findById(powerLineIdForsubLine));*/
				Map<String, PowerLine> powerLineMap = splitPowerLineByLocation(powerLineForSubLine,savedEndLatLng);
				powerLineA = powerLineMap.get("powerLineA");
				powerLineB = powerLineMap.get("powerLineB");
			}
			Integer newPowerLineID = getPowerLineService().persist(powerLine);
			String color = CommonUtilities.getLineColor(CommonUtilities.getPercent(currentCapacity,maxCapacity));
			log.info("NewLy Generated powerLine  ID --> "+newPowerLineID);
			getResponse().setContentType("text/html");
			StringBuffer plResponse = new StringBuffer();
			plResponse.append(newPowerLineID+"!");
			plResponse.append(color);
			if(powerLineA != null && powerLineB != null) {
				plResponse.append("!"+powerLineA.getId()+"!");
				plResponse.append(powerLineB.getId());
			}
			
			//Code to make the current flow on addition of Sub line or Power line and Assign Holon Coordinator
			if(powerLineType.equals(ConstantValues.SUBLINE) || powerLineType.equals(ConstantValues.POWERSUBLINE)) {
				updateHolonObjectsAndPowerSources(newPowerLineID);
			}
			// Code to add color to holon object.
			if(powerLineType.equals(ConstantValues.SUBLINE))
			{
				colorOfHolonObject= getHolonObjectService().findById(subLineHolonObjId).getHolonCoordinator().getHolon().getColor();
				plResponse.append("!"+colorOfHolonObject+"!"+subLineHolonObjId);
			}
			getResponse().getWriter().write(plResponse.toString());	
		}catch(Exception e) {
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
	
	
	
	public void getPowerLineInfo () {		
		try {
			Integer powerLineId = getRequest().getParameter("powerLineId")!=null?Integer.parseInt(getRequest().getParameter("powerLineId")):0;
			PowerLine  powerLine = getPowerLineService().findById(powerLineId);
			ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineId);
			
			int indexToRemove = -1;
			for(int i=0; i < connectedPowerLines.size(); i++) {
				if(connectedPowerLines.get(i).getId().equals(powerLineId)) {
					indexToRemove = i;		
				}
			}
			if(indexToRemove !=-1) {
				connectedPowerLines.remove(indexToRemove);//Removing self power line link
			}
			StringBuffer powerLineIds = new StringBuffer();
			powerLineIds.append("");
			double middleLatitude = 0L;
			double middleLongitude = 0L;
			System.out.println("Selected Power Line = "+powerLine.getId());
			for(PowerLine powerLine2 : connectedPowerLines) {
				System.out.println("Connected lines "+powerLine2.getId());
				middleLatitude = (powerLine2.getLatLngBySource().getLatitude()+powerLine2.getLatLngByDestination().getLatitude())/2;
				middleLongitude = (powerLine2.getLatLngBySource().getLongitude()+powerLine2.getLatLngByDestination().getLongitude())/2;
				powerLineIds.append(powerLine2.getId()+"!"+middleLatitude+"^"+middleLongitude+"~");
			}
			if(powerLineIds.length() > 0) {
				powerLineIds = powerLineIds.deleteCharAt(powerLineIds.lastIndexOf("~"));
			}
			HolonObject subLineHolonObject = powerLine.getHolonObject();
			PowerSource subLinePowerSrc=powerLine.getPowerSource();
			Integer subLineHolonObjectId= 0;
			Integer subLinePowerSrcId=0;
			if(subLineHolonObject!=null) {
				subLineHolonObjectId = subLineHolonObject.getId();
			}
			
			if(subLinePowerSrc!=null) {
				subLinePowerSrcId=subLinePowerSrc.getId();
			}
			String color = CommonUtilities.getLineColor(CommonUtilities.getPercent(powerLine.getCurrentCapacity(),powerLine.getMaximumCapacity()));
			boolean isConnectedToHolonOrPower=checkConnectedStatusForLine(connectedPowerLines,powerLine);
			//Calling the response function and setting the content type of response.
			StringBuffer respStr= new StringBuffer("");
			respStr.append(isConnectedToHolonOrPower+"*");
			respStr.append(powerLine.getId()+"*");
			respStr.append(powerLine.getMaximumCapacity()+"*");
			respStr.append(powerLine.getCurrentCapacity()+"*");
			respStr.append(powerLine.getType()+"*");
			respStr.append(powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude()+"*");
			respStr.append(powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude()+"*");
			respStr.append(subLineHolonObjectId+"*");
			respStr.append(subLinePowerSrcId+"*");
			respStr.append(color+"*");
			respStr.append(powerLineIds);
			
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(respStr.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action getPowerLineInfo()");
			e.printStackTrace();
		}
	}
	
	public Map<String, PowerLine> splitPowerLineByLocation(Integer powerLineId, LatLng switchLatLng2) {
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
	
	
	public void updatePowerLine() {
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
			log.info("Exception "+e.getMessage()+" occurred in action updatePowerLine()");
			e.printStackTrace();
		}
	}
	
	public void editPowerLine() {
		try {
			Integer maxCapacity = getRequest().getParameter("maxCapacity")!=null?Integer.parseInt(getRequest().getParameter("maxCapacity")):0;
			Integer powerLineId = getRequest().getParameter("powerLineId")!=null?Integer.parseInt(getRequest().getParameter("powerLineId")):0;
			PowerLine  powerLine =  getPowerLineService().findById(powerLineId);
			powerLine.setMaximumCapacity(maxCapacity);
			powerLine.setCurrentCapacity(0);
			getPowerLineService().merge(powerLine);
			ArrayList<PowerLine> connectedPowerLines = connectedPowerLines(powerLineId);
			
			int indexToRemove = -1;
			for(int i=0; i < connectedPowerLines.size(); i++) {
				if(connectedPowerLines.get(i).getId().equals(powerLineId)) {
					indexToRemove = i;		
				}
			}
			if(indexToRemove !=-1) {
				connectedPowerLines.remove(indexToRemove);//Removing self power line link
			}
			StringBuffer powerLineIds = new StringBuffer();
			powerLineIds.append("");
			double middleLatitude = 0L;
			double middleLongitude = 0L;
			System.out.println("Selected Power Line = "+powerLine.getId());
			for(PowerLine powerLine2 : connectedPowerLines) {
				System.out.println("Connected lines "+powerLine2.getId());
				middleLatitude = (powerLine2.getLatLngBySource().getLatitude()+powerLine2.getLatLngByDestination().getLatitude())/2;
				middleLongitude = (powerLine2.getLatLngBySource().getLongitude()+powerLine2.getLatLngByDestination().getLongitude())/2;
				powerLineIds.append(powerLine2.getId()+"!"+middleLatitude+"^"+middleLongitude+"~");
			}
			if(powerLineIds.length() > 0) {
				powerLineIds = powerLineIds.deleteCharAt(powerLineIds.lastIndexOf("~"));
			}
			boolean isConnectedToHolonOrPower=checkConnectedStatusForLine(connectedPowerLines,powerLine);
			String color=CommonUtilities.getLineColor(CommonUtilities.getPercent(powerLine.getCurrentCapacity(),powerLine.getMaximumCapacity()));
			StringBuffer respStr= new StringBuffer("");
			respStr.append(isConnectedToHolonOrPower+"*");
			respStr.append(powerLine.getId()+"*");
			respStr.append(powerLine.getMaximumCapacity()+"*");
			respStr.append(powerLine.getCurrentCapacity()+"*");
			respStr.append(powerLine.getType()+"*");
			respStr.append(powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude()+"*");
			respStr.append(powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude()+"*");
			int holonObjectId = 0;
			int powerSourceId= 0;
			if(powerLine.getHolonObject() != null){
				holonObjectId = powerLine.getHolonObject().getId();
			}
			if(powerLine.getPowerSource() != null){
				powerSourceId = powerLine.getPowerSource().getId();
			}
			respStr.append(holonObjectId+"*");
			respStr.append(powerSourceId+"*");
			respStr.append(color+"*");
			respStr.append(powerLineIds);
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		getResponse().getWriter().write(respStr.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action editPowerLine()");
			e.printStackTrace();
		}
	}
	
}