package com.htc.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;
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
			
			//Temporarily cdreating hard coded power source
			PowerSource powerSource = getPowerSourceService().findById(powerSourceId);
			//End of temp code
			
			PowerLine powerLine = new PowerLine();
			powerLine.setCurrentCapacity(currentCapacity);
			powerLine.setIsConnected(isConnected);
			powerLine.setLatLngByDestination(savedEndLatLng);
			powerLine.setLatLngBySource(savedStartLatLng);
			powerLine.setMaximumCapacity(maxCapacity);
			powerLine.setPowerSource(null);
			powerLine.setReasonDown(reasonDown);
			powerLine.setType(powerLineType);
			
			Integer newPowerLineID = getPowerLineService().persist(powerLine);
			
			log.info("NewLy Generated powerLine  ID --> "+newPowerLineID);
			String powerSrcName = powerSource.getName();
			
			getResponse().setContentType("text/html");
			StringBuffer plResponse = new StringBuffer();
			plResponse.append(newPowerLineID+"!");
			plResponse.append(isConnected+"!");
			plResponse.append(maxCapacity+"!");
			plResponse.append(currentCapacity+"!");
			plResponse.append(powerLineType+"!");
			plResponse.append(reasonDown+"!");
			plResponse.append(powerSrcName+"!");
			plResponse.append(latStart+"!");
			plResponse.append(lngStart+"!");
			plResponse.append(latEnd+"!");
			plResponse.append(lngEnd);		
			
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
			PowerLine powerLine = null;
			String startLocation;
			String endLocation;
			log.error("No of PowerLines "+powerLineList.size());
			for(int i=0; i<powerLineList.size();i++){
				powerLine = powerLineList.get(i);
				startLocation = powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude();
				endLocation = powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude();
				powerLineListArray.add(startLocation+"^"+endLocation+"!<b>Connected: </b>"+powerLine.isIsConnected()+"<br>"+
						"<b>PowerLine Id: </b>"+powerLine.getId() +"<br>"+
						"<b>Maximum Capacity: </b>"+powerLine.getMaximumCapacity()+"<br>"+
						"<b>Current Capacity: </b>"+powerLine.getCurrentCapacity()+"<br>"+
						"<b>PowerLine Type: </b>"+powerLine.getType()+"<br>"+
						"<b>Start Location: </b>"+powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude()+"<br>"+
						"<b>End Location: </b>"+powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude()+"<br>*");

			}
			
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(powerLineListArray.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action showPowerLine()");
			e.printStackTrace();
		}
	}
	
	public void connectToPowerSource(){
		
	}
	
	
}
