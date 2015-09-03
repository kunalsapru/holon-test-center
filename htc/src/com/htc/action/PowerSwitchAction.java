package com.htc.action;


import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.utilities.CommonUtilities;
import com.htc.utilities.ConstantValues;


public class PowerSwitchAction extends CommonUtilities {

	/**
	 * 
	 */
	private Map<Integer, PowerLine> listOfAllConnectedPowerLines = new TreeMap<Integer, PowerLine>();
	private ArrayList<PowerLine> listOfAllNeighbouringConnectedPowerLines = new ArrayList<PowerLine>();
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(PowerSwitchAction.class);

//	public void SwitchOn(HolonObject holonObject, PowerSwitch powerSwitch){
//		if(powerSwitch.getBelongsTo() == holonObject.getId()){
//			powerSwitch.setStatus(true);
//		}
//	}
//	
//	public void SwitchOff(HolonObject holonObject, PowerSwitch powerSwitch){
//		if(powerSwitch.getBelongsTo() == holonObject.getId()){
//			powerSwitch.setStatus(false);
//		}
//	}
	
	public void addPowerSwitchForHolonObject(HolonObject holonObject){
		PowerSwitch powerSwitch=new PowerSwitch();
		powerSwitch.setPowerLineByPowerLineA(null);
		powerSwitch.setStatus(true);
		
		//Calling service method to save the object in database and saving the auto-incremented ID in an integer
		Integer newPowerSwitchID = getPowerSwitchService().persist(powerSwitch);
		log.info("NewLy Generated PowerSwitch  ID --> "+newPowerSwitchID);

	}
	
