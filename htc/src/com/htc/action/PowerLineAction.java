package com.htc.action;

import com.htc.pojo.LatLng;
import com.htc.pojo.PowerLine;

public class PowerLineAction {

	public PowerLine drawMainLine(LatLng start, LatLng end,int maximumCapacity){
		PowerLine mainLine = new PowerLine(start, end, maximumCapacity, PowerLine.LineType.mainLine);
		return mainLine;
	}
	
	public PowerLine drawSubLine(LatLng start, LatLng end,int maximumCapacity){
		PowerLine subLine = new PowerLine(start, end, maximumCapacity, PowerLine.LineType.subLine);
		return subLine;
	}
	
	public void connectToPowerSource(){
		
	}
}
