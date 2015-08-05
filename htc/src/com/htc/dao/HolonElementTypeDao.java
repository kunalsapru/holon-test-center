package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonElementType;

public interface HolonElementTypeDao {

	public Integer persist(HolonElementType transientInstance);
	public HolonElementType merge(HolonElementType detachedInstance);
	public HolonElementType findById(int holonElementTypeId);
	public boolean delete(HolonElementType persistentInstance);
	public ArrayList<HolonElementType> getAllHolonElementType();

}
