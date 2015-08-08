package com.htc.service;

import java.util.ArrayList;

import com.htc.hibernate.pojo.LatLng;

public interface LatLngService {

	public Integer persist(LatLng transientInstance);
	public LatLng merge(LatLng detachedInstance);
	public LatLng findById(int latLngId);
	public boolean delete(LatLng persistentInstance);
	public ArrayList<LatLng> getAllLatLng();	
	public ArrayList<LatLng> findByLocation(Double lat, Double lng);

}
