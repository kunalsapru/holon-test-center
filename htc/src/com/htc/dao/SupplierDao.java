package com.htc.dao;

import java.util.ArrayList;

import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.Supplier;

public interface SupplierDao {

	public Integer persist(Supplier transientInstance);
	public Supplier merge(Supplier detachedInstance);
	public Supplier findById(int supplierId);
	public boolean delete(Supplier persistentInstance);
	public ArrayList<Supplier> getAllSupplier();
	public ArrayList<Supplier> getSupplierListForProducer(HolonObject holonObject);
	public ArrayList<Supplier> getSupplierListForConsumer(HolonObject holonObject);
}