package com.htc.utilities;

import java.util.List;

import org.apache.log4j.Logger;

import com.htc.pojo.HolonElement;
import com.htc.pojo.HolonObject;

public class DataDisplayer {
	static Logger log = Logger.getLogger(DataDisplayer.class);
	
	public void displayListOfHolonElements(List<HolonElement> listOfHe){
		log.info("Inside displayListOfHolonElements");
		for (int i = 0; i < listOfHe.size(); i++) {
			int maxcap=listOfHe.get(i).getMaxCapacity();
			log.info("Max Capacity of Holon Element: '"+(i+1)+"' is:"+maxcap);
			log.info("Min Capacity of Holon Element: '"+(i+1)+"' is:"+listOfHe.get(i).getMinCapacity());
			log.info("Holon Element Type: '"+(i+1)+"' is:"+listOfHe.get(i).getHolonElementType());
		}
	}
	
	
	public void displayListOfHolonObject(List<HolonObject> listOfHo){
		for (int i = 0; i < listOfHo.size(); i++) {
			
			log.info("Holon Manager Name of Holon Object:'"+(i+1)+"' is:"+listOfHo.get(i).getHolonManager().getName());
			log.info("Holon Object Type: '"+(i+1)+"' is:"+listOfHo.get(i).getHolonObjectType());
			log.info("Priority of Holon Object: '"+(i+1)+"' is:"+listOfHo.get(i).getPriority());
			log.info("Line Connected State of Holon Object : '"+(i+1)+"' is:"+listOfHo.get(i).isLineConnectedState());
			displayListOfHolonElements(listOfHo.get(i).getListOfHe());
			
		}
	}
}
