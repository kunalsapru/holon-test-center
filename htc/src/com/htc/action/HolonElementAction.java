package com.htc.action;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.htc.hibernate.pojo.HolonElementState;
import com.htc.hibernate.pojo.HolonManager;
import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonElementType;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.utilities.CommonUtilities;

public class HolonElementAction extends CommonUtilities {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonElementAction.class);

	public void createHolonElement(){

		try {
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;
			Integer holonElementTypeId = getRequest().getParameter("holonElementType")!=null?Integer.parseInt(getRequest().getParameter("holonElementType")):0;
			Integer holonElementStateId = getRequest().getParameter("holonElementStateId")!=null?Integer.parseInt(getRequest().getParameter("holonElementStateId")):0;
			String usage = getRequest().getParameter("usage")!=null?getRequest().getParameter("usage"):"";
			Integer maxCapacity = getRequest().getParameter("maxCapacity")!=null?Integer.parseInt(getRequest().getParameter("maxCapacity")):0;
			Integer minCapacity = getRequest().getParameter("minCapacity")!=null?Integer.parseInt(getRequest().getParameter("minCapacity")):0;
			Integer currentCapacity = getRequest().getParameter("currentCapacity")!=null?Integer.parseInt(getRequest().getParameter("currentCapacity")):0;
			//?? currentEnergyStatus ??
			//		String history = getRequest().getParameter("String")!=null?getRequest().getParameter("String"):"";

			HolonObject holonObject = getHolonObjectService().findById(holonObjectId);
			HolonElementType holonElementType = getHolonElementTypeService().findById(holonElementTypeId);
			HolonElementState holonElementState = getHolonElementStateService().findById(holonElementStateId);
			HolonManager holonManager = holonObject.getHolonManager();

			HolonElement holonElement = new HolonElement(); // Creating HolonElement Element to store values
			holonElement.setCurrentCapacity(currentCapacity);
			holonElement.setHolonElementState(holonElementState);
			holonElement.setHolonElementType(holonElementType);
			holonElement.setHolonObject(holonObject);
			holonElement.setUsage(usage);

			//Calling service method to save the Element in database and saving the auto-incremented ID in an integer
			Integer newHolonElementID = getHolonElementService().persist(holonElement);
			System.out.println("NewLy Generated Holon Element ID --> "+newHolonElementID);

			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write("Auto-Increment ID --> "+newHolonElementID);

		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" occurred in action createHolonElement()");
			e.printStackTrace();
		}
	}

	public void editHolonElement(){

		try {
			Integer holonElementId = getRequest().getParameter("holonElementId")!=null?Integer.parseInt(getRequest().getParameter("holonElementId")):0;
			Integer holonElementTypeId = getRequest().getParameter("holonElementType")!=null?Integer.parseInt(getRequest().getParameter("holonElementType")):0;
			Integer holonElementStateId = getRequest().getParameter("holonElementStateId")!=null?Integer.parseInt(getRequest().getParameter("holonElementStateId")):0;
			String usage = getRequest().getParameter("usage")!=null?getRequest().getParameter("usage"):"";
			Integer maxCapacity = getRequest().getParameter("maxCapacity")!=null?Integer.parseInt(getRequest().getParameter("maxCapacity")):0;
			Integer minCapacity = getRequest().getParameter("minCapacity")!=null?Integer.parseInt(getRequest().getParameter("minCapacity")):0;
			Integer currentCapacity = getRequest().getParameter("currentCapacity")!=null?Integer.parseInt(getRequest().getParameter("currentCapacity")):0;
			//?? currentEnergyStatus ??
			//		String history = getRequest().getParameter("String")!=null?getRequest().getParameter("String"):"";

			HolonElementType holonElementType = getHolonElementTypeService().findById(holonElementTypeId);
			HolonElementState holonElementState = getHolonElementStateService().findById(holonElementStateId);

			HolonElement holonElement = getHolonElementService().findById(holonElementId);
			holonElement.setCurrentCapacity(currentCapacity);
			holonElement.setHolonElementState(holonElementState);
			holonElement.setHolonElementType(holonElementType);
			holonElement.setUsage(usage);

			getHolonElementService().merge(holonElement);

			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write("Edit successfull");

		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action createHolonElement()");
			e.printStackTrace();
		}
	}

	public void showHolonElements(){
		try {

			ArrayList<HolonElement> holonElementList = getHolonElementService().getAllHolonElement();
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(holonElementList.toString());
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" occurred in action createHolonElement()");
			e.printStackTrace();
		}
	}

	/**
	 * This action deletes an existing object of holonElement from database.
	 */
	public void deleteHolonElement(){
		Integer holonElementId = getRequest().getParameter("holonElementId")!=null?Integer.parseInt(getRequest().getParameter("holonElementId")):0;
		HolonElement holonElement = getHolonElementService().findById(holonElementId);

		//Editing holonElement object and saving in database 
		boolean deleteStatus = getHolonElementService().delete(holonElement);

		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");

		try {
			getResponse().getWriter().write("Delete Status --> "+deleteStatus);
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action deleteHolonElement()");
			e.printStackTrace();
		}
	}


}
