package com.htc.hibernate.utilities;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.LatLng;

/**
 * Home object for domain model class LatLng.
 * @see .LatLng
 */
public class LatLngHome {
	
	public Integer persist(LatLng transientInstance) {
		Integer latLng_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			latLng_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return latLng_id;
	}
	
	public LatLng merge(LatLng detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			LatLng result = (LatLng) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			throw re;
		}
	}

	public LatLng findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			LatLng instance = (LatLng) session.get(LatLng.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			throw re;
		}
	}

	public boolean delete(LatLng persistentInstance) {
		Session session = null;
		Transaction tx = null;
		boolean deleteStatus = false;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			session.delete(persistentInstance);
			tx.commit();
			deleteStatus = true;
			return deleteStatus;
		} catch (RuntimeException re) {
			System.out.println("Delete Failed...");
		}
		return deleteStatus;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<LatLng> getAllLatLng() {
		Session session = null;
		Transaction tx = null;
		ArrayList<LatLng> listLatLng = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listLatLng = (ArrayList<LatLng>) session.createQuery("from LatLng l").list();
			tx.commit();
			return listLatLng;
		} catch (RuntimeException re) {
			System.out.println("get LatLng list failed");
		}
		return listLatLng;
	}

}
