package com.htc.utilities;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.htc.pojo.HolonCoordinator;

public class HolonCoordinatorFactory {

	
	static Logger log = Logger.getLogger(HolonObjectFactory.class);
	public static List<HolonCoordinator> holonCoordinators;
	public static final String TAG="HolonCoordinatorFactory";
	public static int noOfHolonManagersGenerated;
	
	public static List<HolonCoordinator> getHolonCoordinators() {
		return holonCoordinators;
	}
	public static void setHolonCoordinators(List<HolonCoordinator> holonCoordinators) {
		HolonCoordinatorFactory.holonCoordinators = holonCoordinators;
	}
	
	/**
	 * Not needed not but may need in future if design changes.This I have kept this method private
	 * 
	 * @param numberOfHolonCoordinators
	 * @return
	 */
	private static  List<HolonCoordinator> buildHolonCoordinators(int numberOfHolonCoordinators) {//Has to be static because we do not need object of class to call this method
		List<HolonCoordinator> localHkList=new LinkedList<HolonCoordinator>();
		log.info("HolonObjectFactory generating Random HolonObjects....");
		for(int i=0;i< numberOfHolonCoordinators;i++){
			HolonCoordinator hk = new HolonCoordinator();
			generateRandomValuesForHolonCoordinator(hk);
			localHkList.add(hk);
		}
		setHolonCoordinators(localHkList);
		return localHkList;
	}
	
	public static HolonCoordinator buildSingleHolonCoordinator(){
		HolonCoordinator hk = new HolonCoordinator();
		generateRandomValuesForHolonCoordinator(hk);
		return hk;
	}
	
	
	private static void generateRandomValuesForHolonCoordinator(HolonCoordinator hk){
		
		hk.setName(RandomDataGenerator.generateRandomValueString(8));// 8 is the length of the string for HK
		int randomval=RandomDataGenerator.generateRandomValueIntForUpperBound(9);
		noOfHolonManagersGenerated=randomval;//This info will be used when created HolonObjects as noofHolonManagers==noofHolonObjects
		log.info("Number of Holon Objects and Holon Managers generated are:"+randomval);
		hk.setListOfHm(HolonManagerFactory.buildHolonManagers(randomval));//The very first time generate; next time just use it, don't regenerate
		HolonObjectFactory.buildHolonObjects(randomval);//Just build HolonObjects here; This will inturn create holon elements
		//Note:Creation of Holon Objects and Holon Managers should be an atomic operation!
	}
}
