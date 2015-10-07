package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.PowerLine;
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

}
