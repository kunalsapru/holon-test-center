package com.htc.dao;

import java.util.ArrayList;
import com.htc.hibernate.pojo.HolonManager;

public interface HolonManagerDao {

	public Integer persist(HolonManager transientInstance);
	public HolonManager merge(HolonManager detachedInstance);
	public HolonManager findById(int holonManagerId);
	public boolean delete(HolonManager persistentInstance);
	public ArrayList<HolonManager> getAllHolonManager();

}
