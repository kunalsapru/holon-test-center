package com.htc.service;

import java.util.ArrayList;
import com.htc.hibernate.pojo.HolonElementType;

public interface HolonElementTypeService {
	
	public Integer persist(HolonElementType transientInstance);
	public HolonElementType merge(HolonElementType detachedInstance);
	public HolonElementType findById(int holonElementTypeId);
	public boolean delete(HolonElementType persistentInstance);
	public ArrayList<HolonElementType> getAllHolonElementType();

}
