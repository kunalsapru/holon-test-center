package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonCoordinator;
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
			throw re;
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
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<HolonObject> findByHCoordinator(HolonCoordinator holonCoordinator) {
		log.info("Abhinav holonCoordinator value --> "+holonCoordinator);
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonObject> listHolonObject = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query qr= session.createQuery("from HolonObject h where h.holonCoordinator=:holonCoordinator");
			qr.setEntity("holonCoordinator", holonCoordinator);
			listHolonObject =  (ArrayList<HolonObject>) qr.list();
			tx.commit();
			return listHolonObject;
		} catch (RuntimeException re) {
			log.info("Exception --> "+re.getMessage());
			re.printStackTrace();
		
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
		}
		return listHolonObject;
	}

	
}
