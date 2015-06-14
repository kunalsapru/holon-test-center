package com.htc.daoImpl;

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

}
