package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.action.PowerSwitchAction;
import com.htc.hibernate.config.HibernateSessionFactory;
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
			System.out.println("Merge Failed...");
			throw re;
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
			System.out.println("Exception --> "+re.getMessage());
			throw re;
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
			System.out.println("Delete Failed...");
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
			System.out.println("get Power Switch list failed");
		}
		return listPowerSwitch;
	}

	public int changeSwitchStatus(int powerSwitchId, int statusVal) {
		log.info("changeSwitchStatus start ");
		Session session = null;
		Transaction tx = null;
		int result=0;
		boolean status=false;
		if(statusVal==1)
			status=true;
			try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			log.info("changeSwitchStatus createQuery ");
			tx = session.beginTransaction();
			String hql = "UPDATE PowerSwitch set status = :status "  + 
		             "WHERE id = :id";
			Query query = session.createQuery(hql);
			log.info("changeSwitchStatus createQuery ");
			query.setParameter("status", status);
			query.setParameter("id", powerSwitchId);
			log.info("changeSwitchStatus setParameter ");
			result = query.executeUpdate();
			log.info("changeSwitchStatus executeUpdate ");
			tx.commit();
			log.info("changeSwitchStatus commit ");
		} catch (RuntimeException re) {
			re.printStackTrace();
			System.out.println("switch status change Failed...");
		}
			return result;
	}

}
