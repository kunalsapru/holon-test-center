package com.htc.hibernate.utilities;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonElementType;

/**
 * Home object for domain model class HolonElementType.
 * @see .HolonElementType
 */
public class HolonElementTypeHome {
	
	public Integer persist(HolonElementType transientInstance) {
		Integer holonElementType_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			holonElementType_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return holonElementType_id;
	}
	
	public HolonElementType merge(HolonElementType detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonElementType result = (HolonElementType) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			throw re;
		}
	}

	public HolonElementType findById(int id) {
		Session session = null;
		Transaction tx = null;
		System.out.println("Holon Element Type ID = "+id);
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonElementType instance = (HolonElementType) session.get(HolonElementType.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			throw re;
		}
	}

	public boolean delete(HolonElementType persistentInstance) {
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
	public ArrayList<HolonElementType> getAllHolonElementType() {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonElementType> listHolonElementType = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listHolonElementType = (ArrayList<HolonElementType>) session.createQuery("from HolonElementType h").list();
			tx.commit();
			return listHolonElementType;
		} catch (RuntimeException re) {
			System.out.println("get holon element type list failed");
		}
		return listHolonElementType;
	}

}