	public void createPowerSwitch(){
	try{
		
		PowerSwitch powerSwitch= new PowerSwitch();
		log.info("Power Line Id "+getRequest().getParameter("powerLineId"));
		Integer powerLineId= getRequest().getParameter("powerLineId")!=null?Integer.parseInt(getRequest().getParameter("powerLineId")):0;
		Double latSwitch = getRequest().getParameter("switchPositionLat")!=null?Double.parseDouble(getRequest().getParameter("switchPositionLat")):0D;
		Double lngSwitch = getRequest().getParameter("switchPositionLng")!=null?Double.parseDouble(getRequest().getParameter("switchPositionLng")):0D;
		LatLng switchLatLng = new LatLng(latSwitch, lngSwitch);
		Integer switchlocationId = saveLocation(switchLatLng);
		LatLng switchLatLng2 = getLatLngService().findById(switchlocationId);
		
		Map<String, PowerLine> powerLineMap = new PowerLineAction().splitPowerLineByLocation(powerLineId,switchLatLng2);
		PowerLine powerLineA = powerLineMap.get("powerLineA");
		PowerLine powerLineB = powerLineMap.get("powerLineB");
		
		powerSwitch.setLatLng(switchLatLng2);
		powerSwitch.setPowerLineByPowerLineA(powerLineA);
		powerSwitch.setPowerLineByPowerLineB(powerLineB);
		powerSwitch.setStatus(false);
		Integer newPowerSwitchId= getPowerSwitchService().persist(powerSwitch);
		String resp= newPowerSwitchId.toString()+"*"+powerLineA.getId().toString()+"*"+powerLineB.getId();
		
		System.out.println("---------------------->>>"+newPowerSwitchId);
		PowerSwitch powerSwitch2 = getPowerSwitchService().findById(newPowerSwitchId);
		if(!powerSwitch2.getStatus()) {
			PowerLine newPowerLineA = powerSwitch2.getPowerLineByPowerLineA();
			PowerLine newPowerLineB = powerSwitch2.getPowerLineByPowerLineB();
			ArrayList<PowerLine> connectedLines1 = connectedPowerLines(newPowerLineA.getId());
			Holon holon1 = null;
			Holon holon2 = null;
			for(PowerLine powerLine : connectedLines1) {
				System.out.println("1 --> "+powerLine.getId());
				if(holon1 == null) {
					if(powerLine.getType().equalsIgnoreCase(ConstantValues.SUBLINE) || powerLine.getType().equalsIgnoreCase(ConstantValues.POWERSUBLINE)) {
						if(powerLine.getHolonObject() != null) {
							holon1 = powerLine.getHolonObject().getHolon();
						} else if(powerLine.getPowerSource() != null) {
							holon1 = powerLine.getPowerSource().getHolonCoordinator().getHolon();
						}
						
					}
				}
			}
			ArrayList<PowerLine> connectedLines2 = connectedPowerLines(newPowerLineB.getId());
			for(PowerLine powerLine : connectedLines2) {
				System.out.println("2 --> "+powerLine.getId());
				if(holon2 == null) {
					if(powerLine.getType().equalsIgnoreCase(ConstantValues.SUBLINE) || powerLine.getType().equalsIgnoreCase(ConstantValues.POWERSUBLINE)) {
						if(powerLine.getHolonObject() != null) {
							holon2 = powerLine.getHolonObject().getHolon();
						} else if(powerLine.getPowerSource() != null) {
							holon2 = powerLine.getPowerSource().getHolonCoordinator().getHolon();
						}
						
					}
				}
			}
			
			
			HolonObject hk1 = findConnectedHolonCoordinatorByHolon(holon1, newPowerLineA);
			HolonObject hk2 = findConnectedHolonCoordinatorByHolon(holon2, newPowerLineB);
			if(hk1 != null) {
				System.out.println("hk1 = "+hk1.getId());
			} else {
				System.out.println("hk1 = null");
			}
			if(hk2 != null) {
				System.out.println("hk2 = "+hk2.getId());
			} else {
				System.out.println("hk2 = null");
			}
			ArrayList<HolonObject> h1=getHolonCoordinatorByElectionUsingForMainLineAndSwitch(newPowerLineA, "common");
			ArrayList<HolonObject> h2=getHolonCoordinatorByElectionUsingForMainLineAndSwitch(newPowerLineB, "powerSwitch");
			if(h1.size()>0 && h2.size()>0)
			{
				//resp="*"+h1.get(0)+"*"+h2.get(0);
			}
			
		}
		Boolean flag= false;
		// get holon Objects from two new powerLines and make coordinator
		/*if(newPowerLineA!= null && newPowerLineB!= null){
			// poweLine divides the holon into parts. One powerLine has coordinator other don't
			ArrayList<PowerLine>connectedPowerLinesA  = connectedPowerLines(newPowerLineA.getId());
			if(connectedPowerLinesA.size() > 0){
				for(PowerLine powerLine2 : connectedPowerLinesA){
					if(powerLine2.getType()!=null){
						if(powerLine2.getType().equalsIgnoreCase(ConstantValues.SUBLINE) && powerLine2.getHolonObject()!=null && powerLine2.getHolonObject().getIsCoordinator()==true){
							flag=true;
						}
					}
				}
			}
			
			
			if(flag){
				System.out.println("Flag is:::"+flag+"*******"+powerLineA.getId());
				updateHolonObjectsAndPowerSources(powerLineA.getId());
			}else{
				System.out.println("Flag isssss:::"+flag);
				updateHolonObjectsAndPowerSources(powerLineB.getId());
				System.out.println("Updated powerLine B"+"***"+powerLineB.getId());
			}
			
			System.out.println("Holon A turn");
			ArrayList<HolonObject> holonObjectA= getHolonCoordinatorByElectionUsingForMainLineAndSwitch(powerLineA);
			System.out.println("Holon B turn");
			ArrayList<HolonObject> holonObjectB= getHolonCoordinatorByElectionUsingForMainLineAndSwitch(powerLineB);
			if(holonObjectA.size()>0 && holonObjectB.size()>0){
				System.out.println("Coordinator of A------->"+holonObjectA.get(0).getId()+"**********"+"Coordinator of B------->"+holonObjectB.get(0).getId());
			}
				
		}*/
		
		
		getResponse().setContentType("text/html");
		getResponse().getWriter().write(resp);
		
	}
	catch(Exception e){
		log.info("Exception is::"+ e);
	}
			
	}
	
