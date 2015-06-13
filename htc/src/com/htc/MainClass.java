package com.htc;


import com.htc.utilities.HolonFactory;

public class MainClass {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start HTC App----->");
		DataDisplayer dataDisplayer = new DataDisplayer();// UI CODE to access back end!
		
		
	
		
		HolonFactory.buildHolons(1);
		dataDisplayer.displayListOfHolons(HolonFactory.getHolons());
		
//		dataDisplayer.displayListOfHolonObject(HolonObjectFactory.buildHolonObjects(4));// Code Before creating Holons
		
//		dataDisplayer.displayListOfHolonElements(HolonObjectFactory.getHolonObjects().get(0).getListOfHe());
//		dataDisplayer.displayListOfHolonElements(HolonObjectFactory.getHolonObjects().get(1).getListOfHe());
	}

}
