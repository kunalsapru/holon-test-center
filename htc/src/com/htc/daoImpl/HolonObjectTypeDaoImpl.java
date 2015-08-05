package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.HolonObjectTypeDao;
import com.htc.hibernate.pojo.HolonObjectType;
import com.htc.hibernate.utilities.HolonObjectTypeHome;

public class HolonObjectTypeDaoImpl implements HolonObjectTypeDao {
	
	HolonObjectTypeHome holonObjectTypeHome = new HolonObjectTypeHome();

	public HolonObjectTypeHome getHolonObjectTypeHome() {
		return holonObjectTypeHome;
	}

	public void setHolonObjectTypeHome(HolonObjectTypeHome holonObjectTypeHome) {
		this.holonObjectTypeHome = holonObjectTypeHome;
	}

	@Override
	public Integer persist(HolonObjectType transientInstance) {
		return getHolonObjectTypeHome().persist(transientInstance);
	}

	@Override
	public HolonObjectType merge(HolonObjectType detachedInstance) {
		return getHolonObjectTypeHome().merge(detachedInstance);
	}

	@Override
	public HolonObjectType findById(int holonObjectTypeId) {
		return getHolonObjectTypeHome().findById(holonObjectTypeId);
	}

	@Override
	public boolean delete(HolonObjectType persistentInstance) {
		return getHolonObjectTypeHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonObjectType> getAllHolonObjectType() {
		return getHolonObjectTypeHome().getAllHolonObjectType();
	}

}
