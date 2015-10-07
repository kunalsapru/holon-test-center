package com.htc.hibernate.utilities;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.hibernate.pojo.PowerSwitch;
import com.htc.hibernate.pojo.Simulation;

public class SimulationHome {
static Logger log = Logger.getLogger(SimulationHome.class);
	
	public Integer persist(Simulation transientInstance) {
		Integer simulation_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			simulation_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return simulation_id;
	}

	public boolean delete(Simulation persistentInstance) {
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
	public ArrayList<Simulation> getAllSimulations() {
		Session session = null;
		Transaction tx = null;
		ArrayList<Simulation> listSimulation = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listSimulation = (ArrayList<Simulation>) session.createQuery("from Simulation s").list();
			tx.commit();
			return listSimulation;
		} catch (RuntimeException re) {
			log.info("get getAllSimulations list failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listSimulation;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<PowerLine> getAllPowerLinesSimulation() {
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerLine> listPowerLine = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("select s.powerLine from Simulation s where s.powerLine!=null");
			listPowerLine = (ArrayList<PowerLine>) query.list();
			tx.commit();
		} catch (RuntimeException re) {
			log.info("getAllPowerLinesSimulation failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listPowerLine;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PowerSwitch> getAllPowerSwitchesSimulation() {
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerSwitch> listPowerSwitch = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("select s.powerSwitch from Simulation s where s.powerSwitch!=null");
			listPowerSwitch = (ArrayList<PowerSwitch>) query.list();
			tx.commit();
		} catch (RuntimeException re) {
			log.info("getAllPowerSwitchesSimulation failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listPowerSwitch;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<PowerSource> getAllPowerSourcesSimulation() {
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerSource> listPowerSource = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("select s.powerSource from Simulation s where s.powerSource!=null");
			listPowerSource = (ArrayList<PowerSource>) query.list();
			tx.commit();
		} catch (RuntimeException re) {
			log.info("getAllPowerSourcesSimulation failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listPowerSource;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HolonObject> getAllHolonObjectsSimulation() {
		Session session = null;
		Transaction tx = null;
		ArrayList<HolonObject> listHolonObject = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("select s.holonObject from Simulation s where s.holonObject!=null");
			listHolonObject = (ArrayList<HolonObject>) query.list();
			tx.commit();
		} catch (RuntimeException re) {
			log.info("getAllHolonObjectsSimulation failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listHolonObject;
	}

}
