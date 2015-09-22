package com.htc.action;
import java.util.ArrayList;
import java.util.Map;
import org.apache.log4j.Logger;
import com.htc.hibernate.pojo.Disaster;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.utilities.CommonUtilities;

public class DisasterAction extends CommonUtilities {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(DisasterAction.class);
	
	public void getAllPointsInsideCircle(){
		try{
			Disaster disaster= new Disaster();
			Double latitudeOfDisasterCircle = getRequest().getParameter("latitude")!=null?Double.parseDouble(getRequest().getParameter("latitude")):0D;
			Double longitudeOfDisasterCircle = getRequest().getParameter("longitude")!=null?Double.parseDouble(getRequest().getParameter("longitude")):0D;
			Double radiusOfDisasterCircle = getRequest().getParameter("radius")!=null?Double.parseDouble(getRequest().getParameter("radius")):0D;
			LatLng disasterCircleLatLng= new LatLng(latitudeOfDisasterCircle,longitudeOfDisasterCircle);
			//Save the Location in the Lat Lng table
			Integer disasterCircleLocationId = saveLocation(disasterCircleLatLng);
			LatLng disasterCircleLocationId2 = getLatLngService().findById(disasterCircleLocationId);
			disaster.setCenter(disasterCircleLocationId2);
			disaster.setRadius(radiusOfDisasterCircle);
			Integer disasterId= getDisasterService().persist(disaster);
			Disaster disaster2= getDisasterService().findById(disasterId);
			Map<Integer, PowerLine> mapOfAllPowerLinesInsideCircle= getListOfAllPowerLineIdsInsideCircle(latitudeOfDisasterCircle,longitudeOfDisasterCircle,radiusOfDisasterCircle);
			for(Integer powerLineId : mapOfAllPowerLinesInsideCircle.keySet()) {
				System.out.println("ID --> "+powerLineId);
				System.out.println("PowerLine Type --> "+mapOfAllPowerLinesInsideCircle.get(powerLineId).getType());
				PowerLine powerLine = getPowerLineService().findById(powerLineId);
				powerLine.setDisaster(disaster2);
				getPowerLineService().merge(powerLine);
			}
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(disaster2.getId()+"*"+disaster2.getCenter().getLatitude()+"*"+disaster2.getCenter().getLongitude());
						
		}catch(Exception e){
			log.info("Exception "+e.getMessage()+" occurred in getAllPointsInsideCircle()");
			e.printStackTrace();
		}
	}
	
	public void getAllSavedDisasters(){
		try{
			
			ArrayList<Disaster> disasterList = getDisasterService().getAllDisasterCircles();
			ArrayList<String> disasterListArray = new ArrayList<String>();
			for(Disaster disaster1 : disasterList){
				Integer disasterId=disaster1.getId();
				Double radius=disaster1.getRadius();
				Double latitude= disaster1.getCenter().getLatitude();
				Double longitude= disaster1.getCenter().getLongitude();
				disasterListArray.add(disasterId+"^"+radius+"^"+latitude+"^"+longitude+"*");
			}
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(disasterListArray.toString());
			
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action getAllSavedDisasters()");
			e.printStackTrace();
		}
	}
	
	public void deleteAllDisasterCircleFromDatabase(){
		try{
			ArrayList<PowerLine> listPowerLine= getPowerLineService().getAllPowerLineIdsHavingDisaster();
			String response= deleteDisasterMode(listPowerLine).toString();
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(response);
		}catch(Exception e){
			System.out.println("Exception in deleteAllDisasterCircleFromDatabase()");
		}
	}
	
	public void deleteDisasterCircleFromDatabase(){
		Integer disasterId = getRequest().getParameter("disasterId")!=null && getRequest().getParameter("disasterId") != ""?Integer.parseInt(getRequest().getParameter("disasterId")):0;
		Disaster disaster= getDisasterService().findById(disasterId);
		try{
			if(disaster!= null){
				ArrayList<PowerLine> connectedPowerLines = getPowerLineService().getAllPowerLinesWithDisasterId(disaster);
				String response= deleteDisasterMode(connectedPowerLines).toString();
				getResponse().setContentType("text/html");
				getResponse().getWriter().write(response);
			}
		}catch(Exception e){
			System.out.println("Exception in deleteDisasterCircleFromDatabase");
		}
		
	}
}