	public void getListPowerSwitch(){
		try{
			ArrayList<PowerSwitch> powerSwitchList = getPowerSwitchService().getAllPowerSwitch();
			ArrayList<String> swListArray = new ArrayList<String>();
			PowerSwitch powerSwitch= null;
			for(int i=0;i<powerSwitchList.size();i++)
			{
				powerSwitch=powerSwitchList.get(i);
				Double switchLatitude= powerSwitch.getLatLng().getLatitude();
				Double switchLongitude= powerSwitch.getLatLng().getLongitude();
				Integer switchId = powerSwitch.getId();
				boolean statusBool=powerSwitch.getStatus();
				int status=0;
				if(statusBool){
				status=1;
				}
				swListArray.add(switchLatitude+"^"+switchLongitude+"^"+switchId+"^"+status+"*");
				
			}
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(swListArray.toString());
		}
		catch(Exception e){
			log.info("Exception in getListPowerSwitch");
		}
	}


	public void getSwitchInfo() {
	try {
			Integer powerSwitchId= getRequest().getParameter("powerSwitchId")!=null?Integer.parseInt(getRequest().getParameter("powerSwitchId")):0;
			PowerSwitch powerSwitch = getPowerSwitchService().findById(powerSwitchId);
			Double switchLatitude= powerSwitch.getLatLng().getLatitude();
			Double switchLongitude= powerSwitch.getLatLng().getLongitude();
			Integer switchId = powerSwitch.getId();
			Integer powerLineAId = powerSwitch.getPowerLineByPowerLineA().getId();
			Integer powerLineBId = powerSwitch.getPowerLineByPowerLineB().getId();
			boolean statusBool=powerSwitch.getStatus();
			int status=0;
			if(statusBool){
				status=1;
			}
			String contentString=switchLatitude+"^"+switchLongitude+"^"+switchId+"^"+powerLineAId+"^"+powerLineBId+"^"+status;		
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(contentString);
		} catch(Exception e) {
		log.info("Exception in getListPowerSwitch");
			}
	}
	
	public void powerSwitchOnOff() {
		try {
			Integer powerSwitchId= getRequest().getParameter("powerSwitchId")!=null?Integer.parseInt(getRequest().getParameter("powerSwitchId")):0;
			PowerSwitch pwSw= getPowerSwitchService().findById(powerSwitchId); 
			PowerLine powerLineA = pwSw.getPowerLineByPowerLineA();
			PowerLine powerLineB= pwSw.getPowerLineByPowerLineB();
			boolean psOldStatus=pwSw.getStatus();
			boolean psNewStatus=true;
			Integer psNewIntStatus=1;
			if(psOldStatus) {
				psNewStatus=false;
				//off
				psNewIntStatus=0;
			}
			pwSw.setStatus(psNewStatus);
			getPowerSwitchService().merge(pwSw);
			PowerSwitch powerSwitch2 = getPowerSwitchService().findById(powerSwitchId);
			//String response="";
			if(!powerSwitch2.getStatus()) {
				PowerLine newPowerLineA = powerSwitch2.getPowerLineByPowerLineA();
				PowerLine newPowerLineB = powerSwitch2.getPowerLineByPowerLineB();
				ArrayList<HolonObject> h1=getHolonCoordinatorByElectionUsingForMainLineAndSwitch(newPowerLineA, "common");
				ArrayList<HolonObject> h2=getHolonCoordinatorByElectionUsingForMainLineAndSwitch(newPowerLineB, "powerSwitch");
				//response="*"+h1.get(0).getId()+"*"+h2.get(0).getId();
			} else {
				PowerLine newPowerLineA = powerSwitch2.getPowerLineByPowerLineA();
				ArrayList<HolonObject> h1=getHolonCoordinatorByElectionUsingForMainLineAndSwitch(newPowerLineA, "common");
				//response="*"+h1.get(0);
			}
			
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(psNewIntStatus.toString());
			} catch(Exception e) {
				log.info("Exception in powerSwitchOnOff ");
				}
	}

