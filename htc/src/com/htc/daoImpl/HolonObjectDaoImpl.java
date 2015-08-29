package com.htc.daoImpl;

import java.util.ArrayList;
import com.htc.dao.HolonObjectDao;
import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.utilities.HolonObjectHome;

public class HolonObjectDaoImpl implements HolonObjectDao {
	
	private HolonObjectHome holonObjectHome = new HolonObjectHome();

	public HolonObjectHome getHolonObjectHome() {
		return holonObjectHome;
	}

	public void setHolonObjectHome(HolonObjectHome holonObjectHome) {
		this.holonObjectHome = holonObjectHome;
	}

	@Override
	public Integer persist(HolonObject transientInstance) {
		return getHolonObjectHome().persist(transientInstance);
	}

	@Override
	public HolonObject merge(HolonObject detachedInstance) {
		return getHolonObjectHome().merge(detachedInstance);
	}

	@Override
	public HolonObject findById(int holonObjectId) {
		return getHolonObjectHome().findById(holonObjectId);
	}

	@Override
	public boolean delete(HolonObject persistentInstance) {
		return getHolonObjectHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonObject> getAllHolonObject() {
		return getHolonObjectHome().getAllHolonObject();
	}

	@Override
	public ArrayList<HolonObject> findByHolon(Holon holon) {
		return getHolonObjectHome().findByHolon(holon);
	}

	@Override
	public ArrayList<HolonObject> findAllHolonCoordinators() {
		return getHolonObjectHome().findAllHolonCoordinators();
	}

}
