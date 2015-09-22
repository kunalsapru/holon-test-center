package com.htc.dao;

import java.util.ArrayList;
import com.htc.hibernate.pojo.Disaster;

public interface DisasterDao {
	public Integer persist(Disaster transientInstance);
	public Disaster merge(Disaster detachedInstance);
	public Disaster findById(int disasterId);
	public boolean delete(Disaster persistentInstance);
	public ArrayList<Disaster> getAllDisasterCircles();
}
