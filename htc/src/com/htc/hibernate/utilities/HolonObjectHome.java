package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonObject;

/**
 * Home object for domain model class HolonObject.
 * @see .HolonObject
 */
public class HolonObjectHome {
	static Logger log = Logger.getLogger(HolonObjectHome.class);
	
	public Integer persist(HolonObject transientInstance) {
		Integer holonObject_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			holonObject_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return holonObject_id;
	}
	
	public HolonObject merge(HolonObject detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonObject result = (HolonObject) session.merge(detachedInstance);
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

	public HolonObject findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonObject instance = (HolonObject) session.get(HolonObject.class, id);
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
	
	@SuppressWarnings("unchecked")
	public ArrayList<HolonObject> findByHolon(Holon holon) {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonObject> listHolonObject = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query qr= session.createQuery("from HolonObject h where h.holon=:holon");
			qr.setEntity("holon", holon);
			listHolonObject =  (ArrayList<HolonObject>) qr.list();
			tx.commit();
			return listHolonObject;
		} catch (RuntimeException re) {
			log.info("Exception --> "+re.getMessage());
			re.printStackTrace();
			if(tx!=null) { tx.rollback(); }
		
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listHolonObject;
	}

	public boolean delete(HolonObject persistentInstance) {
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
	public ArrayList<HolonObject> getAllHolonObject() {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonObject> listHolonObject = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listHolonObject = (ArrayList<HolonObject>) session.createQuery("from HolonObject h").list();
			tx.commit();
			return listHolonObject;
		} catch (RuntimeException re) {
			log.info("get holon Object list failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listHolonObject;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HolonObject> findAllHolonCoordinators() {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonObject> listHolonObject = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query qr= session.createQuery("from HolonObject h where h.isCoordinator=1");
			listHolonObject =  (ArrayList<HolonObject>) qr.list();
			tx.commit();
			return listHolonObject;
		} catch (RuntimeException re) {
			log.info("Exception --> "+re.getMessage());
			if(tx!=null) { tx.rollback(); }
			re.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listHolonObject;
	}
	
	public int deleteAllHolonObjects() {
		Session session = null;
		Transaction tx = null;
		int deleteResponse = 0;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("delete HolonObject");
			deleteResponse = query.executeUpdate();
			tx.commit();
			return deleteResponse;
		} catch (RuntimeException re) {
			log.info("deleteAllHolonObjects failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return deleteResponse;
	}
	
}
