package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.Supplier;

/**
 * Home object for domain model class Supplier.
 * @see .Supplier
 */
public class SupplierHome {
	
	static Logger log = Logger.getLogger(SupplierHome.class);
	
	public Integer persist(Supplier transientInstance) {
		Integer supplier_id=null;
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();//Getting Hibernate session from factory
			tx = session.beginTransaction();// active transaction session
			supplier_id = (Integer)session.save(transientInstance);
			tx.commit();// Committing transaction changes
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return supplier_id;
	}
	
	public Supplier merge(Supplier detachedInstance) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Supplier result = (Supplier) session.merge(detachedInstance);
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			System.out.println("Merge Failed...");
			throw re;
		}
	}

	public Supplier findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Supplier instance = (Supplier) session.get(Supplier.class, id);
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			System.out.println("Exception --> "+re.getMessage());
			throw re;
		}
	}

	public boolean delete(Supplier persistentInstance) {
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
	public ArrayList<Supplier> getAllSupplier() {
		Session session = null;
		Transaction tx = null;
		ArrayList<Supplier> listSupplier = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listSupplier = (ArrayList<Supplier>) session.createQuery("from Supplier s").list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			System.out.println("get Supplier list failed");
		}
		return listSupplier;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Supplier> getSupplierListForProducer(HolonObject holonObject) {
		Session session = null;
		Transaction tx = null;
		ArrayList<Supplier> listSupplier = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from Supplier s where s.holonObjectProducer=:holonObject");
			query.setEntity("holonObject", holonObject);
			listSupplier = (ArrayList<Supplier>) query.list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			System.out.println("getSupplierListForProducer failed");
		}
		return listSupplier;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Supplier> getSupplierListForProducerOrderByConsumerPriority(HolonObject holonObject) {
		Session session = null;
		Transaction tx = null;
		ArrayList<Supplier> listSupplier = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from Supplier s where s.holonObjectProducer=:holonObject order by s.holonObjectConsumer.holonObjectType.priority ASC");
			query.setEntity("holonObject", holonObject);
			listSupplier = (ArrayList<Supplier>) query.list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			System.out.println("getSupplierListForProducer failed");
		}
		return listSupplier;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Supplier> getSupplierListForConsumer(HolonObject holonObject) {
		Session session = null;
		Transaction tx = null;
		ArrayList<Supplier> listSupplier = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from Supplier s where s.holonObjectConsumer=:holonObject");
			query.setEntity("holonObject", holonObject);
			listSupplier = (ArrayList<Supplier>) query.list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			System.out.println("getSupplierListForConsumer failed");
		}
		return listSupplier;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Supplier> getListOfSimilarRequests(Integer requestId) {
		Session session = null;
		Transaction tx = null;
		ArrayList<Supplier> listSupplier = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from Supplier s where s.requestId=:requestId");
			query.setInteger("requestId", requestId);
			listSupplier = (ArrayList<Supplier>) query.list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			System.out.println("getSupplierListForConsumer failed");
		}
		return listSupplier;
	}

}
