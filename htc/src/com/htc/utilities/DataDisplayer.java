package com.htc.utilities;

import java.util.List;

import com.htc.pojo.HolonElement;
import com.htc.pojo.HolonObject;

public class DataDisplayer {

	
	public void displayListOfHolonElements(List<HolonElement> listOfHe){
		for (int i = 0; i < listOfHe.size(); i++) {
			int maxcap=listOfHe.get(i).getMaxCapacity();
			System.out.println("Max Capacity of Holon Element: '"+(i+1)+"' is:"+maxcap);
			System.out.println("Min Capacity of Holon Element: '"+(i+1)+"' is:"+listOfHe.get(i).getMinCapacity());
			System.out.println("Holon Element Type: '"+(i+1)+"' is:"+listOfHe.get(i).getHolonElementType());
		}
	}
	
	
	public void displayListOfHolonObject(List<HolonObject> listOfHo){
		for (int i = 0; i < listOfHo.size(); i++) {
			
			System.out.println("Holon Manager Name of Holon Object:'"+(i+1)+"' is:"+listOfHo.get(i).getHolonManager().getName());
			System.out.println("Holon Object Type: '"+(i+1)+"' is:"+listOfHo.get(i).getHolonObjectType());
			System.out.println("Priority of Holon Object: '"+(i+1)+"' is:"+listOfHo.get(i).getPriority());
			System.out.println("Line Connected State of Holon Object : '"+(i+1)+"' is:"+listOfHo.get(i).isLineConnectedState());
			displayListOfHolonElements(listOfHo.get(i).getListOfHe());
			
		}
	}
}
