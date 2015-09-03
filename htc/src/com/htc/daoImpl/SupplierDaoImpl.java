package com.htc.daoImpl;

import java.util.ArrayList;

import com.htc.dao.SupplierDao;
import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.hibernate.pojo.Supplier;
import com.htc.hibernate.utilities.SupplierHome;

public class SupplierDaoImpl implements SupplierDao {
	
	private SupplierHome supplierHome = new SupplierHome();

	@Override
	public Integer persist(Supplier transientInstance) {
		return supplierHome.persist(transientInstance);
	}

	@Override
	public Supplier merge(Supplier detachedInstance) {
		return supplierHome.merge(detachedInstance);
	}

	@Override
	public Supplier findById(int supplierId) {
		return supplierHome.findById(supplierId);
	}

	@Override
	public boolean delete(Supplier persistentInstance) {
		return supplierHome.delete(persistentInstance);
	}

	@Override
	public ArrayList<Supplier> getAllSupplier() {
		return supplierHome.getAllSupplier();
	}

	@Override
	public ArrayList<Supplier> getSupplierListForProducer(HolonObject holonObject) {
		return supplierHome.getSupplierListForProducer(holonObject);
	}

	@Override
	public ArrayList<Supplier> getSupplierListForConsumer(HolonObject holonObject) {
		return supplierHome.getSupplierListForConsumer(holonObject);
	}

	@Override
	public ArrayList<Supplier> getListOfSimilarRequests(Integer requestId) {
		return supplierHome.getListOfSimilarRequests(requestId);
	}

	@Override
	public ArrayList<Supplier> getSupplierListForProducerOrderByConsumerPriority(HolonObject holonObject) {
		return supplierHome.getSupplierListForProducerOrderByConsumerPriority(holonObject);
	}

	@Override
	public ArrayList<Supplier> getSupplierListForProducerPowerSource(PowerSource powerSource) {
		return supplierHome.getSupplierListForProducerPowerSource(powerSource);
	}

	@Override
	public ArrayList<Supplier> getSupplierListHolonCoordinator(Holon holon) {
		return supplierHome.getSupplierListHolonCoordinator(holon);
	}

}
