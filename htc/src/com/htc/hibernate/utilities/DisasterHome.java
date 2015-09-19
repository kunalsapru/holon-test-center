package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.Disaster;

public class DisasterHome {
static Logger log = Logger.getLogger(HolonObjectHome.class);
	
	public Integer persist(Disaster transientInstance) {
		Integer disaster_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			disaster_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return disaster_id;
	}
	public Disaster merge(Disaster detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Disaster result = (Disaster) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			tx.rollback();
			throw re;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public Disaster findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Disaster instance = (Disaster) session.get(Disaster.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			tx.rollback();
			throw re;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public boolean delete(Disaster persistentInstance) {
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
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return deleteStatus;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Disaster> getAllDisasterCircles() {
		Session session = null;
		Transaction tx = null;
		ArrayList<Disaster> listDisaster = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listDisaster = (ArrayList<Disaster>) session.createQuery("from Disaster d").list();
			tx.commit();
			return listDisaster;
		} catch (RuntimeException re) {
			System.out.println("get DisasterCircles list failed");
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listDisaster;
	}


}