	public ArrayList<PowerLine> connectedPowerLines(Integer powerLineId) {
		PowerLine powerLine = null;
		if(powerLineId.compareTo(0) > 0 ){
			powerLine = getPowerLineService().findById(powerLineId);
		}
		ArrayList<PowerLine> connectedPowerLines = new ArrayList<PowerLine>();
		ArrayList<PowerLine> removeIndexListA = new ArrayList<PowerLine>();
		ArrayList<PowerLine> removeIndexListB = new ArrayList<PowerLine>();
		
		if(powerLine != null) {
			connectedPowerLines = getPowerLineService().getConnectedPowerLines(powerLine);
		}
		PowerLine powerLine2 = null;
		PowerSwitch powerSwitch = null;
		for(int i =0; i< connectedPowerLines.size();i++) {
			powerLine2 = connectedPowerLines.get(i);
			powerSwitch = getPowerSwitchService().checkSwitchStatusBetweenPowerLines(powerLine, powerLine2);
			if(powerSwitch != null){
				if(!powerSwitch.getStatus()) {
					removeIndexListA.add(powerLine2);
				}
			}
		}
		for(int i=0; i<removeIndexListA.size();i++) {
			connectedPowerLines.remove(removeIndexListA.get(i));
		}
		for(PowerLine powerLine3 : connectedPowerLines) {
			if(!(listOfAllConnectedPowerLines.containsKey(powerLine3.getId()))) {
				listOfAllConnectedPowerLines.put(powerLine3.getId(), powerLine3);
				listOfAllNeighbouringConnectedPowerLines.add(powerLine3);
			}
		}
		for(PowerLine powerLine3 : connectedPowerLines) {
			ArrayList<PowerLine> tempConnectedPowerLines = getPowerLineService().getConnectedPowerLines(powerLine3);

			PowerLine powerLineTemp = null;
			PowerSwitch powerSwitchTemp = null;
			for(int i =0; i< tempConnectedPowerLines.size();i++) {
				powerLineTemp = tempConnectedPowerLines.get(i);
				powerSwitchTemp = getPowerSwitchService().checkSwitchStatusBetweenPowerLines(powerLine, powerLineTemp);
				if(powerSwitchTemp != null){
					if(!powerSwitchTemp.getStatus()) {
						removeIndexListB.add(powerLineTemp);
					}
				}
			}
			for(int i=0; i<removeIndexListB.size();i++) {
				tempConnectedPowerLines.remove(removeIndexListB.get(i));
			}
			for(PowerLine powerLine4 : tempConnectedPowerLines) {
				if(!(listOfAllConnectedPowerLines.containsKey(powerLine4.getId()))) {
					connectedPowerLines(powerLine3.getId());//Recursive call to get list of neighbors of neighbor
				}
			}
		}
		return listOfAllNeighbouringConnectedPowerLines;
	}

	public void setNewPowerLineForExistingSwitch(PowerLine powerLineB) {
		ArrayList<PowerSwitch> powerSwitchList = getPowerSwitchService().getAllPowerSwitch();
		PowerSwitch powerSwitch= null;
		for(int i=0;i<powerSwitchList.size();i++) {
			PowerSwitch pSwitch=powerSwitchList.get(i);
			LatLng switchLocation= pSwitch.getLatLng();
			if(switchLocation.equals(powerLineB.getLatLngByDestination())) {
				powerSwitch =pSwitch;
				break;
			}				
		}
		if(powerSwitch!=null) {
			powerSwitch.setPowerLineByPowerLineA(powerLineB);
			getPowerSwitchService().merge(powerSwitch);
		}
	}

}