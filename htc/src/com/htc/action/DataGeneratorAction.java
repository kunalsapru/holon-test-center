package com.htc.action;

import org.apache.log4j.Logger;

import com.htc.DataDisplayer;
import com.htc.utilities.CommonUtilities;
import com.htc.utilities.HolonFactory;

public class DataGeneratorAction extends CommonUtilities {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7944349867221775081L;
	static Logger log = Logger.getLogger(HolonElementTypeAction.class);

	/**
	 * This action creates data(Holon Objects) and saves them in the database.
	 */
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
						
		Integer attr6 = getRequest().getParameter("attr6")!=null?
				Integer.parseInt(getRequest().getParameter("attr6")):0;//Getting attr1 value from JSP
								
		Integer attr7 = getRequest().getParameter("attr7")!=null?
				Integer.parseInt(getRequest().getParameter("attr7")):0;//Getting attr1 value from JSP
				
		Integer attr8 = getRequest().getParameter("attr8")!=null?
				Integer.parseInt(getRequest().getParameter("attr8")):0;//Getting attr1 value from JSP
						
		Integer attr9 = getRequest().getParameter("attr9")!=null?
				Integer.parseInt(getRequest().getParameter("attr9")):0;//Getting attr1 value from JSP
				
		Integer attr10 = getRequest().getParameter("attr10")!=null?
				Integer.parseInt(getRequest().getParameter("attr10")):0;//Getting attr1 value from JSP
				
//		String attr2 = getRequest().getParameter("attr2")!=null?
//				getRequest().getParameter("attr2"):"No Value";//Getting attr2 value from JSP
//		
//		String attr3 = getRequest().getParameter("attr3")!=null?
//				getRequest().getParameter("attr3"):"No Value";//Getting attr3 value from JSP
//		String attr4 = getRequest().getParameter("attr4")!=null?
//				getRequest().getParameter("attr4"):"No Value";//Getting attr4 value from JSP
//		String attr5 = getRequest().getParameter("attr5")!=null?
//				getRequest().getParameter("attr5"):"No Value";//Getting attr5 value from JSP

		System.out.println("No of Holons = "+attr1);
		System.out.println("Weight for Hospitals: = "+attr2);
		System.out.println("attr3 = "+attr3);
		System.out.println("attr4 = "+attr4);
		System.out.println("attr5 = "+attr5);

		// TODO Auto-generated method stub
		log.info("Start HTC App----->");
		DataDisplayer dataDisplayer = new DataDisplayer();// UI CODE to access back end!
		
//		int freq[]={2,1,2,2,2,2,2,2,20,2};//These integer values will be fetched from the UI Form and will be stored as an array by javascript.
//		HolonElementType ht=RandomDataGenerator.getRandomEnumWithProbability(HolonElement.HolonElementType.class, freq);
//		log.info("Holon Element Type is:"+ht);
	
		
		HolonFactory.buildHolons(attr1);
		dataDisplayer.displayListOfHolons(HolonFactory.getHolons());
		
//		dataDisplayer.displayListOfHolonObject(HolonObjectFactory.buildHolonObjects(4));// Code Before creating Holons
		
//		dataDisplayer.displayListOfHolonElements(HolonObjectFactory.getHolonObjects().get(0).getListOfHe());
//		dataDisplayer.displayListOfHolonElements(HolonObjectFactory.getHolonObjects().get(1).getListOfHe());
		}	

}
