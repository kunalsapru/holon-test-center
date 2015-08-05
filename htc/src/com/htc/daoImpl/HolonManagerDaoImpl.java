package com.htc.daoImpl;

import java.util.ArrayList;
import com.htc.dao.HolonManagerDao;
import com.htc.hibernate.pojo.HolonManager;
import com.htc.hibernate.utilities.HolonManagerHome;

public class HolonManagerDaoImpl implements HolonManagerDao {

	HolonManagerHome holonManagerHome = new HolonManagerHome();

	public HolonManagerHome getHolonManagerHome() {
		return holonManagerHome;
	}

	public void setHolonManagerHome(HolonManagerHome holonManagerHome) {
		this.holonManagerHome = holonManagerHome;
	}

	@Override
	public Integer persist(HolonManager transientInstance) {
		return getHolonManagerHome().persist(transientInstance);
	}

	@Override
	public HolonManager merge(HolonManager detachedInstance) {
		return getHolonManagerHome().merge(detachedInstance);
	}

	@Override
	public HolonManager findById(int holonManagerId) {
		return getHolonManagerHome().findById(holonManagerId);
	}

	@Override
	public boolean delete(HolonManager persistentInstance) {
		return getHolonManagerHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonManager> getAllHolonManager() {
		return getHolonManagerHome().getAllHolonManager();
	}

}
