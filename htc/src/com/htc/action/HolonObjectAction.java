package com.htc.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonCoordinator;
import com.htc.hibernate.pojo.HolonManager;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.HolonObjectType;
import com.htc.hibernate.pojo.LatLng;
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
		Integer holonObjectPriority = getRequest().getParameter("holonObjectPriority")!=null?Integer.parseInt(getRequest().getParameter("holonObjectPriority")):0;
		
		LatLng latLng = new LatLng(latNE, lngNE, latSW, lngSW);
		
		Integer locationId = getLatLngService().persist(latLng);
		LatLng latLng2 = getLatLngService().findById(locationId);

		HolonObject holonObject = new HolonObject(); // Creating HolonObject object to store values

		HolonCoordinator holonCoordinator = getHolonCoordinatorService().findById(holonCoordinatorId);
		Holon holon = holonCoordinator.getHolon();
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
				
		HolonManager holonManager = new HolonManager();
		holonManager.setName(holonManagerName);
		Integer hmId = getHolonManagerService().persist(holonManager);
		HolonManager holonManager2 = getHolonManagerService().findById(hmId);
		
		holonObject.setHolon(holon);
		holonObject.setHolonCoordinator(holonCoordinator);
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.setLatLng(latLng2);
		holonObject.setLineConnectedState(false);
		holonObject.setPriority(holonObjectPriority);
		holonObject.setHolonManager(holonManager2);
		
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newHolonObjectID = getHolonObjectService().persist(holonObject);
		System.out.println("NewLy Generated Holon Object ID --> "+newHolonObjectID);
		HolonObject holonObject2 = getHolonObjectService().findById(newHolonObjectID);
		String holonName = holonObject2.getHolon().getName();
		String holonCoordinatorName_Holon = holonObject2.getHolonCoordinator().getName().concat("_"+holonObject2.getHolonCoordinator().getHolon().getName());
		String holonObjectTypeName = holonObject2.getHolonObjectType().getName();
		String ne_location = holonObject2.getLatLng().getLat_ne()+"~"+holonObject2.getLatLng().getLng_ne();
		String sw_location = holonObject2.getLatLng().getLat_sw()+"~"+holonObject2.getLatLng().getLng_sw();
		
		Boolean lineConnectedState = holonObject2.getLineConnectedState();
		Integer priority = holonObject2.getPriority();
		System.out.println("Newly Created HO Information -->");
		System.out.println("holonName --> "+holonName);
		System.out.println("holonCoordinatorName_Holon --> "+holonCoordinatorName_Holon);
		System.out.println("holonObjectTypeName --> "+holonObjectTypeName);
		System.out.println("ne_location --> "+ne_location);
		System.out.println("lineConnectedState --> "+lineConnectedState);
		System.out.println("priority --> "+priority);
				
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		StringBuffer hoResponse = new StringBuffer();
		hoResponse.append(holonObject2.getId()+"!");
		hoResponse.append(holonName+"!");
		hoResponse.append(holonCoordinatorName_Holon+"!");
		hoResponse.append(holonObjectTypeName+"!");
		hoResponse.append(ne_location+"!");
		hoResponse.append(sw_location+"!");
		hoResponse.append(lineConnectedState+"!");
		hoResponse.append(priority+"!");
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
		Integer holonObjectPriority = getRequest().getParameter("holonObjectPriority")!=null?Integer.parseInt(getRequest().getParameter("holonObjectPriority")):0;
		
		HolonObject holonObject = getHolonObjectService().findById(hiddenHolonObjectId);
		HolonCoordinator holonCoordinator = getHolonCoordinatorService().findById(holonCoordinatorId);
		Holon holon = holonCoordinator.getHolon();
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId);
				
		
		holonObject.setHolon(holon);
		holonObject.setHolonCoordinator(holonCoordinator);
		holonObject.setHolonObjectType(holonObjectType);
		holonObject.setPriority(holonObjectPriority);
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
				ne_location = holonObject.getLatLng().getLat_ne()+"~"+holonObject.getLatLng().getLng_ne();
				sw_location = holonObject.getLatLng().getLat_sw()+"~"+holonObject.getLatLng().getLng_sw();
				hoListArray.add(ne_location+"^"+sw_location+"!<b>Priority: </b>"+holonObject.getPriority()+"<br>"+
						"<b>Holon Object Id: </b>"+holonObject.getId() +"<br>"+
						"<b>Holon Name: </b>"+holonObject.getHolon().getName()+"<br>"+
						"<b>Holon Manager: </b>"+holonObject.getHolonManager().getName()+"<br>"+
						"<b>North East Location: </b>"+holonObject.getLatLng().getLat_ne()+"~"+holonObject.getLatLng().getLng_ne()+"<br>"+
						"<b>South West Location: </b>"+holonObject.getLatLng().getLat_sw()+"~"+holonObject.getLatLng().getLng_sw()+"<br><br>"+
						"<input type='button' id='editHolonObject' name='editHolonObject' value='Edit Holon Object' onclick='editHolonObject("+
						holonObject.getId()+")'/>&nbsp;&nbsp;"+ "<input type='button' id='deleteHolonObject' name='deleteHolonObject' "
								+ "value='Delete Holon Object'/>&nbsp;&nbsp;"+ "<input type='button' id='addHolonElement' name='addHolonElement' "
										+ "value='Add Holon Element' onclick='addHolonElement(" +holonObject.getId()+")'/>&nbsp;&nbsp;"+
						"<input type='button' id='showHolonElement' name='showHolonElement' value='Show Holon Elements' "
						+ "onclick='showHolonElementsForHolon("+holonObject.getId()+")'/>*");

			}
			
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(hoListArray.toString());
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" occurred in action createHolonObject()");
			e.printStackTrace();
		}
	}

}
