package com.htc.hibernate.utilities;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonObjectType;

/**
 * Home object for domain model class HolonObjectType.
 * @see .HolonObjectType
 */
public class HolonObjectTypeHome {
	
	public Integer persist(HolonObjectType transientInstance) {
		Integer holonObjectType_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			holonObjectType_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return holonObjectType_id;
	}
	
	public HolonObjectType merge(HolonObjectType detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonObjectType result = (HolonObjectType) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			throw re;
		}
	}

	public HolonObjectType findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonObjectType instance = (HolonObjectType) session.get(HolonObjectType.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			throw re;
		}
	}

	public boolean delete(HolonObjectType persistentInstance) {
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
	public ArrayList<HolonObjectType> getAllHolonObjectType() {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonObjectType> listHolonObjectType = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listHolonObjectType = (ArrayList<HolonObjectType>) session.createQuery("from HolonObjectType h").list();
			tx.commit();
			return listHolonObjectType;
		} catch (RuntimeException re) {
			System.out.println("get holon Object type list failed");
		}
		return listHolonObjectType;
	}

}
