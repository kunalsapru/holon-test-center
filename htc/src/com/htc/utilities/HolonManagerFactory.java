package com.htc.utilities;

import java.util.LinkedList;
import java.util.List;

import com.htc.pojo.HolonManager;

public class HolonManagerFactory {
	public static List<HolonManager> holonManagers;
	public static final String TAG="HolonManagerFactory";
	
	public static List<HolonManager> getHolonManagers() {
		return holonManagers;
	}
	public static void setHolonManagers(List<HolonManager> holonManagers) {
		HolonManagerFactory.holonManagers = holonManagers;
	}
	
	static{
        System.out.println("Static Block for 'HolonManagerFactory.java' ");
	}

	
	public static  List<HolonManager> buildHolonManagers(int numberOfHolonManagers) {//Has to be static because we do not need object of class to call this method
		List<HolonManager> localHmList=new LinkedList<HolonManager>();
		System.out.println("HolonObjectFactory generating Random HolonObjects....");
		for(int i=0;i< numberOfHolonManagers;i++){
			HolonManager hm = new HolonManager();
			generateRandomValuesForHolonManager(hm);
			localHmList.add(hm);
		}
		setHolonManagers(localHmList);
		return localHmList;
	}
	
	public static HolonManager buildSingleHolonManager(){
		HolonManager hm = new HolonManager();
		generateRandomValuesForHolonManager(hm);
		return hm;
	}
	
	//Methods like these for HE,HM,HK, will take a lot of time as lot of internet RnD
	//on Proper Range values is needed
	
	
	private static void generateRandomValuesForHolonManager(HolonManager hm){
		
		hm.setName(RandomDataGenerator.generateRandomValueString(4));
		
	}
	
}
