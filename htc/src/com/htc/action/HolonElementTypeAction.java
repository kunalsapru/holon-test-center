package com.htc.action;

import org.apache.log4j.Logger;
import com.htc.hibernate.pojo.HolonElementType;
import com.htc.utilities.CommonUtilities;

public class HolonElementTypeAction extends CommonUtilities {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonElementTypeAction.class);

	/**
	 * This action creates a transient object of holonElementType and 
	 * saves it in the database and then returns the ID of newly created row.
	 */
	public void createHolonElementType(){

		HolonElementType holonElementType = new HolonElementType(); // Creating HolonElementType object to store values
		String holonElementTypeName = getRequest().getParameter("holonElementTypeName")!=null?
				getRequest().getParameter("holonElementTypeName"):"Default Value";//Getting HE name value from JSP
		holonElementType.setName(holonElementTypeName); // Setting values in HE type object

		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newHolonElementTypeID = getHolonElementTypeService().persist(holonElementType);
		
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		
		try {
			getResponse().getWriter().write("<tr><td>"+newHolonElementTypeID+"</td><td>"+holonElementTypeName+"</td></tr>");
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action createHolonElementType()");
			e.printStackTrace();
		}
	}
}
