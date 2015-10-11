package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.Disaster;
import com.htc.service.DaoAware;
import com.htc.service.DisasterService;

public class DisasterServiceImpl extends DaoAware implements DisasterService{

	@Override
	public Integer persist(Disaster transientInstance) {
		return getDisasterDao().persist(transientInstance);
	}

	@Override
	public Disaster merge(Disaster detachedInstance) {
		return getDisasterDao().merge(detachedInstance);
	}

	@Override
	public Disaster findById(int disasterId) {
		return getDisasterDao().findById(disasterId);
	}

	@Override
	public boolean delete(Disaster persistentInstance) {
		return getDisasterDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<Disaster> getAllDisasterCircles() {
		return getDisasterDao().getAllDisasterCircles();
	}

	@Override
	public int deleteAllDisasters() {
		return getDisasterDao().deleteAllDisasters();
	}

}
