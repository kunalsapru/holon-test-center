package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.DisasterDao;
import com.htc.hibernate.pojo.Disaster;
import com.htc.hibernate.utilities.DisasterHome;

public class DisasterDaoImpl  implements DisasterDao {
	private DisasterHome disasterHome = new DisasterHome();

	public DisasterHome getDisasterHome() {
		return disasterHome;
	}

	public void setDisasterHome(DisasterHome disasterHome) {
		this.disasterHome = disasterHome;
	}

	@Override
	public Integer persist(Disaster transientInstance) {
		// TODO Auto-generated method stub
		return getDisasterHome().persist(transientInstance);
	}

	@Override
	public Disaster merge(Disaster detachedInstance) {
		// TODO Auto-generated method stub
		return getDisasterHome().merge(detachedInstance);
	}

	@Override
	public Disaster findById(int disasterId) {
		// TODO Auto-generated method stub
		return getDisasterHome().findById(disasterId);
	}

	@Override
	public boolean delete(Disaster persistentInstance) {
		// TODO Auto-generated method stub
		return getDisasterHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<Disaster> getAllDisasterCircles() {
		// TODO Auto-generated method stub
		return getDisasterHome().getAllDisasterCircles();
	}

}
