package com.htc.hibernate.utilities;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonManager;

/**
 * Home object for domain model class HolonManager.
 * @see .HolonManager
 */
public class HolonManagerHome {
	
	public Integer persist(HolonManager transientInstance) {
		Integer holonManager_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			holonManager_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return holonManager_id;
	}
	
	public HolonManager merge(HolonManager detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonManager result = (HolonManager) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			throw re;
		}
	}

	public HolonManager findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonManager instance = (HolonManager) session.get(HolonManager.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			throw re;
		}
	}

	public boolean delete(HolonManager persistentInstance) {
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
	public ArrayList<HolonManager> getAllHolonManager() {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonManager> listHolonManager = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listHolonManager = (ArrayList<HolonManager>) session.createQuery("from HolonManager h").list();
			tx.commit();
			return listHolonManager;
		} catch (RuntimeException re) {
			System.out.println("get holon Manager list failed");
		}
		return listHolonManager;
	}

}
