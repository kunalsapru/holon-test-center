package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.HolonElementTypeDao;
import com.htc.hibernate.pojo.HolonElementType;
import com.htc.hibernate.utilities.HolonElementTypeHome;

public class HolonElementTypeDaoImpl implements HolonElementTypeDao {
	
	HolonElementTypeHome holonElementTypeHome = new HolonElementTypeHome();

	public HolonElementTypeHome getHolonElementTypeHome() {
		return holonElementTypeHome;
	}

	public void setHolonElementTypeHome(HolonElementTypeHome holonElementTypeHome) {
		this.holonElementTypeHome = holonElementTypeHome;
	}

	@Override
	public Integer persist(HolonElementType transientInstance) {
		return getHolonElementTypeHome().persist(transientInstance);
	}

	@Override
	public HolonElementType merge(HolonElementType detachedInstance) {
		return getHolonElementTypeHome().merge(detachedInstance);
	}

	@Override
	public HolonElementType findById(int holonElementTypeId) {
		return getHolonElementTypeHome().findById(holonElementTypeId);
	}

	@Override
	public boolean delete(HolonElementType persistentInstance) {
		return getHolonElementTypeHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<HolonElementType> getAllHolonElementType() {
		return getHolonElementTypeHome().getAllHolonElementType();
	}

}
