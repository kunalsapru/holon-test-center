package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonElement;
import com.htc.hibernate.pojo.HolonObject;

/**
 * Home object for domain model class HolonElement.
 * @see .HolonElement
 */
public class HolonElementHome {
	static Logger log = Logger.getLogger(HolonElementHome.class);
	public Integer persist(HolonElement transientInstance) {
		Integer holonElement_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			holonElement_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return holonElement_id;
	}
	
	public HolonElement merge(HolonElement detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonElement result = (HolonElement) session.merge(detachedInstance);
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

	public HolonElement findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			HolonElement instance = (HolonElement) session.get(HolonElement.class, id);
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

	public boolean delete(HolonElement persistentInstance) {
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
	public ArrayList<HolonElement> getAllHolonElement() {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonElement> listHolonElement = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listHolonElement = (ArrayList<HolonElement>) session.createQuery("from HolonElement h").list();
			tx.commit();
			return listHolonElement;
		} catch (RuntimeException re) {
			log.info("get holon element list failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listHolonElement;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HolonElement> getHolonElements(HolonObject holonObject) {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonElement> listHolonElement = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from HolonElement h where h.holonObject=:holonObject");
			query.setEntity("holonObject", holonObject);
			listHolonElement = (ArrayList<HolonElement>) query.list();
			tx.commit();
			return listHolonElement;
		} catch (RuntimeException re) {
			log.info("get holon element list failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listHolonElement;
	}
}
