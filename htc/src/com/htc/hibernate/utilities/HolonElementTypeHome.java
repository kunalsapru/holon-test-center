package com.htc.hibernate.utilities;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.HolonElementType;

/**
 * Home object for domain model class HolonElementType.
 * @see .HolonElementType
 */
public class HolonElementTypeHome {

	public Integer persist(HolonElementType transientInstance) {
		Integer holonElementType_id=null;
		Session session = null;
		Transaction tx = null;
		try
		{
			//this line get session for connection.
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			//this line has transaction definition.
			tx = session.beginTransaction();
			holonElementType_id = (Integer)session.save(transientInstance);
			tx.commit();
		} catch (Exception exp){
			exp.printStackTrace();
		}
		return holonElementType_id;
	
/*		log.debug("persisting HolonElementType instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.info("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			re.printStackTrace();
			throw re;
		}*/
	}

/*	public void attachDirty(HolonElementType instance) {
		log.debug("attaching dirty HolonElementType instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(HolonElementType instance) {
		log.debug("attaching clean HolonElementType instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(HolonElementType persistentInstance) {
		log.debug("deleting HolonElementType instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public HolonElementType merge(HolonElementType detachedInstance) {
		log.debug("merging HolonElementType instance");
		try {
			HolonElementType result = (HolonElementType) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public HolonElementType findById(int id) {
		log.debug("getting HolonElementType instance with id: " + id);
		try {
			HolonElementType instance = (HolonElementType) sessionFactory
					.getCurrentSession().get("HolonElementType", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(HolonElementType instance) {
		log.debug("finding HolonElementType instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("HolonElementType")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}*/
}
