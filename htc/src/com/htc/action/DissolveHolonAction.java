package com.htc.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.hibernate.pojo.Supplier;
import com.htc.utilities.CommonUtilities;
import com.htc.utilities.ConstantValues;

public class DissolveHolonAction extends CommonUtilities {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(DissolveHolonAction.class);

	public void dissolveHolon() {
		try {
			Integer holonCoordinatorId = getRequest().getParameter("holonCoordinatorId")!=null?Integer.parseInt(getRequest().getParameter("holonCoordinatorId")):0;
			String responseDissolveHolon = "false";
			StringBuffer responseDissolveHolonTrue = new StringBuffer();
			if(holonCoordinatorId > 0) {
				HolonObject holonCoordinator = getHolonObjectService().findById(holonCoordinatorId);
				Integer currentEnergyRequiredHolon = 0;
				Integer flexibilityHolon = 0;
				Map<String, String> holonEnergyDetails = getHolonEnergyDetails(holonCoordinator);
				currentEnergyRequiredHolon = Integer.parseInt(holonEnergyDetails.get("currentEnergyRequiredHolon"));
				flexibilityHolon = Integer.parseInt(holonEnergyDetails.get("flexibilityHolon"));
				//Holon will dissolve only if the flexibility of current holon is zero and current energy requirement is greater than zero
				if(flexibilityHolon == 0 && currentEnergyRequiredHolon > 0) {
					PowerLine powerLine = getPowerLineService().getPowerLineByHolonObject(holonCoordinator);
					if(powerLine != null) {
						ArrayList<HolonObject> connectedHolonObjectsOfAllHolons = getHolonObjectListByConnectedPowerLinesOfAllHolons(powerLine, "common");
						Map<String, ArrayList<HolonObject>> mapOfHolonCoordinatorsOfAllHolons = getHolonCoordinatorsOfAllHolons(connectedHolonObjectsOfAllHolons);
						ArrayList<HolonObject> redHolonCoordinatorsList = mapOfHolonCoordinatorsOfAllHolons.get("redCoordinators");
						ArrayList<HolonObject> yellowHolonCoordinatorsList = mapOfHolonCoordinatorsOfAllHolons.get("yellowCoordinators");
						ArrayList<HolonObject> blueHolonCoordinatorsList = mapOfHolonCoordinatorsOfAllHolons.get("blueCoordinators");
						ArrayList<HolonObject> greenHolonCoordinatorsList = mapOfHolonCoordinatorsOfAllHolons.get("greenCoordinators");
						HolonObject redHolonCoordinator = null;
						HolonObject yellowHolonCoordinator = null;
						HolonObject blueHolonCoordinator = null;
						HolonObject greenHolonCoordinator = null;
						if(redHolonCoordinatorsList!=null && redHolonCoordinatorsList.size() == 1) {
							redHolonCoordinator = redHolonCoordinatorsList.get(0);
						}
						if(yellowHolonCoordinatorsList!=null && yellowHolonCoordinatorsList.size() == 1) {
							yellowHolonCoordinator = yellowHolonCoordinatorsList.get(0);
						}
						if(blueHolonCoordinatorsList!=null && blueHolonCoordinatorsList.size() == 1) {
							blueHolonCoordinator = blueHolonCoordinatorsList.get(0);
						}
						if(greenHolonCoordinatorsList!=null && greenHolonCoordinatorsList.size() == 1) {
							greenHolonCoordinator = greenHolonCoordinatorsList.get(0);
						}
						HolonObject bestHolonCoordinatorMatch = null;
						Integer redBenchmarkEnergy = 0;
						Integer yellowBenchmarkEnergy = 0;
						Integer greenBenchmarkEnergy = 0;
						Integer blueBenchmarkEnergy = 0;
						
						if(redHolonCoordinator != null) {
							Integer currentEnergyRequiredHolonTemp = 0;
							Integer flexibilityHolonTemp = 0;
							Map<String, String> holonEnergyDetailsTemp = getHolonEnergyDetails(redHolonCoordinator);
							currentEnergyRequiredHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("currentEnergyRequiredHolon"));
							flexibilityHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("flexibilityHolon"));
							redBenchmarkEnergy = (flexibilityHolonTemp-currentEnergyRequiredHolonTemp)-currentEnergyRequiredHolon;
							if(redBenchmarkEnergy >= 0) {
								bestHolonCoordinatorMatch = redHolonCoordinator;
							}
						}
						if(bestHolonCoordinatorMatch == null && yellowHolonCoordinator != null) {
							Integer currentEnergyRequiredHolonTemp = 0;
							Integer flexibilityHolonTemp = 0;
							Map<String, String> holonEnergyDetailsTemp = getHolonEnergyDetails(yellowHolonCoordinator);
							currentEnergyRequiredHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("currentEnergyRequiredHolon"));
							flexibilityHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("flexibilityHolon"));
							yellowBenchmarkEnergy = (flexibilityHolonTemp-currentEnergyRequiredHolonTemp)-currentEnergyRequiredHolon;
							if(yellowBenchmarkEnergy > redBenchmarkEnergy && yellowBenchmarkEnergy > 0) {
								bestHolonCoordinatorMatch = yellowHolonCoordinator;
							}
						}
						if(bestHolonCoordinatorMatch == null && greenHolonCoordinator != null) {
							Integer currentEnergyRequiredHolonTemp = 0;
							Integer flexibilityHolonTemp = 0;
							Map<String, String> holonEnergyDetailsTemp = getHolonEnergyDetails(greenHolonCoordinator);
							currentEnergyRequiredHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("currentEnergyRequiredHolon"));
							flexibilityHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("flexibilityHolon"));
							greenBenchmarkEnergy = (flexibilityHolonTemp-currentEnergyRequiredHolonTemp)-currentEnergyRequiredHolon;
							if(greenBenchmarkEnergy > yellowBenchmarkEnergy && greenBenchmarkEnergy > 0) {
								bestHolonCoordinatorMatch = greenHolonCoordinator;
							}
						}
						if(bestHolonCoordinatorMatch == null && blueHolonCoordinator != null) {
							Integer currentEnergyRequiredHolonTemp = 0;
							Integer flexibilityHolonTemp = 0;
							Map<String, String> holonEnergyDetailsTemp = getHolonEnergyDetails(blueHolonCoordinator);
							currentEnergyRequiredHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("currentEnergyRequiredHolon"));
							flexibilityHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("flexibilityHolon"));
							blueBenchmarkEnergy = (flexibilityHolonTemp-currentEnergyRequiredHolonTemp)-currentEnergyRequiredHolon;
							if(blueBenchmarkEnergy > greenBenchmarkEnergy && blueBenchmarkEnergy > 0) {
								bestHolonCoordinatorMatch = yellowHolonCoordinator;
							}
						}
						if(bestHolonCoordinatorMatch != null) {
							if(bestHolonCoordinatorMatch.getIsCoordinator() && bestHolonCoordinatorMatch.getHolon()!=null) {
								HolonObject newHolonCoordinator = null;
								responseDissolveHolon = "true";
								Holon newHolon = bestHolonCoordinatorMatch.getHolon();
								Holon oldHolon = holonCoordinator.getHolon();
								BigDecimal competencyTrust1 = bestHolonCoordinatorMatch.getCoordinatorCompetency().multiply(bestHolonCoordinatorMatch.getTrustValue());
								BigDecimal competencyTrust2 = holonCoordinator.getCoordinatorCompetency().multiply(holonCoordinator.getTrustValue());
								if(competencyTrust1.compareTo(competencyTrust2) == -1) {
									responseDissolveHolonTrue.append(bestHolonCoordinatorMatch.getId()+"!"+newHolon.getColor()+"*");
									bestHolonCoordinatorMatch.setIsCoordinator(false);
									getHolonObjectService().merge(bestHolonCoordinatorMatch);
									newHolonCoordinator = holonCoordinator;
								} else {
									responseDissolveHolonTrue.append(holonCoordinator.getId()+"!"+newHolon.getColor()+"*");
									holonCoordinator.setIsCoordinator(false);
									newHolonCoordinator = bestHolonCoordinatorMatch;
								}
								//Updating power sources connected to the current holon
								ArrayList<PowerSource> listOfPowerSources = getPowerSourceService().findByHolonCoordinator(holonCoordinator);
								for(PowerSource powerSource : listOfPowerSources) {
									powerSource.setHolonCoordinator(newHolonCoordinator);
									getPowerSourceService().merge(powerSource);
								}
								//Setting holon objects of current holon in list to set color on ajaz request completion.
								for(HolonObject holonObject : connectedHolonObjectsOfAllHolons) {
									if(holonObject.getHolon().getId() == holonCoordinator.getHolon().getId()) {
										responseDissolveHolonTrue.append(holonObject.getId()+"~");
										holonObject.setHolon(newHolon);
										getHolonObjectService().merge(holonObject);
									}
								}
								holonCoordinator.setHolon(newHolon);
								getHolonObjectService().merge(holonCoordinator);
								System.out.println("Old Holon --> "+oldHolon.getName());
								System.out.println("New Holon --> "+newHolon.getName());
							}
						} else {
							responseDissolveHolon = "noOtherHolonFound";
						}
					}
					
				}
			}
			//Calling the response function and setting the content type of response.
			getResponse().setContentType("text/html");
			if(responseDissolveHolon.equalsIgnoreCase("true")) {
				getResponse().getWriter().write(responseDissolveHolonTrue.toString());
			} else {
				getResponse().getWriter().write(responseDissolveHolon);
			}
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action dissolveHolon()");
			e.printStackTrace();
		}
	}
	
	public void checkDynamicCurrentEnergyRequired() {
		try {
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;
			HolonObject holonObject = getHolonObjectService().findById(holonObjectId);
			Integer currentEnergyRequired = 0;
			if(holonObject != null) {
				currentEnergyRequired = getHolonObjectEnergyDetails(holonObject).get("currentEnergyRequired");
			}
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(currentEnergyRequired+"");
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action startDynamicHolon()");
			e.printStackTrace();
		}
	}
	
	public void startDynamicHolonMerger() {
		try {
			Integer holonObjectId = getRequest().getParameter("holonObjectId")!=null?Integer.parseInt(getRequest().getParameter("holonObjectId")):0;
			Integer dynamicCurrentEnergyRequired = getRequest().getParameter("dynamicCurrentEnergyRequired")!=null?
					Integer.parseInt(getRequest().getParameter("dynamicCurrentEnergyRequired")):0;
			HolonObject holonObject = getHolonObjectService().findById(holonObjectId);
			String startDynamicHolonMergerResponse = "false";
			StringBuffer startDynamicHolonMergerResponseBuffer = new StringBuffer();
			
			if(holonObject != null && dynamicCurrentEnergyRequired > 0) {
				PowerLine powerLine = getPowerLineService().getPowerLineByHolonObject(holonObject);
				if(powerLine != null) {
					ArrayList<HolonObject> connectedHolonObjectsOfAllHolons = getHolonObjectListByConnectedPowerLinesOfAllHolons(powerLine, "common");
					Map<String, ArrayList<HolonObject>> mapOfHolonCoordinatorsOfAllHolons = getHolonCoordinatorsOfAllHolons(connectedHolonObjectsOfAllHolons);
					ArrayList<HolonObject> redHolonCoordinatorsList = mapOfHolonCoordinatorsOfAllHolons.get("redCoordinators");
					ArrayList<HolonObject> yellowHolonCoordinatorsList = mapOfHolonCoordinatorsOfAllHolons.get("yellowCoordinators");
					ArrayList<HolonObject> blueHolonCoordinatorsList = mapOfHolonCoordinatorsOfAllHolons.get("blueCoordinators");
					ArrayList<HolonObject> greenHolonCoordinatorsList = mapOfHolonCoordinatorsOfAllHolons.get("greenCoordinators");
					HolonObject redHolonCoordinator = null;
					HolonObject yellowHolonCoordinator = null;
					HolonObject blueHolonCoordinator = null;
					HolonObject greenHolonCoordinator = null;
					if(redHolonCoordinatorsList!=null && redHolonCoordinatorsList.size() == 1) {
						redHolonCoordinator = redHolonCoordinatorsList.get(0);
					}
					if(yellowHolonCoordinatorsList!=null && yellowHolonCoordinatorsList.size() == 1) {
						yellowHolonCoordinator = yellowHolonCoordinatorsList.get(0);
					}
					if(blueHolonCoordinatorsList!=null && blueHolonCoordinatorsList.size() == 1) {
						blueHolonCoordinator = blueHolonCoordinatorsList.get(0);
					}
					if(greenHolonCoordinatorsList!=null && greenHolonCoordinatorsList.size() == 1) {
						greenHolonCoordinator = greenHolonCoordinatorsList.get(0);
					}
					HolonObject bestHolonCoordinatorMatch = null;
					Integer redBenchmarkEnergy = 0;
					Integer yellowBenchmarkEnergy = 0;
					Integer greenBenchmarkEnergy = 0;
					Integer blueBenchmarkEnergy = 0;
					
					if(redHolonCoordinator != null) {
						Integer currentEnergyRequiredHolonTemp = 0;
						Integer flexibilityHolonTemp = 0;
						Map<String, String> holonEnergyDetailsTemp = getHolonEnergyDetails(redHolonCoordinator);
						currentEnergyRequiredHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("currentEnergyRequiredHolon"));
						flexibilityHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("flexibilityHolon"));
						redBenchmarkEnergy = (flexibilityHolonTemp-currentEnergyRequiredHolonTemp)-dynamicCurrentEnergyRequired;
						if(redBenchmarkEnergy >= 0) {
							bestHolonCoordinatorMatch = redHolonCoordinator;
						}
					}
					if(bestHolonCoordinatorMatch == null && yellowHolonCoordinator != null) {
						Integer currentEnergyRequiredHolonTemp = 0;
						Integer flexibilityHolonTemp = 0;
						Map<String, String> holonEnergyDetailsTemp = getHolonEnergyDetails(yellowHolonCoordinator);
						currentEnergyRequiredHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("currentEnergyRequiredHolon"));
						flexibilityHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("flexibilityHolon"));
						yellowBenchmarkEnergy = (flexibilityHolonTemp-currentEnergyRequiredHolonTemp)-dynamicCurrentEnergyRequired;
						if(yellowBenchmarkEnergy > redBenchmarkEnergy && yellowBenchmarkEnergy > 0) {
							bestHolonCoordinatorMatch = yellowHolonCoordinator;
						}
					}
					if(bestHolonCoordinatorMatch == null && greenHolonCoordinator != null) {
						Integer currentEnergyRequiredHolonTemp = 0;
						Integer flexibilityHolonTemp = 0;
						Map<String, String> holonEnergyDetailsTemp = getHolonEnergyDetails(greenHolonCoordinator);
						currentEnergyRequiredHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("currentEnergyRequiredHolon"));
						flexibilityHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("flexibilityHolon"));
						greenBenchmarkEnergy = (flexibilityHolonTemp-currentEnergyRequiredHolonTemp)-dynamicCurrentEnergyRequired;
						if(greenBenchmarkEnergy > yellowBenchmarkEnergy && greenBenchmarkEnergy > 0) {
							bestHolonCoordinatorMatch = greenHolonCoordinator;
						}
					}
					if(bestHolonCoordinatorMatch == null && blueHolonCoordinator != null) {
						Integer currentEnergyRequiredHolonTemp = 0;
						Integer flexibilityHolonTemp = 0;
						Map<String, String> holonEnergyDetailsTemp = getHolonEnergyDetails(blueHolonCoordinator);
						currentEnergyRequiredHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("currentEnergyRequiredHolon"));
						flexibilityHolonTemp = Integer.parseInt(holonEnergyDetailsTemp.get("flexibilityHolon"));
						blueBenchmarkEnergy = (flexibilityHolonTemp-currentEnergyRequiredHolonTemp)-dynamicCurrentEnergyRequired;
						if(blueBenchmarkEnergy > greenBenchmarkEnergy && blueBenchmarkEnergy > 0) {
							bestHolonCoordinatorMatch = yellowHolonCoordinator;
						}
					}
					if(bestHolonCoordinatorMatch != null) {
						if(bestHolonCoordinatorMatch.getIsCoordinator() && bestHolonCoordinatorMatch.getHolon()!=null) {
							startDynamicHolonMergerResponse = "true";
							Holon newHolon = bestHolonCoordinatorMatch.getHolon();
							Holon oldHolon = holonObject.getHolon();
							if(holonObject.getIsCoordinator()) {
								BigDecimal competencyTrust1 = bestHolonCoordinatorMatch.getCoordinatorCompetency().multiply(bestHolonCoordinatorMatch.getTrustValue());
								BigDecimal competencyTrust2 = holonObject.getCoordinatorCompetency().multiply(holonObject.getTrustValue());
								if(competencyTrust1.compareTo(competencyTrust2) == -1) {
									startDynamicHolonMergerResponseBuffer.append(bestHolonCoordinatorMatch.getId()+"!"+newHolon.getColor()+"!"+holonObjectId);
									bestHolonCoordinatorMatch.setIsCoordinator(false);
									getHolonObjectService().merge(bestHolonCoordinatorMatch);
								} else {
									startDynamicHolonMergerResponseBuffer.append(holonObject.getId()+"!"+newHolon.getColor()+"!"+holonObjectId);
									holonObject.setIsCoordinator(false);
								}
							} else {
								startDynamicHolonMergerResponseBuffer.append(0+"!"+newHolon.getColor()+"!"+holonObjectId);
							}
							ArrayList<Supplier> listSupplier = getSupplierService().getSupplierListForConsumerOrProducer(holonObject);
							for(Supplier supplier : listSupplier) {
								//Before joining a new holon, resetting previous communications between holon objects of old holon
								supplier.setMessageStatus(ConstantValues.CONNECTION_RESET);
								getSupplierService().merge(supplier);
							}
							holonObject.setHolon(newHolon);
							getHolonObjectService().merge(holonObject);
							System.out.println("Old Holon --> "+oldHolon.getName());
							System.out.println("New Holon --> "+newHolon.getName());
						}
					}
				}
			}
			getResponse().setContentType("text/html");
			if(startDynamicHolonMergerResponse.equalsIgnoreCase("true")) {
				getResponse().getWriter().write(startDynamicHolonMergerResponseBuffer.toString());
			} else {
				getResponse().getWriter().write(startDynamicHolonMergerResponse);
			}
		} catch (Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in action startDynamicHolon()");
			e.printStackTrace();
		}
	}
	
}
