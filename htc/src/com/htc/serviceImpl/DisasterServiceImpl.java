package com.htc.serviceImpl;

import com.htc.hibernate.pojo.Disaster;
import com.htc.service.AbstractService;
import com.htc.service.DisasterService;

public class DisasterServiceImpl extends AbstractService implements DisasterService{

	@Override
	public Integer persist(Disaster transientInstance) {
		return getDisasterdao().persist(transientInstance);
	}

	@Override
	public Disaster merge(Disaster detachedInstance) {
		// TODO Auto-generated method stub
		return getDisasterdao().merge(detachedInstance);
	}

	@Override
	public Disaster findById(int disasterId) {
		// TODO Auto-generated method stub
		return getDisasterdao().findById(disasterId);
	}

	@Override
	public boolean delete(Disaster persistentInstance) {
		// TODO Auto-generated method stub
		return getDisasterdao().delete(persistentInstance);
	}

	

}
