package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonObjectType;

public interface HolonObjectTypeDao {

	public Integer persist(HolonObjectType transientInstance);
	public HolonObjectType merge(HolonObjectType detachedInstance);
	public HolonObjectType findById(int holonObjectTypeId);
	public boolean delete(HolonObjectType persistentInstance);
	public ArrayList<HolonObjectType> getAllHolonObjectType();

}
