package com.htc.action;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;
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
			String resaonDown = getRequest().getParameter("resaonDown")!=null?getRequest().getParameter("resaonDown"):"";
			Double latStart = getRequest().getParameter("latStart")!=null?Double.parseDouble(getRequest().getParameter("latStart")):0D;
			Double lngStart = getRequest().getParameter("lngStart")!=null?Double.parseDouble(getRequest().getParameter("lngStart")):0D;
			Double latEnd = getRequest().getParameter("latEnd")!=null?Double.parseDouble(getRequest().getParameter("latEnd")):0D;
			Double lngEnd = getRequest().getParameter("latEnd")!=null?Double.parseDouble(getRequest().getParameter("latEnd")):0D;
			Set<PowerSwitch> powerSwitches= new HashSet<PowerSwitch>(0);
			
			LatLng latLng = new LatLng(latStart, lngStart, latEnd, lngEnd);
			
			int newLatLngId = getLatLngService().persist(latLng);
			
			LatLng savedLatLng=getLatLngService().findById(newLatLngId);
			
			
			
			PowerLine powerLine = new PowerLine(savedLatLng, savedLatLng, powerLineType, currentCapacity, maxCapacity, isConnected, resaonDown, powerSwitches);
			
			Integer newPowerLineID = getPowerLineService().persist(powerLine);
			
			log.info("NewLy Generated powerLine  ID --> "+newPowerLineID);
			
			
			getResponse().setContentType("text/html");
			StringBuffer plResponse = new StringBuffer();
			plResponse.append(newPowerLineID+"!");
			plResponse.append(isConnected+"!");
			plResponse.append(maxCapacity+"!");
			plResponse.append(currentCapacity+"!");
			plResponse.append(powerLineType+"!");
			plResponse.append(resaonDown+"!");
			plResponse.append(latStart+"!");
			plResponse.append(lngStart+"!");
			plResponse.append(latEnd+"!");
			plResponse.append(lngEnd);		
			
			getResponse().getWriter().write(plResponse.toString());	
			
			
			
			
		}catch(Exception e)
		{
			System.out.println("Exception "+e.getMessage()+" occurred in action createHolonObject()");
			e.printStackTrace();

		}
		
		
		
	}
	
	
	
	public void connectToPowerSource(){
		
	}
	
	
}
