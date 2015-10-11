package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.LatLng;
import com.htc.service.DaoAware;
import com.htc.service.LatLngService;

public class LatLngServiceImpl extends DaoAware implements LatLngService {

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

	@Override
	public ArrayList<LatLng> findAllLatLngInsideTheCircle(Double lat, Double lng, Double radius) {
		return getLatLngDao().findAllLatLngInsideTheCircle(lat,lng,radius);
	}

	@Override
	public int deleteAllLatLngs() {
		return getLatLngDao().deleteAllLatLngs();
	}

}
