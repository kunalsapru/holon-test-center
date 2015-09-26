package com.htc.hibernate.utilities;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSwitch;

/**
 * Home object for domain model class PowerSwitch.
 * @see .PowerSwitch
 */
public class PowerSwitchHome {
	
	static Logger log = Logger.getLogger(PowerSwitchHome.class);
	
	public Integer persist(PowerSwitch transientInstance) {
		Integer powerSwitch_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			powerSwitch_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return powerSwitch_id;
	}
	
	public PowerSwitch merge(PowerSwitch detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			PowerSwitch result = (PowerSwitch) session.merge(detachedInstance);
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

	public PowerSwitch findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			PowerSwitch instance = (PowerSwitch) session.get(PowerSwitch.class, id);
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

	public boolean delete(PowerSwitch persistentInstance) {
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
	public ArrayList<PowerSwitch> getAllPowerSwitch() {
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerSwitch> listPowerSwitch = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listPowerSwitch = (ArrayList<PowerSwitch>) session.createQuery("from PowerSwitch p").list();
			tx.commit();
			return listPowerSwitch;
		} catch (RuntimeException re) {
			log.info("get Power Switch list failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listPowerSwitch;
	}
	
	public PowerSwitch checkSwitchStatusBetweenPowerLines(PowerLine powerLineA, PowerLine powerLineB) {
		Session session = null;
		Transaction tx = null;
		PowerSwitch powerSwitch = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from PowerSwitch ps where (ps.powerLineByPowerLineA=:powerLineA and ps.powerLineByPowerLineB=:powerLineB) or "
					+ "ps.powerLineByPowerLineA=:powerLineB and ps.powerLineByPowerLineB=:powerLineA");
			query.setEntity("powerLineA", powerLineA);
			query.setEntity("powerLineB", powerLineB);
			powerSwitch =  (PowerSwitch) query.uniqueResult();
			tx.commit();
		} catch (RuntimeException re) {
			log.info("checkSwitchStatusBetweenPowerLines failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return powerSwitch;
	}

}
