package com.htc.dao;

import com.htc.hibernate.pojo.Disaster;

public interface DisasterDao {
	public Integer persist(Disaster transientInstance);
	public Disaster merge(Disaster detachedInstance);
	public Disaster findById(int disasterId);
	public boolean delete(Disaster persistentInstance);
}
