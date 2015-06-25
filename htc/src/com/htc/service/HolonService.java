package com.htc.service;

import java.util.ArrayList;
import com.htc.hibernate.pojo.Holon;

public interface HolonService {

	public Integer persist(Holon transientInstance);
	public Holon merge(Holon detachedInstance);
	public Holon findById(int holonId);
	public boolean delete(Holon persistentInstance);
	public ArrayList<Holon> getAllHolon();

}
