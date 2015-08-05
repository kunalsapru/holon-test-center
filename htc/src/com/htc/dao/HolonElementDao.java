package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonObject;

public interface HolonElementDao {

	public Integer persist(HolonElement transientInstance);
	public HolonElement merge(HolonElement detachedInstance);
	public HolonElement findById(int holonElementId);
	public boolean delete(HolonElement persistentInstance);
	public ArrayList<HolonElement> getAllHolonElement();
	public ArrayList<HolonElement> getHolonElements(HolonObject holonObject);

}
