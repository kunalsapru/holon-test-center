package com.htc.service;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonElement;

public interface HolonElementService {

	public Integer persist(HolonElement transientInstance);
	public HolonElement merge(HolonElement detachedInstance);
	public HolonElement findById(int holonElementId);
	public boolean delete(HolonElement persistentInstance);
	public ArrayList<HolonElement> getAllHolonElement();

}
