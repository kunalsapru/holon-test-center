package com.htc.hibernate.utilities;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.Holon;

/**
 * Home object for domain model class Holon.
 * @see .Holon
 */
public class HolonHome {
	
	public Integer persist(Holon transientInstance) {
		Integer holon_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			holon_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return holon_id;
	}
	
	public Holon merge(Holon detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Holon result = (Holon) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			throw re;
		}
	}

	public Holon findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Holon instance = (Holon) session.get(Holon.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			throw re;
		}
	}

	public boolean delete(Holon persistentInstance) {
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
	public ArrayList<Holon> getAllHolon() {
		Session session = null;
		Transaction tx = null;
		ArrayList<Holon> listHolon = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listHolon = (ArrayList<Holon>) session.createQuery("from Holon h").list();
			tx.commit();
			return listHolon;
		} catch (RuntimeException re) {
			System.out.println("get holon list failed");
		}
		return listHolon;
	}

}
