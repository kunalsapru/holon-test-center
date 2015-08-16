package com.htc.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.utilities.CommonUtilities;

public class PowerSourceAction  extends CommonUtilities{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(PowerSourceAction.class);
	
	
	public void createPowerSourceObject(){

		try {
		Integer psMaxProdCap = getRequest().getParameter("psMaxProdCap")!=null?Integer.parseInt(getRequest().getParameter("psMaxProdCap")):0;
		Integer psStatus = getRequest().getParameter("psStatus")!=null?Integer.parseInt(getRequest().getParameter("psStatus")):0;
		Double radius = getRequest().getParameter("radius")!=null?Double.parseDouble(getRequest().getParameter("radius")):0D;
		Double latCenter = getRequest().getParameter("latCenter")!=null?Double.parseDouble(getRequest().getParameter("latCenter")):0D;
		Double lngCenter = getRequest().getParameter("lngCenter")!=null?Double.parseDouble(getRequest().getParameter("lngCenter")):0D;
				
		LatLng centerLatLng = new LatLng(latCenter, lngCenter);
		LatLngAction latLngAct= new LatLngAction();
		Integer centerLatLngId = latLngAct.saveLocation(centerLatLng);
		PowerSource pwSrc = new PowerSource(); // Creating HolonObject object to store values

		pwSrc.setCenter(getLatLngService().findById(centerLatLngId));
		pwSrc.setCurrentProduction(0);
		pwSrc.setRadius(radius);
		pwSrc.setMaxProduction(psMaxProdCap);
		pwSrc.setMinProduction(0);
		pwSrc.setStatus((psStatus==1?true:false));
		Integer newPsId = getPowerSourceService().persist(pwSrc);
					
		log.info("NewLy Generated Power Source ID --> "+newPsId);
		PowerSource pwSrc2 = getPowerSourceService().findById(newPsId);
				
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		StringBuffer psResponse = new StringBuffer();
		psResponse.append(pwSrc2.getId()+"!");
		psResponse.append(psStatus);
				
		log.info(psResponse.toString());
		getResponse().getWriter().write(psResponse.toString());
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" occurred in action createPowerSourceObject()");
			e.printStackTrace();
		}
	}
	
	
	public void editPowerSourceObject()
	{
		
		try {
			Integer psMaxProdCap = getRequest().getParameter("psMaxProdCap")!=null?Integer.parseInt(getRequest().getParameter("psMaxProdCap")):0;
			Integer psStatus = getRequest().getParameter("psStatus")!=null?Integer.parseInt(getRequest().getParameter("psStatus")):0;
			Integer pSourceId = getRequest().getParameter("hiddenPowerObjectId")!=null?Integer.parseInt(getRequest().getParameter("hiddenPowerObjectId")):0;		
			
			PowerSource pwSrcOld = getPowerSourceService().findById(pSourceId);

			pwSrcOld.setMaxProduction(psMaxProdCap);
			pwSrcOld.setStatus((psStatus==1?true:false));
			getPowerSourceService().merge(pwSrcOld);
			PowerSource pwSrc2 = getPowerSourceService().findById(pSourceId);
					
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			StringBuffer psResponse = new StringBuffer();
			psResponse.append(pwSrc2.getId()+"!");
			psResponse.append(psStatus);
					
			log.info(psResponse.toString());
			getResponse().getWriter().write(psResponse.toString());
			} catch (Exception e) {
				System.out.println("Exception "+e.getMessage()+" occurred in action createPowerSourceObject()");
				e.printStackTrace();
			}
	}
	
	
	public void getPsObjectInfoWindow()
	{
		try {
			Integer psId = getRequest().getParameter("psId")!=null?Integer.parseInt(getRequest().getParameter("psId")):0;
			PowerSource pwSrc = getPowerSourceService().findById(psId); 
			
			Integer MaxProdCap=pwSrc.getMaxProduction();
			Integer currProd=pwSrc.getCurrentProduction();
			Integer minProd=pwSrc.getMinProduction();
			Boolean status = pwSrc.getStatus();
			double latCenter=pwSrc.getCenter().getLatitude();
			double lngCenter=pwSrc.getCenter().getLongitude();
			double pwSrcRad=pwSrc.getRadius();
			HolonCoordinator hc= pwSrc.getHolonCoordinator();
			Integer CoHolonId=0;
			String coHoLoc="";
			String CoHolonName="";
			if(hc!=null) {
				CoHolonId=hc.getHolonObject().getId();
				CoHolonName= hc.getName().concat("_"+hc.getHolon().getName());
				coHoLoc=hc.getHolonObject().getLatLngByNeLocation().getLatitude()+"~"+hc.getHolonObject().getLatLngByNeLocation().getLongitude();
			}
			
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			StringBuffer psResponse = new StringBuffer();
			psResponse.append(psId+"!");
			psResponse.append(MaxProdCap+"!");
			psResponse.append(currProd+"!");
			psResponse.append(CoHolonId+"!");
			psResponse.append(coHoLoc+"!");
			psResponse.append(status+"!");
			psResponse.append(minProd+"!");
			psResponse.append(latCenter+"!");
			psResponse.append(lngCenter+"!");
			psResponse.append(pwSrcRad+"!");
			psResponse.append(CoHolonName+"!");
			
			log.info(psResponse.toString());
			getResponse().getWriter().write(psResponse.toString());
			} catch (Exception e) {
				System.out.println("Exception "+e.getMessage()+" occurred in action getPsObjectInfoWindow()");
				e.printStackTrace();
			}
		}
	
	public void showPowerSources(){
		try {			
			ArrayList<String> psListArray = new ArrayList<String>();
			PowerSource pwSrc = null;
				
			ArrayList<PowerSource> psObjectList = getPowerSourceService().getAllPowerSource();
			for(int i=0; i<psObjectList.size();i++){
				boolean status=false;
				String center;
				double radius;
				Integer psObjectId;
				pwSrc = psObjectList.get(i);
				center = pwSrc.getCenter().getLatitude()+"~"+pwSrc.getCenter().getLongitude();
				radius = pwSrc.getRadius();
				status=pwSrc.getStatus();
				psObjectId=pwSrc.getId();
				psListArray.add(psObjectId+"#"+radius+"#"+center+"#"+status+"*");
			}
			
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(psListArray.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action showPowerSources()");
			e.printStackTrace();
		}
	}
	
	public void powerSourceOnOff()
	{
		try {			
			Integer powerSrcId = getRequest().getParameter("powerSrcId")!=null?Integer.parseInt(getRequest().getParameter("powerSrcId")):0;
			PowerSource pwSrc = getPowerSourceService().findById(powerSrcId); 
			boolean pwOldStatus=pwSrc.getStatus();
			int maxProd = pwSrc.getMaxProduction();
			int minProd = pwSrc.getMinProduction();
			int currentProd = pwSrc.getCurrentProduction();
			boolean pwNewStatus=true;
			Integer pwNewIntStatus=1;
			if(pwOldStatus) {
				pwNewStatus=false;
				pwNewIntStatus=0;
				pwSrc.setCurrentProduction(0);
			} else {
				pwSrc.setCurrentProduction(pwSrc.getMaxProduction());
			}
			pwSrc.setStatus(pwNewStatus);
			getPowerSourceService().merge(pwSrc);
			HolonCoordinator hc= pwSrc.getHolonCoordinator();
			Integer CoHolonId=0;
			String coHoLoc="";
			if(hc!=null) {
				CoHolonId=hc.getHolonObject().getId();
				coHoLoc=hc.getHolonObject().getLatLngByNeLocation().getLatitude()+"~"+hc.getHolonObject().getLatLngByNeLocation().getLongitude();
			}
			
			StringBuffer respStr=new StringBuffer();
			respStr.append(CoHolonId+"!");
			respStr.append(coHoLoc+"!");
			respStr.append(pwNewIntStatus+"!");
			respStr.append(maxProd+"!");
			respStr.append(minProd+"!");
			respStr.append(currentProd);
			
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(respStr.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action powerSourceOnOff()");
			e.printStackTrace();
		}
		
	}

}
