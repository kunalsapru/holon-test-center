package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.LatLngDao;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.utilities.LatLngHome;

public class LatLngDaoImpl implements LatLngDao {

	LatLngHome latLngHome = new LatLngHome();

	public LatLngHome getLatLngHome() {
		return latLngHome;
	}

	public void setLatLngHome(LatLngHome latLngHome) {
		this.latLngHome = latLngHome;
	}

	@Override
	public Integer persist(LatLng transientInstance) {
		return getLatLngHome().persist(transientInstance);
	}

	@Override
	public LatLng merge(LatLng detachedInstance) {
		return getLatLngHome().merge(detachedInstance);
	}

	@Override
	public LatLng findById(int latLngId) {
		return getLatLngHome().findById(latLngId);
	}

	@Override
	public boolean delete(LatLng persistentInstance) {
		return getLatLngHome().delete(persistentInstance);
	}

	@Override
	public ArrayList<LatLng> getAllLatLng() {
		return getLatLngHome().getAllLatLng();
	}

	@Override
	public ArrayList<LatLng> findByLocation(Double lat, Double lng) {
		return getLatLngHome().findByLocation(lat,lng);
	}

	@Override
	public ArrayList<LatLng> findAllLatLngInsideTheCircle(Double lat,
			Double lng, Double radius) {
		// TODO Auto-generated method stub
		return getLatLngHome().findAllLatLngInsideTheCircle(lat,lng,radius);
	}


}
