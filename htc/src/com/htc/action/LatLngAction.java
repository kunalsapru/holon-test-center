package com.htc.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.LatLng;
import com.htc.utilities.CommonUtilities;

public class LatLngAction extends CommonUtilities {
	static Logger log = Logger.getLogger(LatLngAction.class);
	private static final long serialVersionUID = 1L;
	
	public Integer saveLocation(LatLng locationToSave)
	{
		Integer locationid=0;
		Double lat =locationToSave.getLatitude();
		Double lng =locationToSave.getLongitude();
		ArrayList<LatLng> locationList=getLatLngService().findByLocation(lat,lng);
		log.info("Abhinav Size of latLng Object list is "+locationList);
	
		if(locationList.size()==0)
		{
			log.info("Abhinav Location is not in database ");
			locationid=getLatLngService().persist(locationToSave);
		}
		else
		{
			log.info("Abhinav Location is already in database ");
			LatLng location = locationList.get(0);
			locationid=location.getId();
		}
		return locationid;
		
	}
	
	
	

}
