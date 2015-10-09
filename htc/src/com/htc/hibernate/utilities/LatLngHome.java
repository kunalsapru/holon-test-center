package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.LatLng;

/**
 * Home object for domain model class LatLng.
 * @see .LatLng
 */
public class LatLngHome {
	static Logger log = Logger.getLogger(LatLngHome.class);
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
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
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
			log.info("Merge Failed...");
			if(tx!=null) { tx.rollback(); }
			throw re;
		} finally {
			HibernateSessionFactory.closeSession();
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
			log.info("Exception --> "+re.getMessage());
			if(tx!=null) { tx.rollback(); }
			throw re;
		} finally {
			HibernateSessionFactory.closeSession();
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
			log.info("Delete Failed...");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
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
			log.info("get LatLng list failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listLatLng;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<LatLng> findByLocation(Double lat, Double lng) {
		Session session = null;
		Transaction tx = null;
		ArrayList<LatLng> listLatLng = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query qr= session.createQuery("from LatLng l where l.latitude=:latitude and l.longitude=:longitude");
			qr.setParameter("latitude", lat);
			qr.setParameter("longitude", lng);
			listLatLng =  (ArrayList<LatLng>) qr.list();
			tx.commit();
			return listLatLng;
		} catch (RuntimeException re) {
			log.info("Exception --> "+re.getMessage());
			re.printStackTrace();
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listLatLng;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<LatLng> findAllLatLngInsideTheCircle(Double lat,Double lng,Double radius) {
		Session session = null;
		Transaction tx = null;
		ArrayList<LatLng> listLatLng = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			String queryStr = "from LatLng l group by l.id having ( 6371377 * acos( cos( radians("+lat+") ) * cos( radians( l.latitude ) ) * cos( radians( l.longitude ) - "
					+ "radians("+lng+") ) + sin( radians("+lat+") ) * sin( radians( l.latitude ) ) ) ) <"+radius;
			Query qr= session.createQuery(queryStr);
			listLatLng =  (ArrayList<LatLng>) qr.list();
			tx.commit();
			return listLatLng;
		} catch (RuntimeException re) {
			log.info("Exception --> "+re.getMessage());
			re.printStackTrace();
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		
		return listLatLng;
	}
	
	public int deleteAllLatLngs() {
		Session session = null;
		Transaction tx = null;
		int deleteResponse = 0;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("delete LatLng");
			deleteResponse = query.executeUpdate();
			tx.commit();
			return deleteResponse;
		} catch (RuntimeException re) {
			log.info("deleteAllLatLngs failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return deleteResponse;
	}
	
}
