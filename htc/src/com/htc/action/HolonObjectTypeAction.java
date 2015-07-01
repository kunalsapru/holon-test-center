package com.htc.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonObjectType;
import com.htc.utilities.CommonUtilities;

public class HolonObjectTypeAction extends CommonUtilities {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonObjectTypeAction.class);

	/**
	 * This action creates a transient object of holonObjectType and 
	 * saves it in the database and then returns the ID of newly created row.
	 */
	public void createHolonObjectType(){
		System.out.println("Inside Create action ---");

		HolonObjectType holonObjectType = new HolonObjectType(); // Creating HolonObjectType object to store values
		String holonObjectTypeName = getRequest().getParameter("holonObjectTypeName")!=null?
				getRequest().getParameter("holonObjectTypeName"):"Default Value";//Getting HO name value from JSP
		holonObjectType.setName(holonObjectTypeName); // Setting values in HE type object

		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newHolonObjectTypeID = getHolonObjectTypeService().persist(holonObjectType);
		
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		
		try {
			getResponse().getWriter().write("<tr><td onclick='deleteHolonObject("+newHolonObjectTypeID+")'>"+newHolonObjectTypeID+"</td><td>"+holonObjectTypeName+"</td></tr>");
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action createHolonObjectType()");
			e.printStackTrace();
		}
	}

	/**
	 * This action merges an existing object of holonObjectType and 
	 * merges it in the database and then returns the merged object.
	 */
	public void editHolonObjectType(){
		Integer holonObjectTypeId = getRequest().getParameter("holonObjectTypeId")!=null?
				Integer.parseInt(getRequest().getParameter("holonObjectTypeId")):0;//Getting HE ID value from JSP
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId); // Fetching holon Object type from database
				
		holonObjectType.setName("Holon_test"); // Setting new values in HO type object

		//Editing holon Object type object and saving in database 
		HolonObjectType holonObjectType2 = getHolonObjectTypeService().merge(holonObjectType);
		
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		
		try {
			getResponse().getWriter().write("Updated Name from DB = "+holonObjectType2.getName());
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action editHolonObjectType()");
			e.printStackTrace();
		}
	}
	
	/**
	 * This action deletes an existing object of holonObjectType from database.
	 */
	public void deleteHolonObjectType(){
		Integer holonObjectTypeId = getRequest().getParameter("holonObjectTypeId")!=null?
				Integer.parseInt(getRequest().getParameter("holonObjectTypeId")):0;//Getting HE ID value from JSP
		HolonObjectType holonObjectType = getHolonObjectTypeService().findById(holonObjectTypeId); // Fetching holon Object type from database

		//Editing holon Object type object and saving in database 
		boolean deleteStatus = getHolonObjectTypeService().delete(holonObjectType);
		
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		
		try {
			getResponse().getWriter().write("Delete Status --> "+deleteStatus);
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action deleteHolonObjectType()");
			e.printStackTrace();
		}
	}
	
	/**
	 * This action fetches list of all holon Object types from database.
	 */
	public void getListHolonObjectType(){

		ArrayList<HolonObjectType> holonObjectTypes = getHolonObjectTypeService().getAllHolonObjectType();
		StringBuffer holonObjectTypeNameList = new StringBuffer();
		for(HolonObjectType holonObjectType:holonObjectTypes){
			holonObjectTypeNameList.append(holonObjectType.getId()+"-"+holonObjectType.getName()+"*\n");
		}
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		
		try {
			getResponse().getWriter().write(holonObjectTypeNameList.toString());
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action getListHolonObjectType()");
			e.printStackTrace();
		}
	}
	

}
