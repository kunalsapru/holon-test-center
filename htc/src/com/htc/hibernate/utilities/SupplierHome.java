package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.hibernate.pojo.Supplier;
import com.htc.utilities.ConstantValues;

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
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
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
			log.info("Merge Failed...");
			if(tx!=null) { tx.rollback(); }
			throw re;
		} finally {
			HibernateSessionFactory.closeSession();
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
			log.info("Exception --> "+re.getMessage());
			if(tx!=null) { tx.rollback(); }
			throw re;
		} finally {
			HibernateSessionFactory.closeSession();
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
			log.info("Delete Failed...");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
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
			listSupplier = (ArrayList<Supplier>) session.createQuery("from Supplier s order by s.id DESC").list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			log.info("get Supplier list failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
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
			Query query = session.createQuery("from Supplier s where s.holonObjectProducer=:holonObject order by s.id DESC");
			query.setEntity("holonObject", holonObject);
			listSupplier = (ArrayList<Supplier>) query.list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			log.info("getSupplierListForProducer failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listSupplier;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Supplier> getSupplierListForProducerPowerSource(PowerSource powerSource) {
		Session session = null;
		Transaction tx = null;
		ArrayList<Supplier> listSupplier = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from Supplier s where s.powerSource=:powerSource order by s.id DESC");
			query.setEntity("powerSource", powerSource);
			listSupplier = (ArrayList<Supplier>) query.list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			log.info("getSupplierListForProducerPowerSource failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
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
			log.info("getSupplierListForProducer failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
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
			Query query = session.createQuery("from Supplier s where s.holonObjectConsumer=:holonObject order by s.id DESC");
			query.setEntity("holonObject", holonObject);
			listSupplier = (ArrayList<Supplier>) query.list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			log.info("getSupplierListForConsumer failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
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
			log.info("getSupplierListForConsumer failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listSupplier;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Supplier> getSupplierListHolonCoordinator(Holon holon) {
		Session session = null;
		Transaction tx = null;
		ArrayList<Supplier> listSupplier = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from Supplier s where s.holonObjectConsumer.holon=:holon and s.communicationMode=:communicationMode order by s.id DESC");
			query.setEntity("holon", holon);
			query.setString("communicationMode", ConstantValues.COMMUNICATION_MODE_COORDINATOR);
			listSupplier = (ArrayList<Supplier>) query.list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			log.info("getSupplierListForConsumer failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listSupplier;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Supplier> getSupplierListForConsumerOrProducer(HolonObject holonObject) {
		Session session = null;
		Transaction tx = null;
		ArrayList<Supplier> listSupplier = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from Supplier s where s.holonObjectConsumer=:holonObject or s.holonObjectProducer=:holonObject order by s.id DESC");
			query.setEntity("holonObject", holonObject);
			listSupplier = (ArrayList<Supplier>) query.list();
			tx.commit();
			return listSupplier;
		} catch (RuntimeException re) {
			log.info("getSupplierListForConsumer failed");
			if(tx!=null) { tx.rollback(); }
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listSupplier;
	}

}
