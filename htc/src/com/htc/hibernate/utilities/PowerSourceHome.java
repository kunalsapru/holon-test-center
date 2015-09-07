package com.htc.hibernate.utilities;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerSource;

/**
 * Home object for domain model class PowerSource.
 * @see .PowerSource
 */
public class PowerSourceHome {
	static Logger log = Logger.getLogger(PowerSourceHome.class);
	
	public Integer persist(PowerSource transientInstance) {
		Integer powerSource_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			powerSource_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return powerSource_id;
	}
	
	public PowerSource merge(PowerSource detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			PowerSource result = (PowerSource) session.merge(detachedInstance);
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

	public PowerSource findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			PowerSource instance = (PowerSource) session.get(PowerSource.class, id);
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

	public boolean delete(PowerSource persistentInstance) {
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
	public ArrayList<PowerSource> getAllPowerSource() {
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerSource> listPowerSource = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listPowerSource = (ArrayList<PowerSource>) session.createQuery("from PowerSource p").list();
			tx.commit();
			return listPowerSource;
		} catch (RuntimeException re) {
			System.out.println("get Power Source list failed");
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listPowerSource;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PowerSource> findByHolonCoordinator(HolonObject holonCoordinator) {
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerSource> listPSrcObject = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query qr= session.createQuery("from PowerSource p where p.holonCoordinator=:holonCoordinator");
			qr.setEntity("holonCoordinator", holonCoordinator);
			listPSrcObject =  (ArrayList<PowerSource>) qr.list();
			tx.commit();
			return listPSrcObject;
		} catch (RuntimeException re) {
			log.info("Exception --> "+re.getMessage());
			re.printStackTrace();
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listPSrcObject;
	}

}
