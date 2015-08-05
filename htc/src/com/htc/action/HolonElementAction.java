package com.htc.action;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonElementState;
import com.htc.hibernate.pojo.HolonElementType;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.utilities.CommonUtilities;

public class HolonElementAction extends CommonUtilities {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonElementAction.class);

	public void createHolonElement(){

		try {
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;
			Integer holonElementTypeId = getRequest().getParameter("holonElementTypeId")!=null?Integer.parseInt(getRequest().getParameter("holonElementTypeId")):0;
			Integer holonElementStateId = getRequest().getParameter("holonElementStateId")!=null?Integer.parseInt(getRequest().getParameter("holonElementStateId")):0;
			Integer currentCapacity = getRequest().getParameter("currentCapacity")!=null && getRequest().getParameter("currentCapacity")!=""?Integer.parseInt(getRequest().getParameter("currentCapacity")):0;
			//?? currentEnergyStatus ??
			//		String history = getRequest().getParameter("String")!=null?getRequest().getParameter("String"):"";

			HolonObject holonObject = getHolonObjectService().findById(holonObjectId);
			HolonElementType holonElementType = getHolonElementTypeService().findById(holonElementTypeId);
			Integer hoCoObjIdOld=holonObject.getHolonCoordinator().getHolonObject().getId();
			HolonElementState holonElementState = getHolonElementStateService().findById(holonElementStateId);
			HolonElement holonElement = new HolonElement(); // Creating HolonElement Element to store values
			holonElement.setCurrentCapacity(currentCapacity);
			holonElement.setHolonElementState(holonElementState);
			holonElement.setHolonElementType(holonElementType);
			holonElement.setHolonObject(holonObject);
			
			
			//Calling service method to save the Element in database and saving the auto-incremented ID in an integer
			Integer newHolonElementID = getHolonElementService().persist(holonElement);
			System.out.println("NewLy Generated Holon Element ID --> "+newHolonElementID);

			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			String dbResponse = "false";
			if(newHolonElementID > 0 ) {
				dbResponse = "true";
			}
			
			String response = dbResponse+"*"+holonObjectId+"*"+hoCoObjIdOld;
			
			getResponse().getWriter().write(response);
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" occurred in action createHolonElement()");
			e.printStackTrace();
		}
	}
	
	

	public void editHolonElement(){

		try {
			Integer holonElementId = getRequest().getParameter("holonElementId")!=null?Integer.parseInt(getRequest().getParameter("holonElementId")):0;
			Integer holonElementTypeId = getRequest().getParameter("holonElementTypeId")!=null?Integer.parseInt(getRequest().getParameter("holonElementTypeId")):0;
			Integer holonElementStateId = getRequest().getParameter("holonElementStateId")!=null?Integer.parseInt(getRequest().getParameter("holonElementStateId")):0;
			Integer currentCapacity = getRequest().getParameter("currentCapacity")!=null?Integer.parseInt(getRequest().getParameter("currentCapacity")):0;
			//?? currentEnergyStatus ??
			//		String history = getRequest().getParameter("String")!=null?getRequest().getParameter("String"):"";

			HolonElementType holonElementType = getHolonElementTypeService().findById(holonElementTypeId);
			HolonElementState holonElementState = getHolonElementStateService().findById(holonElementStateId);

			HolonElement holonElement = getHolonElementService().findById(holonElementId);
			Integer holonObjectId = holonElement.getHolonObject().getId();
			Integer hoCoObjIdOld = getHolonObjectService().findById(holonObjectId).getHolonCoordinator().getHolonObject().getId();
			holonElement.setCurrentCapacity(currentCapacity);
			holonElement.setHolonElementState(holonElementState);
			holonElement.setHolonElementType(holonElementType);
			HolonElement holonElement2 = getHolonElementService().merge(holonElement);
					
			
			String dbResponse = "false";
			if(holonElement2 != null) {
				dbResponse = "true";
			}
			String response = dbResponse+"*"+holonObjectId+"*"+hoCoObjIdOld;;
			
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(response);
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action editHolonElement()");
			e.printStackTrace();
		}
	}

	public void showHolonElements() throws IOException{
		try {
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):13;
			HolonObject holonObject = getHolonObjectService().findById(holonObjectId);		 
			ArrayList<HolonElement> holonElementList = getHolonElementService().getHolonElements(holonObject);
			getResponse().setContentType("text/html");
			StringBuffer response = new StringBuffer();
			response = listHolonElementsAsPerUI(holonElementList, holonObjectId);

			getResponse().getWriter().write(response.toString());
		} catch (Exception e) {
			getResponse().getWriter().write("Error occurred in getting Holon Elements. Please check application Logs for more information.");
			e.printStackTrace();
		}
	}
	
	private StringBuffer listHolonElementsAsPerUI(ArrayList<HolonElement> holonElementsList, int holonObjectId) {
		StringBuffer listElements = new StringBuffer();
		if(holonElementsList != null && holonElementsList.size() > 0) {
			for(HolonElement holonElement : holonElementsList) {
				listElements.append("<tr>");
				//listElements.append("<td>"+holonElement.getId()+"</td>");
				listElements.append("<td>"+holonElement.getHolonElementType().getName()+"</td>");
				String producer=holonElement.getHolonElementType().getProducer()==true?"Yes":"No";
				listElements.append("<td>"+producer+"</td>");
				listElements.append("<td>"+holonElement.getHolonElementType().getMaxCapacity()+"</td>");
				listElements.append("<td>"+holonElement.getHolonElementType().getMinCapacity()+"</td>");
				listElements.append("<td>"+holonElement.getHolonElementState().getName()+"</td>");
				listElements.append("<td>"+holonElement.getCurrentCapacity()+"</td>");
				listElements.append("<td><i class=\"fa fa-remove\" onclick=\"deleteHolonElement("+holonElement.getId()+","+holonObjectId+")\"></i></td>");
				listElements.append("<td><i class=\"fa fa-edit\" onclick=\"editHolonElement("+holonElement.getId()+","+holonElement.getHolonElementType().getId()+","+holonElement.getHolonElementState().getId()+","+holonElement.getCurrentCapacity()+","+holonObjectId+")\"></i></td>");
				listElements.append("<td><i class=\"fa fa-line-chart\"></i></td>");
				listElements.append("</tr>");
			}
		} else {
			listElements.append("<tr><td colspan=\"10\">No Elements found! Please click on Add Holon Element to add some.</td> ");
		}
		return listElements;
	}

	/**
	 * This action deletes an existing object of holonElement from database.
	 */
	public void deleteHolonElement(){
		Integer holonElementId = getRequest().getParameter("holonElementId")!=null?Integer.parseInt(getRequest().getParameter("holonElementId")):0;
		HolonElement holonElement = getHolonElementService().findById(holonElementId);
		Integer holonObjectId = holonElement.getHolonObject().getId();
		Integer hoCoObjIdOld = getHolonObjectService().findById(holonObjectId).getHolonCoordinator().getHolonObject().getId();
		

		//Editing holonElement object and saving in database 
		boolean deleteStatus = getHolonElementService().delete(holonElement);

		
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		try {
			String response = deleteStatus+"*"+holonObjectId+"*"+hoCoObjIdOld;
			getResponse().getWriter().write(response);
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action deleteHolonElement()");
			e.printStackTrace();
		}
	}


}
