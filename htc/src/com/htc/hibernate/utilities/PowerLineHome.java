package com.htc.hibernate.utilities;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.PowerLine;

/**
 * Home object for domain model class PowerLine.
 * @see .PowerLine
 */
public class PowerLineHome {
	
	public Integer persist(PowerLine transientInstance) {
		Integer powerLine_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			powerLine_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return powerLine_id;
	}
	
	public PowerLine merge(PowerLine detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			PowerLine result = (PowerLine) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			throw re;
		}
	}

	public PowerLine findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			PowerLine instance = (PowerLine) session.get(PowerLine.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			throw re;
		}
	}

	public boolean delete(PowerLine persistentInstance) {
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
	public ArrayList<PowerLine> getAllPowerLine() {
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerLine> listPowerLine = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listPowerLine = (ArrayList<PowerLine>) session.createQuery("from PowerLine p").list();
			tx.commit();
			return listPowerLine;
		} catch (RuntimeException re) {
			System.out.println("get Power Line list failed");
		}
		return listPowerLine;
	}

}
