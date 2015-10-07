package com.htc.simulation.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.Simulation;
import com.htc.simulation.locations.SimulationLocationMainLines;
import com.htc.utilities.CommonUtilities;
import com.htc.utilities.ConstantValues;

public class SimulationAction extends CommonUtilities {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(SimulationAction.class);
	
	public void drawPowerLinesSimulation(){
		try {
			//Code to add main power line from SimulationLocationMainLines class
			ArrayList<String> mainLineLocationListSimulation = SimulationLocationMainLines.mainLinesLocationList;
			for(String mainLineLocationData : mainLineLocationListSimulation) {
				Boolean isConnected = false;
				Integer maxCapacity = 1000;
				String powerLineType = ConstantValues.MAINLINE;
				String reasonDown = "";
				Double latStart = Double.parseDouble(mainLineLocationData.split("~")[0].split("!")[0]);
				Double lngStart = Double.parseDouble(mainLineLocationData.split("~")[0].split("!")[1]);
				Double latEnd = Double.parseDouble(mainLineLocationData.split("~")[1].split("!")[0]);
				Double lngEnd = Double.parseDouble(mainLineLocationData.split("~")[1].split("!")[1]);
				
				LatLng StartLatLng = new LatLng(latStart, lngStart);
				LatLng EndLatLng = new LatLng(latEnd, lngEnd);
				int newStartLatLngId = saveLocation(StartLatLng);
				int newEndLatLngId = saveLocation(EndLatLng);
				LatLng savedStartLatLng=getLatLngService().findById(newStartLatLngId);
				LatLng savedEndLatLng=getLatLngService().findById(newEndLatLngId);
				int currentCapacity= 0;
				PowerLine powerLine = new PowerLine();
				powerLine.setCurrentCapacity(currentCapacity);
				powerLine.setIsConnected(isConnected);
				powerLine.setLatLngByDestination(savedEndLatLng);
				powerLine.setLatLngBySource(savedStartLatLng);
				powerLine.setMaximumCapacity(maxCapacity);
				powerLine.setReasonDown(reasonDown);
				powerLine.setType(powerLineType);
				powerLine.setHolonObject(null);
				powerLine.setPowerSource(null);
				Integer newPowerLineID = getPowerLineService().persist(powerLine);
				log.info("Newly created power line object from simulation --> "+newPowerLineID);
				Simulation simulation = new Simulation();
				simulation.setPowerLine(getPowerLineService().findById(newPowerLineID));
				getSimulationService().persist(simulation);
				//Code to add main line ends
			}
			
			ArrayList<PowerLine> powerLineList = getSimulationService().getAllPowerLinesSimulation();
			ArrayList<String> powerLineListArray = new ArrayList<String>();
			String startLocation;
			String endLocation;
			String color;
			if(powerLineList != null) {
				for(int i=0; i<powerLineList.size();i++){
					PowerLine  powerLine = powerLineList.get(i);
					log.info("PowerLine Id: "+powerLine.getId());
					startLocation = powerLine.getLatLngBySource().getLatitude()+"~"+powerLine.getLatLngBySource().getLongitude();
					endLocation = powerLine.getLatLngByDestination().getLatitude()+"~"+powerLine.getLatLngByDestination().getLongitude();
					color= CommonUtilities.getLineColor(CommonUtilities.getPercent(powerLine.getCurrentCapacity(),powerLine.getMaximumCapacity())); 				
					powerLineListArray.add(startLocation+"^"+endLocation+"!"+color+"!"+powerLine.getId()+"*");
				}
			}
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(powerLineListArray.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action drawPowerLinesSimulation()");
			e.printStackTrace();
		}
	}
	
	public void clearAllSimulations(){
		Boolean deleteStatusFlag = false;
		try {
			ArrayList<PowerLine> powerLineList = getSimulationService().getAllPowerLinesSimulation();
			ArrayList<Simulation> simulationList = getSimulationService().getAllSimulations();
			if(simulationList != null) {
				for(Simulation simulation : simulationList) {
					getSimulationService().delete(simulation); //Deleting all simulation entries from simulation table in database.
				}
				deleteStatusFlag = true;
			}
			if(powerLineList != null) {
				for(PowerLine powerLine : powerLineList) {
					getPowerLineService().delete(powerLine); //Deleting all power lines created by simulation from database.
				}
			}
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(deleteStatusFlag.toString());
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action clearAllSimulations()");
			e.printStackTrace();
		}
	}
	
	
}
