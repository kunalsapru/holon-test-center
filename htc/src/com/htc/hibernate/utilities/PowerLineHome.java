package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.LatLng;
import com.htc.hibernate.pojo.PowerLine;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.utilities.ConstantValues;

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
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
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
			tx.rollback();
			throw re;
		} finally {
			HibernateSessionFactory.closeSession();
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
			tx.rollback();
			throw re;
		} finally {
			HibernateSessionFactory.closeSession();
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
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
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
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listPowerLine;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<PowerLine> getConnectedPowerLines(PowerLine powerLine) {
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerLine> connectedPowerLines = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			LatLng latLngBySource = powerLine.getLatLngBySource();
			LatLng latLngByDestination = powerLine.getLatLngByDestination();
			Integer powerLineId = powerLine.getId();
			Query query = session.createQuery("from PowerLine p where (p.latLngBySource=:latLngBySource or p.latLngBySource=:latLngByDestination"
					+ " or p.latLngByDestination=:latLngByDestination or p.latLngByDestination=:latLngBySource) and p.id !=:id");
			query.setEntity("latLngBySource", latLngBySource);
			query.setEntity("latLngByDestination", latLngByDestination);
			query.setInteger("id", powerLineId);
			connectedPowerLines =  (ArrayList<PowerLine>) query.list();
			tx.commit();
			return connectedPowerLines;
		} catch (RuntimeException re) {
			System.out.println("get Connected Power Line list failed");
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return connectedPowerLines;
	}
	
	public PowerLine getPowerLineByHolonObject(HolonObject holonObject) {
		Session session = null;
		Transaction tx = null;
		PowerLine powerLine = null;
		String powerLineType = ConstantValues.SUBLINE;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from PowerLine p where p.type=:powerLineType and p.holonObject =:holonObject");
			query.setString("powerLineType", powerLineType);
			query.setEntity("holonObject", holonObject);
			powerLine =  (PowerLine)query.uniqueResult();
			tx.commit();
		} catch (RuntimeException re) {
			System.out.println("getPowerLineByHolonObject failed");
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return powerLine;
	}
	
	public PowerLine getPowerLineByPowerSource(PowerSource powerSource) {
		Session session = null;
		Transaction tx = null;
		PowerLine powerLine = null;
		String powerLineType = ConstantValues.POWERSUBLINE;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from PowerLine p where p.type=:powerLineType and p.powerSource =:powerSource");
			query.setString("powerLineType", powerLineType);
			query.setEntity("powerSource", powerSource);
			powerLine =  (PowerLine)query.uniqueResult();
			tx.commit();
		} catch (RuntimeException re) {
			System.out.println("getPowerLineByPowerSource failed");
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return powerLine;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PowerLine> getPowerLineFromLatLng(LatLng latLng){
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerLine> listPowerLine = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from PowerLine p where p.latLngBySource=:latLng or p.latLngByDestination =:latLng");
			query.setEntity("latLng", latLng);
			listPowerLine = (ArrayList<PowerLine>) query.list();
			tx.commit();
		} catch (RuntimeException re) {
			System.out.println("getPowerLineFromLatLngId failed");
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listPowerLine;
	}
}
