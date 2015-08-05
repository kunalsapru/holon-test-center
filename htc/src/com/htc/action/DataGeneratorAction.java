package com.htc.action;

import org.apache.log4j.Logger;
import com.htc.utilities.CommonUtilities;

public class DataGeneratorAction extends CommonUtilities {
	
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonElementTypeAction.class);

	public void dataGenerator(){

		Integer attr1 = getRequest().getParameter("attr1")!=null?
				Integer.parseInt(getRequest().getParameter("attr1")):0;//Getting attr1 value from JSP
				
		Integer attr2 = getRequest().getParameter("attr2")!=null?
				Integer.parseInt(getRequest().getParameter("attr2")):0;//Getting attr1 value from JSP
						
		Integer attr3 = getRequest().getParameter("attr3")!=null?
				Integer.parseInt(getRequest().getParameter("attr3")):0;//Getting attr1 value from JSP				
						
		Integer attr4 = getRequest().getParameter("attr4")!=null?
				Integer.parseInt(getRequest().getParameter("attr4")):0;//Getting attr1 value from JSP
						
		Integer attr5 = getRequest().getParameter("attr5")!=null?
				Integer.parseInt(getRequest().getParameter("attr5")):0;//Getting attr1 value from JSP
										
		System.out.println("No of Holons = "+attr1);
		System.out.println("Weight for Hospitals: = "+attr2);
		System.out.println("attr3 = "+attr3);
		System.out.println("attr4 = "+attr4);
		System.out.println("attr5 = "+attr5);

		}	

}
