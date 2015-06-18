package com.htc.action;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonElementType;
import com.htc.utilities.CommonUtilities;

public class DataGeneratorAction extends CommonUtilities {
	
	static Logger log = Logger.getLogger(HolonElementTypeAction.class);

	/**
	 * This action creates data(Holon Objects) and saves them in the database.
	 */
	public void dataGenerator(){

		String attr1 = getRequest().getParameter("attr1")!=null?
				getRequest().getParameter("attr1"):"No Value";//Getting attr1 value from JSP
		String attr2 = getRequest().getParameter("attr2")!=null?
				getRequest().getParameter("attr2"):"No Value";//Getting attr2 value from JSP
		
		String attr3 = getRequest().getParameter("attr3")!=null?
				getRequest().getParameter("attr3"):"No Value";//Getting attr3 value from JSP
		String attr4 = getRequest().getParameter("attr4")!=null?
				getRequest().getParameter("attr4"):"No Value";//Getting attr4 value from JSP
		String attr5 = getRequest().getParameter("attr5")!=null?
				getRequest().getParameter("attr5"):"No Value";//Getting attr5 value from JSP

		System.out.println("attr1 = "+attr1);
		System.out.println("attr2 = "+attr2);
		System.out.println("attr3 = "+attr3);
		System.out.println("attr4 = "+attr4);
		System.out.println("attr5 = "+attr5);
		//Calling the response function and setting the content type of response.
		getResponse().setContentType("text/html");
		
		try {
			getResponse().getWriter().write("Response from dataGenerator");
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action dataGenerator()");
			e.printStackTrace();
		}
	}	

}
