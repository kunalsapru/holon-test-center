package com.htc.action;

import org.apache.log4j.Logger;
import com.htc.utilities.CommonUtilities;

public class HolonCrudAction extends CommonUtilities {
	
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonCrudAction.class);
	
	public void createHolons(){

		log.debug("Inside createHolons action");
		getResponse().setContentType("text/html");
		try {
			getResponse().getWriter().write("Response 200 o.k");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
