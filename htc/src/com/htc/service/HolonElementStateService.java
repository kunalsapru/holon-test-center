package com.htc.service;

import java.util.ArrayList;
import com.htc.hibernate.pojo.HolonElementState;

public interface HolonElementStateService {
	
	public Integer persist(HolonElementState transientInstance);
	public HolonElementState merge(HolonElementState detachedInstance);
	public HolonElementState findById(int holonElementStateId);
	public boolean delete(HolonElementState persistentInstance);
	public ArrayList<HolonElementState> getAllHolonElementState();

}
