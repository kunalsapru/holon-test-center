package com.htc.action;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.htc.pojo.HolonCoordinator;
import com.htc.pojo.HolonElement;
import com.htc.pojo.HolonObject;
import com.htc.utilities.CommonUtilities;
import com.htc.utilities.HolonCoordinatorFactory;
import com.htc.utilities.HolonObjectFactory;

public class HolonFactory extends CommonUtilities{
	
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(HolonFactory.class);
	private static StringBuffer holonInfo = new StringBuffer("");

	public void createHolonsFromFactory(){
		log.info("Inside createHolonsFromFactory");
		List<HolonObject> holonObjects;
		if(HolonCoordinatorFactory.noOfHolonManagersGenerated==0){
			holonObjects = HolonObjectFactory.buildHolonObjects(10);
		}
		else{
			 holonObjects = HolonObjectFactory.buildHolonObjects((HolonCoordinatorFactory.noOfHolonManagersGenerated));
		}//This "if else" is for persistent state of the elements inside a "Holon".Because no of Hms and Hos should be same!
		displayListOfHolonObject(holonObjects);

		String dataAttr1 = getRequest().getParameter("dataAttr1")!=null?getRequest().getParameter("dataAttr1"):"BLANK";
		String dataAttr2 = getRequest().getParameter("dataAttr2")!=null?getRequest().getParameter("dataAttr2"):"BLANK";
		String dataAttr3 = getRequest().getParameter("dataAttr3")!=null?getRequest().getParameter("dataAttr3"):"BLANK";
		String dataAttr4 = getRequest().getParameter("dataAttr4")!=null?getRequest().getParameter("dataAttr4"):"BLANK";

		log.info("Data Attributes from JSP::"+dataAttr1+dataAttr2+dataAttr3+dataAttr4);

		//Create Holons in Database
		
		
		//End of Code for create holons in Database

		getResponse().setContentType("text/html");
		try {
			getResponse().getWriter().write(holonInfo.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void displayListOfHolonElements(List<HolonElement> listOfHe){
		for (int i = 0; i < listOfHe.size(); i++) {
			int maxcap=listOfHe.get(i).getMaxCapacity();
			holonInfo.append("<br/><b>Holon Element#"+(i+1)+"</b>");
			holonInfo.append("<br/><b>Type:</b> "+listOfHe.get(i).getHolonElementType());
			holonInfo.append("<br/><b>Max Capacity:</b> "+maxcap);
			holonInfo.append("<br/><b>Min Capacity:</b> "+listOfHe.get(i).getMinCapacity());
		}
		holonInfo.append("##");
	}
	
	
	public void displayListOfHolonObject(List<HolonObject> listOfHo){
		for (int i = 0; i < listOfHo.size(); i++) {
			holonInfo.append("<b>Holon Object#"+(i+1)+"</b><br/><b>Name:</b> "+listOfHo.get(i).getHolonManager().getName());
			holonInfo.append("<br/><b>Type:</b> "+listOfHo.get(i).getHolonObjectType());
			holonInfo.append("<br/><b>Priority:</b> "+listOfHo.get(i).getPriority());
			holonInfo.append("<br/><b>Line Connected State:</b> "+listOfHo.get(i).isLineConnectedState());
			displayListOfHolonElements(listOfHo.get(i).getListOfHe());
		}
	}
	
}
