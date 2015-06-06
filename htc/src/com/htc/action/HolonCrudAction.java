package com.htc.action;

import java.io.IOException;
import java.util.List;

import com.htc.pojo.*;
import com.htc.utilities.CommonUtilities;
import com.htc.utilities.HolonObjectFactory;

public class HolonCrudAction extends CommonUtilities {
	
	private static final long serialVersionUID = 1L;
	private static StringBuffer holonInfo = new StringBuffer("");

	public void createHolons(){

		List<HolonObject> holonObjects = HolonObjectFactory.buildHolonObjects(10);
		displayListOfHolonObject(holonObjects);
		
		System.out.println("Inside createHolons Action");
		logger.info("Checking log4j functionality");
		String dataAttr1 = getRequest().getParameter("dataAttr1")!=null?getRequest().getParameter("dataAttr1"):"BLANK";
		String dataAttr2 = getRequest().getParameter("dataAttr2")!=null?getRequest().getParameter("dataAttr2"):"BLANK";
		String dataAttr3 = getRequest().getParameter("dataAttr3")!=null?getRequest().getParameter("dataAttr3"):"BLANK";
		String dataAttr4 = getRequest().getParameter("dataAttr4")!=null?getRequest().getParameter("dataAttr4"):"BLANK";

		System.out.println("Data Attributes from JSP::"+dataAttr1+dataAttr2+dataAttr3+dataAttr4);
		
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
