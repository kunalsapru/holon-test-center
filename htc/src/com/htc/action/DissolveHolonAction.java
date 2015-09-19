package com.htc.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonObject;
import com.htc.utilities.CommonUtilities;

public class DissolveHolonAction extends CommonUtilities {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(DissolveHolonAction.class);

	public void dissolveHolon() {
		try {
			
			Integer holonCoordinatorId = getRequest().getParameter("holonCoordinatorId")!=null?Integer.parseInt(getRequest().getParameter("holonCoordinatorId")):0;
			String responseDissolveHolon = "false";
			if(holonCoordinatorId > 0) {
				HolonObject holonCoordinator = getHolonObjectService().findById(holonCoordinatorId);
				Integer currentEnergyRequiredHolon = 0;
				Integer flexibilityHolon = 0;
				Map<String, String> holonEnergyDetails = getHolonEnergyDetails(holonCoordinator);
				currentEnergyRequiredHolon = Integer.parseInt(holonEnergyDetails.get("currentEnergyRequiredHolon"));
				flexibilityHolon = Integer.parseInt(holonEnergyDetails.get("flexibilityHolon"));
				//Holon will dissolve only if the flexibility of current holon is zero and current energy requirement is greater than zero
				if(flexibilityHolon == 0 && currentEnergyRequiredHolon > 0) {
					responseDissolveHolon = "true";
				}
			}
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(responseDissolveHolon);
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action dissolveHolon()");
			e.printStackTrace();
		}
	}
	
	public void startDynamicHolon() {
		try {
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write("");
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action startDynamicHolon()");
			e.printStackTrace();
		}
	}
	
}
