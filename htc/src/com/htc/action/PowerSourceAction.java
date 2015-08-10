package com.htc.action;

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
		Integer psCoordinatorId = getRequest().getParameter("psCoordinatorId")!=null?Integer.parseInt(getRequest().getParameter("psCoordinatorId")):0;
		Integer psMaxProdCap = getRequest().getParameter("psMaxProdCap")!=null?Integer.parseInt(getRequest().getParameter("psMaxProdCap")):0;
		Integer psCurrentPord = getRequest().getParameter("holonCoordinatorId")!=null?Integer.parseInt(getRequest().getParameter("psCurrentPord")):0;
		Integer psStatus = getRequest().getParameter("psStatus")!=null?Integer.parseInt(getRequest().getParameter("psStatus")):0;
		Double radius = getRequest().getParameter("radius")!=null?Double.parseDouble(getRequest().getParameter("radius")):0D;
		Double latCenter = getRequest().getParameter("latCenter")!=null?Double.parseDouble(getRequest().getParameter("latCenter")):0D;
		Double lngCenter = getRequest().getParameter("lngCenter")!=null?Double.parseDouble(getRequest().getParameter("lngCenter")):0D;
				
		LatLng centerLatLng = new LatLng(latCenter, lngCenter);
		LatLngAction latLngAct= new LatLngAction();
		Integer centerLatLngId = latLngAct.saveLocation(centerLatLng);
		PowerSource pwSrc = new PowerSource(); // Creating HolonObject object to store values

		HolonCoordinator holonCoordinator = getHolonCoordinatorService().findById(psCoordinatorId);
		if(psCoordinatorId!=0)
		{
			pwSrc.setHolonCoordinator(holonCoordinator);
		}
		pwSrc.setCentre(getLatLngService().findById(centerLatLngId));
		pwSrc.setCurrentProduction(psCurrentPord);
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
	
	public void getPsObjectInfoWindow()
	{
		try {
			Integer psId = getRequest().getParameter("psId")!=null?Integer.parseInt(getRequest().getParameter("psId")):0;
			PowerSource pwSrc = getPowerSourceService().findById(psId); // Creating HolonObject object to store values
			
			Integer MaxProdCap=pwSrc.getMaxProduction();
			Integer currProd=pwSrc.getCurrentProduction();
			Integer minProd=pwSrc.getMinProduction();
			Boolean status = pwSrc.isStatus();
			double latCenter=pwSrc.getCentre().getLatitude();
			double lngCenter=pwSrc.getCentre().getLongitude();
			HolonCoordinator hc= pwSrc.getHolonCoordinator();
			Integer CoHolonId=0;
			String coHoLoc="";
			
			if(hc!=null)
			{
				CoHolonId=hc.getHolonObject().getId();
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
			
			log.info(psResponse.toString());
			getResponse().getWriter().write(psResponse.toString());
			
			} catch (Exception e) {
				System.out.println("Exception "+e.getMessage()+" occurred in action createPowerSourceObject()");
				e.printStackTrace();
			}
		}
	

}
