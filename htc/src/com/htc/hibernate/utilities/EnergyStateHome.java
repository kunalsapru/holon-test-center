package com.htc.hibernate.utilities;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.htc.hibernate.config.HibernateSessionFactory;
import com.htc.hibernate.pojo.EnergyState;

public class EnergyStateHome {
	

	public EnergyState findById(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			EnergyState instance = (EnergyState) session.get(EnergyState.class, id);
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

	
	@SuppressWarnings("unchecked")
	public ArrayList<EnergyState> getAllEnergyState() {
		Session session = null;
		Transaction tx = null;
		ArrayList<EnergyState> listEnergyState = null;
		try {
			session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			listEnergyState = (ArrayList<EnergyState>) session.createQuery("from EnergyState h").list();
			tx.commit();
			return listEnergyState;
		} catch (RuntimeException re) {
			System.out.println("get holon element State list failed");
			tx.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return listEnergyState;
	}


}
