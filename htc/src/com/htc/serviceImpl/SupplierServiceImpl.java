package com.htc.serviceImpl;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.Supplier;
import com.htc.service.AbstractService;
import com.htc.service.SupplierService;

public class SupplierServiceImpl extends AbstractService implements  SupplierService{

	@Override
	public Integer persist(Supplier transientInstance) {
		return getSupplierDao().persist(transientInstance);
	}

	@Override
	public Supplier merge(Supplier detachedInstance) {
		return getSupplierDao().merge(detachedInstance);
	}

	@Override
	public Supplier findById(int supplierId) {
		return getSupplierDao().findById(supplierId);
	}

	@Override
	public boolean delete(Supplier persistentInstance) {
		return getSupplierDao().delete(persistentInstance);
	}

	@Override
	public ArrayList<Supplier> getAllSupplier() {
		return getSupplierDao().getAllSupplier();
	}

	@Override
	public ArrayList<Supplier> getSupplierListForProducer(HolonObject holonObject) {
		return getSupplierDao().getSupplierListForProducer(holonObject);
	}

	@Override
	public ArrayList<Supplier> getSupplierListForConsumer(HolonObject holonObject) {
		return getSupplierDao().getSupplierListForConsumer(holonObject);
	}
	
}