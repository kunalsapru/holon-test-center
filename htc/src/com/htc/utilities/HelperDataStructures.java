package com.htc.utilities;

import java.util.LinkedList;
import java.util.List;

import com.htc.pojo.HolonObjectLatLng;
import com.htc.pojo.LatLng;

public class HelperDataStructures {

	
	public enum HolonElementState{
		OverSupply,NormalSupply,MinimumSupply,BrownOut,HeartBeat,BlackOut
	}
	
	public enum HolonElementType{
		Fridge,TV,WashingMachine,SolarPanel,Microwave,Light,Battery,Generator,PowerLines,gas2Power
	}
	
	public static List<HolonObjectLatLng> holonObjectLatLngObjects;

	

	public static List<HolonObjectLatLng> getHolonObjectLatLngObjects() {
		return holonObjectLatLngObjects;
	}



	public static void setHolonObjectLatLngObjects(
			List<HolonObjectLatLng> holonObjectLatLngObjects) {
		HelperDataStructures.holonObjectLatLngObjects = holonObjectLatLngObjects;
	}
	
	public static void generateHardcodedHolonObjectLatLngList(){
		List<HolonObjectLatLng> localHolonObjectLatLngList=new LinkedList<HolonObjectLatLng>();
		HolonObjectLatLng item=generateHolonObjectLatLng(49.863677, 8.554067,49.863578, 8.554266);
		localHolonObjectLatLngList.add(item);
		HolonObjectLatLng item2=generateHolonObjectLatLng(49.863436, 8.552528,49.863315, 8.553064);
		localHolonObjectLatLngList.add(item2);
		HolonObjectLatLng item3=generateHolonObjectLatLng(49.863775, 8.554451,49.863662, 8.554829);
		localHolonObjectLatLngList.add(item3);
		HolonObjectLatLng item4=generateHolonObjectLatLng(49.863285, 8.553779,49.862972, 8.554161);
		localHolonObjectLatLngList.add(item4);
		HolonObjectLatLng item5=generateHolonObjectLatLng(49.863119, 8.552898,49.863074, 8.553163);
		localHolonObjectLatLngList.add(item5);
		HolonObjectLatLng item6=generateHolonObjectLatLng(49.862525, 8.552830,49.862453, 8.553128);
		localHolonObjectLatLngList.add(item6);
		HolonObjectLatLng item7=generateHolonObjectLatLng(49.861824, 8.553585,49.861715, 8.553958);
		localHolonObjectLatLngList.add(item7);
		HolonObjectLatLng item8=generateHolonObjectLatLng(49.862722, 8.554171,49.862590, 8.554440);
		localHolonObjectLatLngList.add(item8);
		HolonObjectLatLng item9=generateHolonObjectLatLng(49.862301, 8.554122,49.862213, 8.554339);
		localHolonObjectLatLngList.add(item9);
		HolonObjectLatLng item10=generateHolonObjectLatLng(49.862155, 8.553207,49.862075, 8.553511);
		localHolonObjectLatLngList.add(item10);
		
		setHolonObjectLatLngObjects(localHolonObjectLatLngList);//This is the required data given by this contract.
	}
	
	private static HolonObjectLatLng generateHolonObjectLatLng(double latne, double lngne,double latsw, double lngsw){
		LatLng latlngne=new LatLng();
		latlngne.setLat(latne);
		latlngne.setLng(lngne);
	
		LatLng latlngsw=new LatLng();
		latlngsw.setLat(latsw);
		latlngsw.setLng(lngsw);
		
		HolonObjectLatLng holonObjectLatLng=new HolonObjectLatLng(latlngne, latlngsw);
		return holonObjectLatLng;

	}

}
