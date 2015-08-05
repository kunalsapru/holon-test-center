package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.PowerLineNode;

/**
 * Home object for domain model class PowerLineNode.
 * @see .PowerLineNode
 */
public class PowerLineNodeHome {
	
	public Integer persist(PowerLineNode transientInstance) {
		Integer powerLineNode_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			powerLineNode_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return powerLineNode_id;
	}
	
	public PowerLineNode merge(PowerLineNode detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			PowerLineNode result = (PowerLineNode) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			throw re;
		}
	}

	public PowerLineNode findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			PowerLineNode instance = (PowerLineNode) session.get(PowerLineNode.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			throw re;
		}
	}

	public boolean delete(PowerLineNode persistentInstance) {
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
	public ArrayList<PowerLineNode> getAllPowerLineNode() {
		Session session = null;
		Transaction tx = null;
		ArrayList<PowerLineNode> listPowerLineNode = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listPowerLineNode = (ArrayList<PowerLineNode>) session.createQuery("from PowerLineNode p").list();
			tx.commit();
			return listPowerLineNode;
		} catch (RuntimeException re) {
			System.out.println("get PowerLine Node list failed");
		}
		return listPowerLineNode;
	}

}
