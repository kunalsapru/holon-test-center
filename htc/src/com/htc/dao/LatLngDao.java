package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.LatLng;

public interface LatLngDao {

	public Integer persist(LatLng transientInstance);
	public LatLng merge(LatLng detachedInstance);
	public LatLng findById(int holonId);
	public boolean delete(LatLng persistentInstance);
	public ArrayList<LatLng> getAllLatLng();
	public ArrayList<LatLng> findByLocation(Double lat, Double lng);
	public ArrayList<LatLng> findAllLatLngInsideTheCircle(Double lat, Double lng,Double radius);
	public int deleteAllLatLngs();

}
