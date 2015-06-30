package com.htc.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.Holon;
import com.htc.utilities.CommonUtilities;

public class HolonAction extends CommonUtilities {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonElementTypeAction.class);

	public void addHolon(){

		try{String holonName = getRequest().getParameter("holonName")!=null?getRequest().getParameter("holonName"):"";
		Holon holon = new Holon();
		holon.setName(holonName);

		Integer newHolonId = getHolonService().persist(holon);
		getResponse().setContentType("text/html");
		getResponse().getWriter().write(newHolonId.toString());
		}
		catch(Exception e){
			System.out.println("Error while adding the Holon Name in addHolonName method");
		}

	}

	/**
	 * This action merges an existing object of holon and 
	 * merges it in the database and then returns the merged object.
	 */
	public void editHolon(){

	}

	/**
	 * This action deletes an existing object of holon from database.
	 */
	public void deleteHolon(){

	}

	/**
	 * This action fetches list of all holons from database.
	 */
	public void getListHolon(){


		//Get holon object and saving in database 
		ArrayList<Holon> holons = getHolonService().getAllHolon();
		StringBuffer holonList = new StringBuffer();
		for(Holon holon:holons){
			holonList.append(holon.getId()+" - "+holon.getName()+"*\n");
		}
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		
		try {
			getResponse().getWriter().write(holonList.toString());
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action getListHolon()");
			e.printStackTrace();
		}
	
	}

}
