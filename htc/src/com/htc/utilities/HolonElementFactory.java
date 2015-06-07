package com.htc.utilities;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.htc.pojo.HolonElement;

public class HolonElementFactory {
	static Logger log = Logger.getLogger(HolonElementFactory.class);
	public static List<HolonElement> holonElements;
	public static final String TAG="HolonElementFactory";
//	private static RandomDataGenerator randomDataGenerator;
	
	
	public static List<HolonElement> getHolonElements() {
		return holonElements;
	}


	public static void setHolonElements(List<HolonElement> holonElements) {
		HolonElementFactory.holonElements = holonElements;
	}
	
	
	static{
        log.info("Static Block for 'HolonElementFactory.java' ");
//        randomDataGenerator = new RandomDataGenerator();
	}
	
	
	public static List<HolonElement> buildHolonElements(int numberOfHolonElements) {//Has to be static because we do not need object of class to call this method
		log.info("Inside buildHolonElements");
		List<HolonElement> localHeList=new LinkedList<HolonElement>();
		log.info("HolonElementFactory generating Random HolonElements....");
		for(int i=0;i< numberOfHolonElements;i++){
	    HolonElement he = new HolonElement();
	    generateRandomValuesForHolonElement(he);
	    localHeList.add(he);//Required major output from this class!
		}
		setHolonElements(localHeList);
		return localHeList;
	}
	
	
	    
	//Methods like these for HE,HM,HK, will take a lot of time as lot of internet RnD
	//on Proper Range values is needed and even technically to generate them is quite
	//difficult
	private static void generateRandomValuesForHolonElement(HolonElement he){
		int randomVal=RandomDataGenerator.generateRandomValueIntForUpperBound(49);
		he.setMinCapacity(randomVal);
		he.setMaxCapacity(RandomDataGenerator.generateRandomValueIntInRange(50, 100));
		he.setHolonElementType(RandomDataGenerator.getRandomEnum(HolonElement.HolonElementType.class));
		
	}
	
	
	
}
