package com.htc.utilities;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.htc.pojo.Holon;
/**
 * This is top level(level 1) class which creates random Holons, which in turn creates random {hms and hos} and only hos creates
 * random hes (the hms do not create hes)
 * 
 * Use this class(or contract) to create the "Initial Back End Data State" for the Map
 * 
 * @author Achal
 *
 */
public class HolonFactory {

	static Logger log = Logger.getLogger(HolonFactory.class);
	public static List<Holon> holons;
	public static final String TAG="HolonFactory";
	public static List<Holon> getHolons() {
		return holons;
	}
	public static void setHolons(List<Holon> holons) {
		HolonFactory.holons = holons;
	}
	
	
	public static  List<Holon> buildHolons(int numberOfHolons) {//Has to be static because we do not need object of class to call this method
		List<Holon> localHolonList=new LinkedList<Holon>();
		log.info("HolonFactory generating Random Holons....");
		for(int i=0;i< numberOfHolons;i++){
			Holon h = new Holon();
			generateRandomValuesForHolon(h);
			localHolonList.add(h);
		}
		setHolons(localHolonList);
		return localHolonList;
	}
	
	
	
	private static void generateRandomValuesForHolon(Holon h){

		h.setName(RandomDataGenerator.generateRandomValueString(10));//A string of length 10.
		h.setHoloncoordinator(HolonCoordinatorFactory.buildSingleHolonCoordinator());//This inturn will create hms and hos in atomic fashion
		h.setListOfHm(HolonManagerFactory.getHolonManagers());
		h.setListOfHo(HolonObjectFactory.getHolonObjects());
		
	}
}
