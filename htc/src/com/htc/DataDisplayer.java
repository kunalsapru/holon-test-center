package com.htc;

import java.util.List;

import com.htc.pojo.Holon;
import com.htc.pojo.HolonElement;
import com.htc.pojo.HolonObject;



public class DataDisplayer {

	
	public void displayListOfHolonElements(List<HolonElement> listOfHe){
		System.out.println("Printing List of Holon Elements---->");
		for (int i = 0; i < listOfHe.size(); i++) {
			System.out.println("Holon Element Type: '"+(i+1)+"' is:"+listOfHe.get(i).getHolonElementType());
			System.out.println("Max Capacity of Holon Element: '"+(i+1)+"' is:"+listOfHe.get(i).getMaxCapacity());
			System.out.println("Min Capacity of Holon Element: '"+(i+1)+"' is:"+listOfHe.get(i).getMinCapacity());
			System.out.println("------------------------------------------------------");
		}
	}
	
	
	public void displayListOfHolonObjects(List<HolonObject> listOfHo){
		System.out.println("Printing List of Holon Objects---->");
		for (int i = 0; i < listOfHo.size(); i++) {
			
			System.out.println("Holon Object Type: '"+(i+1)+"' is:"+listOfHo.get(i).getHolonObjectType());
			System.out.println("Holon Manager Name of Holon Object:'"+(i+1)+"' is:"+listOfHo.get(i).getHolonManager().getName());
			System.out.println("Priority of Holon Object: '"+(i+1)+"' is:"+listOfHo.get(i).getPriority());
			System.out.println("Line Connected State of Holon Object : '"+(i+1)+"' is:"+listOfHo.get(i).isLineConnectedState());
			System.out.println("Latitude North East of Holon Object:"+listOfHo.get(i).getLatLngNorthEast().getLat());
			System.out.println("Longitude North East of Holon Object:"+listOfHo.get(i).getLatLngNorthEast().getLng());
			System.out.println("Latitude South West of Holon Object:"+listOfHo.get(i).getLatLngSouthWest().getLat());
			System.out.println("Longitude South West of Holon Object:"+listOfHo.get(i).getLatLngSouthWest().getLng());
			displayListOfHolonElements(listOfHo.get(i).getListOfHe());
			System.out.println("*******Completed printing details of one Holon Object*******");
			
		}
			
	}
	
	
	
	public void displayListOfHolons(List<Holon> listOfH){
		for (int i = 0; i < listOfH.size(); i++) {
			
			System.out.println("Holon Name:"+listOfH.get(i).getName());
			System.out.println("Holon Coordinator Name of Holon:'"+(i+1)+"' is:"+listOfH.get(i).getHolonCoordinator().getName());
			displayListOfHolonObjects(listOfH.get(i).getListOfHo());	
		}
	}
	
	
	
}
