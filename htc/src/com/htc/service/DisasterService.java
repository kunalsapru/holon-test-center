package com.htc.service;

import com.htc.hibernate.pojo.Disaster;

public interface DisasterService {
	public Integer persist(Disaster transientInstance);
	public Disaster merge(Disaster detachedInstance);
	public Disaster findById(int disasterId);
	public boolean delete(Disaster persistentInstance);

}
