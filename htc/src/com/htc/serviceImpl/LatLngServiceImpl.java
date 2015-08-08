package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.LatLng;
import com.htc.service.AbstractService;
import com.htc.service.LatLngService;

public class LatLngServiceImpl extends AbstractService implements LatLngService {

	@Override
	public Integer persist(LatLng transientInstance) {
		return getLatLngDao().persist(transientInstance);
	}

	@Override
	public LatLng merge(LatLng detachedInstance) {
		return getLatLngDao().merge(detachedInstance);
	}

	@Override
	public LatLng findById(int latLngId) {
		return getLatLngDao().findById(latLngId);
	}

	@Override
	public boolean delete(LatLng persistentInstance) {
		return getLatLngDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<LatLng> getAllLatLng() {
		return getLatLngDao().getAllLatLng();
	}

	@Override
	public ArrayList<LatLng> findByLocation(Double lat, Double lng) {
		return getLatLngDao().findByLocation(lat,lng);
	}

}
