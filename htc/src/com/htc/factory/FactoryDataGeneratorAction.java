package com.htc.factory;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.htc.hibernate.pojo.HolonElementType;
import com.htc.hibernate.pojo.HolonObjectType;
import com.htc.utilities.CommonUtilities;

public class FactoryDataGeneratorAction extends CommonUtilities {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(FactoryDataGeneratorAction.class);

	public void factoryDataGenerator() {
		try {
			Integer totalHolonObjectTypes = getRequest().getParameter("totalHolonObjectTypes")!=null?Integer.parseInt(getRequest().getParameter("totalHolonObjectTypes")):0;
			String htmlIdHolonObjectTypes = getRequest().getParameter("htmlIdHolonObjectTypes")!=null?getRequest().getParameter("htmlIdHolonObjectTypes"):"";
			String htmlValuesHolonObjectTypes = getRequest().getParameter("htmlValuesHolonObjectTypes")!=null?getRequest().getParameter("htmlValuesHolonObjectTypes"):"";
			System.out.println("No of HolonObject Types = "+totalHolonObjectTypes);
			System.out.println("htmlIdHolonObjectTypes = "+htmlIdHolonObjectTypes);
			System.out.println("htmlValuesHolonObjectTypes = "+htmlValuesHolonObjectTypes);

			String[] holonObjectTypesIdsList = htmlIdHolonObjectTypes.replaceAll("holonObjectType_", "").split("~~");
			String[] holonObjectTypesValues = htmlValuesHolonObjectTypes.split("~~");
		
			Map<Integer, Integer> holonObjectTypeProbabilityMap = new TreeMap<Integer, Integer>();
			for(int i=0;i<holonObjectTypesIdsList.length;i++) {
				Integer holonObjectTypeId = Integer.parseInt(holonObjectTypesIdsList[i]);
				Integer objectTypeProbability = Integer.parseInt(holonObjectTypesValues[i]);
				holonObjectTypeProbabilityMap.put(holonObjectTypeId, objectTypeProbability);
			}
			
			FactoryUtilities factoryUtilities = new FactoryUtilities();
			factoryUtilities.createHolonObjects(holonObjectTypeProbabilityMap);
			
			
			getResponse().setContentType("text/html");
			getResponse().getWriter().write("Holon Objects creation in progress...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public void factoryListHolonObjectType() {
		try {
			ArrayList<HolonObjectType> holonObjectTypes = getHolonObjectTypeService().getAllHolonObjectType();
			StringBuffer holonObjectTypeList = new StringBuffer();
			for(HolonObjectType holonObjectType:holonObjectTypes){
				holonObjectTypeList.append(holonObjectType.getId()+"~");
				holonObjectTypeList.append(holonObjectType.getName()+"~");
				holonObjectTypeList.append(holonObjectType.getPriority());
				holonObjectTypeList.append("*");
			}
			holonObjectTypeList.replace(holonObjectTypeList.length()-1, holonObjectTypeList.length(), "");
			getResponse().setContentType("text/html");
			getResponse().getWriter().write(holonObjectTypeList.toString());
		} catch(Exception e) {
			log.info("Exception "+e.getMessage()+" occurred in factoryListHolonObjectType()");
			e.printStackTrace();
		}
	}

	public void factoryListHolonElementType() {
		ArrayList<HolonElementType> holonElementTypes = getHolonElementTypeService().getAllHolonElementType();
		StringBuffer holonElementTypeNameList = new StringBuffer();
		String holonElementTypeInfo;
		int sNo = 0;
		for(HolonElementType holonElementType:holonElementTypes) {
			if(holonElementType.getProducer()) {
				holonElementTypeInfo = holonElementType.getName().
					concat(" : (Max. Capacity:"+holonElementType.getMaxCapacity()).concat(", Min. Capacity:"+holonElementType.getMinCapacity()+")").concat(" : [Producer]");
			} else {
				holonElementTypeInfo = holonElementType.getName().
						concat(" : (Max. Capacity:"+holonElementType.getMaxCapacity()).concat(", Min. Capacity:"+holonElementType.getMinCapacity()+")").concat(" : [Consumer]");
			}
			holonElementTypeNameList.append((sNo+1)+" - "+holonElementTypeInfo+"~~");
			sNo++;
			log.info(holonElementTypeInfo);
		}
		getResponse().setContentType("text/html");
		try {
			getResponse().getWriter().write(holonElementTypeNameList.toString());
		} catch (Exception e) {
			log.debug("Exception "+e.getMessage()+" occurred in action factoryListHolonElementType()");
			e.printStackTrace();
		}
	}

}
