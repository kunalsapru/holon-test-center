package com.htc.utilities;

import java.util.LinkedList;
import java.util.List;

import com.htc.pojo.HolonObject;
import com.htc.pojo.HolonObject.HolonObjectType;

public class HolonObjectFactory {

	public static List<HolonObject> holonObjects;
	public static final String TAG="HolonObjectFactory";
	
	public static List<HolonObject> getHolonObjects() {
		return holonObjects;
	}

	public static void setHolonObjects(List<HolonObject> holonObjects) {
		HolonObjectFactory.holonObjects = holonObjects;
	}

	
	static{
        System.out.println("Static Block for 'HolonObjectFactory.java' ");
      //To ensure exception object is created only once and thus optimize on performance!
//        holonObjects=new LinkedList<HolonObject>();
	}
	
	
	public static  List<HolonObject> buildHolonObjects(int numberOfHolonObjects) {//Has to be static because we do not need object of class to call this method
		List<HolonObject> localHoList=new LinkedList<HolonObject>();
		System.out.println("HolonObjectFactory generating Random HolonObjects....");
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
	}
		

}
