package com.htc.utilities;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.htc.pojo.HolonObject;
import com.htc.pojo.HolonObject.HolonObjectType;

public class HolonObjectFactory {
	static Logger log = Logger.getLogger(HolonObjectFactory.class);
	public static List<HolonObject> holonObjects;
	public static final String TAG="HolonObjectFactory";
	
	public static List<HolonObject> getHolonObjects() {
		return holonObjects;
	}

	public static void setHolonObjects(List<HolonObject> holonObjects) {
		HolonObjectFactory.holonObjects = holonObjects;
	}

	
	static{
        log.info("Static Block for 'HolonObjectFactory.java' ");
      //To ensure exception object is created only once and thus optimize on performance!
//        holonObjects=new LinkedList<HolonObject>();
	}
	
	
	public static  List<HolonObject> buildHolonObjects(int numberOfHolonObjects) {//Has to be static because we do not need object of class to call this method
		List<HolonObject> localHoList=new LinkedList<HolonObject>();
		HelperDataStructures.generateHardcodedHolonObjectLatLngList();
		log.info("HolonObjectFactory generating Random HolonObjects....");
		for(int i=0;i< numberOfHolonObjects;i++){
			HolonObject ho = new HolonObject();
			generateRandomValuesForHolonObject(ho);
			localHoList.add(ho);
		}
		setHolonObjects(localHoList);
		return localHoList;
	}
	
	//Methods like these for HE,HM,HK, will take a lot of time as lot of internet RnD
	//on Proper Range values is needed and even technically to generate them is quite
	//difficult
	private static void generateRandomValuesForHolonObject(HolonObject ho){
		
		ho.setHolonManager(HolonManagerFactory.buildSingleHolonManager());
		int randomval=RandomDataGenerator.generateRandomValueIntForUpperBound(9);
		ho.setListOfHe(HolonElementFactory.buildHolonElements(randomval));
		HolonObjectType randomEnum= RandomDataGenerator.getRandomEnum(HolonObject.HolonObjectType.class);
		ho.setHolonObjectType(randomEnum);
		ho.setPriority(randomEnum.ordinal());
		ho.setLineConnectedState(RandomDataGenerator.generateRandomValueBoolean());
		ho.setLatLngNorthEast(HelperDataStructures.getHolonObjectLatLngObjects().get(0).getLatLngNorthEast());
		ho.setLatLngSouthWest(HelperDataStructures.getHolonObjectLatLngObjects().get(0).getLatLngSouthWest());
		HelperDataStructures.getHolonObjectLatLngObjects().remove(0);//Remove the item from the list after assigned to concerned HolonObject
		
	}
	
	/**
	 * This method takes a holon object and an array of frequency(probability) and assigns random values to the holon object
	 * Special note:For the It assigns a 'HolonObjectType' based on the weighted(probability) values in the freq[]
	 * @param ho
	 * @param freq
	 */
	private static void generateRandomValuesForHolonObject(HolonObject ho,int freq[]){
		
		ho.setHolonManager(HolonManagerFactory.buildSingleHolonManager());
		int randomval=RandomDataGenerator.generateRandomValueIntForUpperBound(9);
		ho.setListOfHe(HolonElementFactory.buildHolonElements(randomval));
		HolonObjectType randomEnum= RandomDataGenerator.getRandomEnumWithProbability(HolonObject.HolonObjectType.class, freq);
		ho.setHolonObjectType(randomEnum);
		ho.setPriority(randomEnum.ordinal());
		ho.setLineConnectedState(RandomDataGenerator.generateRandomValueBoolean());	
	}

}
