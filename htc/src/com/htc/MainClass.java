package com.htc;



import org.apache.log4j.Logger;

import com.htc.pojo.HolonElement;
import com.htc.pojo.HolonElement.HolonElementType;
import com.htc.utilities.*;

public class MainClass {
	static Logger log = Logger.getLogger(MainClass.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		log.info("Start HTC App----->");
		DataDisplayer dataDisplayer = new DataDisplayer();// UI CODE to access back end!
		
//		int freq[]={2,1,2,2,2,2,2,2,20,2};//These integer values will be fetched from the UI Form and will be stored as an array by javascript.
//		HolonElementType ht=RandomDataGenerator.getRandomEnumWithProbability(HolonElement.HolonElementType.class, freq);
//		log.info("Holon Element Type is:"+ht);
	
		
		HolonFactory.buildHolons(1);
		dataDisplayer.displayListOfHolons(HolonFactory.getHolons());
		
//		dataDisplayer.displayListOfHolonObject(HolonObjectFactory.buildHolonObjects(4));// Code Before creating Holons
		
//		dataDisplayer.displayListOfHolonElements(HolonObjectFactory.getHolonObjects().get(0).getListOfHe());
//		dataDisplayer.displayListOfHolonElements(HolonObjectFactory.getHolonObjects().get(1).getListOfHe());
	}

}
