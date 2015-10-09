package com.htc.service;

import java.util.ArrayList;

import com.htc.hibernate.pojo.Disaster;

public interface DisasterService {
	public Integer persist(Disaster transientInstance);
	public Disaster merge(Disaster detachedInstance);
	public Disaster findById(int disasterId);
	public boolean delete(Disaster persistentInstance);
	public ArrayList<Disaster> getAllDisasterCircles();
	public int deleteAllDisasters();

}
