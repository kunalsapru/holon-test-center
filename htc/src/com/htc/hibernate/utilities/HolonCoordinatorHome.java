package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonCoordinator;

/**
 * Home object for domain model class HolonCoordinator.
 * @see .HolonCoordinator
 */
public class HolonCoordinatorHome {
	
	public Integer persist(HolonCoordinator transientInstance) {
		Integer holonCoordinator_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			holonCoordinator_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return holonCoordinator_id;
	}
	
	public HolonCoordinator merge(HolonCoordinator detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonCoordinator result = (HolonCoordinator) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			throw re;
		}
	}

	public  HolonCoordinator findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonCoordinator instance = (HolonCoordinator) session.get(HolonCoordinator.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			throw re;
		}
	}

	public boolean delete(HolonCoordinator persistentInstance) {
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
	public ArrayList<HolonCoordinator> getAllHolonCoordinator() {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonCoordinator> listHolonCoordinator = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listHolonCoordinator = (ArrayList<HolonCoordinator>) session.createQuery("from HolonCoordinator h").list();
			tx.commit();
			return listHolonCoordinator;
		} catch (RuntimeException re) {
			System.out.println("get holon coordinator list failed");
		}
		return listHolonCoordinator;
	}
}
