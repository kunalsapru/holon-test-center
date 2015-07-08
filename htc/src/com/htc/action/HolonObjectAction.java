package com.htc.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonCoordinator;
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
		String holonManagerName = getRequest().getParameter("holonManager")!=null?getRequest().getParameter("holonManager"):"";
		Double latNE = getRequest().getParameter("latNE")!=null?Double.parseDouble(getRequest().getParameter("latNE")):0D;
		Double lngNE = getRequest().getParameter("lngNE")!=null?Double.parseDouble(getRequest().getParameter("lngNE")):0D;
		Double latSW = getRequest().getParameter("latSW")!=null?Double.parseDouble(getRequest().getParameter("latSW")):0D;
		Double lngSW = getRequest().getParameter("lngSW")!=null?Double.parseDouble(getRequest().getParameter("lngSW")):0D;
		
		LatLng NorthlatLng = new LatLng(latNE, lngNE);
		LatLng SouthlatLng = new LatLng(latSW, lngSW);
		LatLng DoorlatLng = getDoorLocation(NorthlatLng,SouthlatLng);
		
		Integer NorthlocationId = getLatLngService().persist(NorthlatLng);
		Integer SouthlocationId = getLatLngService().persist(SouthlatLng);
		Integer DoorlocationId = getLatLngService().persist(DoorlatLng);
		LatLng NorthlatLng2 = getLatLngService().findById(NorthlocationId);
		LatLng SouthlatLng2 = getLatLngService().findById(SouthlocationId);
		LatLng DoorlatLng2 = getLatLngService().findById(DoorlocationId);
		HolonObject holonObject = new HolonObject(); // Creating HolonObject object to store values

		HolonCoordinator holonCoordinator = getHolonCoordinatorService().findById(holonCoordinatorId);
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
				
		HolonManager holonManager = new HolonManager();
		holonManager.setName(holonManagerName);
		Integer hmId = getHolonManagerService().persist(holonManager);
		HolonManager holonManager2 = getHolonManagerService().findById(hmId);
		
		holonObject.setLatLngByNeLocation(NorthlatLng2);
		holonObject.setLatLngBySwLocation(SouthlatLng2);
		holonObject.setLatLngByDoorLocation(DoorlatLng2);//Temporarily saving the ne latlng. Need to be calculated and saved.
		holonObject.setHolonCoordinator(holonCoordinator);
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.setLineConnectedState(false);
		holonObject.setHolonManager(holonManager2);
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newHolonObjectID = getHolonObjectService().persist(holonObject);
		System.out.println("NewLy Generated Holon Object ID --> "+newHolonObjectID);
		HolonObject holonObject2 = getHolonObjectService().findById(newHolonObjectID);
		String holonCoordinatorName_Holon = holonObject2.getHolonCoordinator().getName().concat("_"+holonObject2.getHolonCoordinator().getHolon().getName());
		String holonObjectTypeName = holonObject2.getHolonObjectType().getName();
		String ne_location = holonObject2.getLatLngByNeLocation().getLatitude()+"~"+holonObject2.getLatLngByNeLocation().getLongitude();
		String sw_location = holonObject2.getLatLngBySwLocation().getLatitude()+"~"+holonObject2.getLatLngBySwLocation().getLongitude();
		
		Boolean lineConnectedState = holonObject2.getLineConnectedState();
		System.out.println("Newly Created HO Information -->");
		System.out.println("holonCoordinatorName_Holon --> "+holonCoordinatorName_Holon);
		System.out.println("holonObjectTypeName --> "+holonObjectTypeName);
		System.out.println("ne_location --> "+ne_location);
		System.out.println("lineConnectedState --> "+lineConnectedState);
					
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		StringBuffer hoResponse = new StringBuffer();
		hoResponse.append(holonObject2.getId()+"!");
		hoResponse.append(holonCoordinatorName_Holon+"!");
		hoResponse.append(holonObjectTypeName+"!");
		hoResponse.append(ne_location+"!");
		hoResponse.append(sw_location+"!");
		hoResponse.append(lineConnectedState+"!");
		hoResponse.append(holonObject2.getHolonManager().getName());
		
		
		getResponse().getWriter().write(hoResponse.toString());
		
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" occurred in action createHolonObject()");
			e.printStackTrace();
		}
	}

	public void editHolonObject(){

		try {
		Integer holonObjectTypeId = getRequest().getParameter("holonObjectType")!=null?Integer.parseInt(getRequest().getParameter("holonObjectType")):0;
		Integer hiddenHolonObjectId = getRequest().getParameter("hiddenHolonObjectId")!=null?Integer.parseInt(getRequest().getParameter("hiddenHolonObjectId")):0;
		Integer holonCoordinatorId = getRequest().getParameter("holonCoordinatorId")!=null?Integer.parseInt(getRequest().getParameter("holonCoordinatorId")):0;
		String holonManagerName = getRequest().getParameter("holonManager")!=null?getRequest().getParameter("holonManager"):"";
		
		HolonObject holonObject = getHolonObjectService().findById(hiddenHolonObjectId);
		HolonCoordinator holonCoordinator = getHolonCoordinatorService().findById(holonCoordinatorId);
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
				
		
	
		holonObject.setHolonCoordinator(holonCoordinator);
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.getHolonManager().setName(holonManagerName);
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		getHolonObjectService().merge(holonObject);

		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		
		getResponse().getWriter().write("Edit successfull");
		
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action createHolonObject()");
			e.printStackTrace();
		}
	}
	
	public void showHolonObjects(){
		try {
			
			ArrayList<HolonObject> holonObjectList = getHolonObjectService().getAllHolonObject();
			ArrayList<String> hoListArray = new ArrayList<String>();
			HolonObject holonObject = null;
			String ne_location;
			String sw_location;
			
			for(int i=0; i<holonObjectList.size();i++){
				holonObject = holonObjectList.get(i);
				ne_location = holonObject.getLatLngByNeLocation().getLatitude()+"~"+holonObject.getLatLngByNeLocation().getLongitude();
				sw_location = holonObject.getLatLngBySwLocation().getLatitude()+"~"+holonObject.getLatLngBySwLocation().getLongitude();
				hoListArray.add(ne_location+"^"+sw_location+"!<b>Priority: </b>"+
						"<b>Holon Object Id: </b>"+holonObject.getId() +"<br>"+
						"<b>Holon Manager: </b>"+holonObject.getHolonManager().getName()+"<br>"+
						"<b>North East Location: </b>"+ne_location+"<br>"+
						"<b>South West Location: </b>"+sw_location+"<br><br>"+
						"<input type='button' id='editHolonObject' name='editHolonObject' value='Edit Holon Object' onclick='editHolonObject("+
						holonObject.getId()+")'/>&nbsp;&nbsp;"+ "<input type='button' id='deleteHolonObject' name='deleteHolonObject' "
								+ "value='Delete Holon Object'/>&nbsp;&nbsp;"+ "<input type='button' id='addHolonElement' name='addHolonElement' "
										+ "value='Add Holon Element' onclick='addHolonElement(" +holonObject.getId()+")'/>&nbsp;&nbsp;"+
						"<input type='button' id='showHolonElement' name='showHolonElement' value='Show Holon Elements' "
						+ "onclick='showHolonElements("+holonObject.getId()+")'/>*");

			}
			
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(hoListArray.toString());
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" occurred in action createHolonObject()");
			e.printStackTrace();
		}
	}

	public LatLng getDoorLocation(LatLng northlatLng, LatLng southlatLng) {
		LatLng doorLocation = new LatLng();
		LatLng midPoint = new LatLng();
		double distance=0;
		HashMap<Double,LatLng> distanceMap = new HashMap<Double,LatLng>();
		PowerLine powerLine = getNearestLine(midPoint);
		List<LatLng> probableDoorLocList = new ArrayList<LatLng>();
		List<Double> distanceList = new ArrayList<Double>();
		probableDoorLocList = getProbableDoorLocations(northlatLng,southlatLng);
		for(LatLng loc: probableDoorLocList){
			distance = getDistanceFromPointToLine(powerLine,loc);
			distanceList.add(distance);
			distanceMap.put(distance,loc);
		}
		Collections.sort(distanceList);
		doorLocation = distanceMap.get(distanceList.get(0));
		
		return doorLocation;
	}

	public List<LatLng> getProbableDoorLocations(LatLng northlatLng,
			LatLng southlatLng) {
		List<LatLng> doorLocationsList = new ArrayList<LatLng>();
		LatLng north = new LatLng();
		LatLng south = new LatLng();
		LatLng east = new LatLng();
		LatLng west = new LatLng();
		north.setLatitude(northlatLng.getLatitude());
		north.setLongitude((northlatLng.getLongitude() + southlatLng.getLongitude())/2);
		south.setLatitude(southlatLng.getLatitude());
		south.setLongitude((northlatLng.getLongitude() + southlatLng.getLongitude())/2);
		east.setLatitude((northlatLng.getLatitude() + southlatLng.getLatitude())/2);
		east.setLongitude(northlatLng.getLongitude());
		west.setLatitude((northlatLng.getLatitude() + southlatLng.getLatitude())/2);
		west.setLongitude(southlatLng.getLongitude());
		return doorLocationsList;
	}

	public double getDistanceFromPointToLine(PowerLine powerLine, LatLng loc) {
		return 0;
		// TODO Auto-generated method stub
		
	}

	public PowerLine getNearestLine(LatLng midPoint) {
		double distance;
		List<Double> distanceList = new ArrayList<Double>();
		HashMap<Double,PowerLine> distanceMap = new HashMap<Double,PowerLine>();
		PowerLine nearestPowerLine = new PowerLine();
		List<PowerLine> powerLineList = getPowerLineService().getAllPowerLine();
		for(PowerLine powerLine : powerLineList){
			distance = getDistanceFromPointToLine(powerLine, midPoint);
			distanceList.add(distance);
			distanceMap.put(distance,powerLine);
		}
		Collections.sort(distanceList);
		nearestPowerLine = distanceMap.get(distanceList.get(0));
		return nearestPowerLine;
	}

	public void updateConnectedPowerLine(HolonObject holonObject,
			PowerLine powerLine) {
		// TODO Auto-generated method stub
		
	}
}
