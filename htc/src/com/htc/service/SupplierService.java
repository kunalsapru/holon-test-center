package com.htc.service;

import java.util.ArrayList;
import com.htc.hibernate.pojo.Holon;
import com.htc.hibernate.pojo.HolonObject;
import com.htc.hibernate.pojo.PowerSource;
import com.htc.hibernate.pojo.Supplier;

public interface SupplierService {
	
	public Integer persist(Supplier transientInstance);
	public Supplier merge(Supplier detachedInstance);
	public Supplier findById(int supplierId);
	public boolean delete(Supplier persistentInstance);
	public ArrayList<Supplier> getAllSupplier();
	public ArrayList<Supplier> getSupplierListForProducer(HolonObject holonObject);
	public ArrayList<Supplier> getSupplierListForConsumer(HolonObject holonObject);
	public ArrayList<Supplier> getListOfSimilarRequests(Integer requestId);
	public ArrayList<Supplier> getSupplierListForProducerOrderByConsumerPriority(HolonObject holonObject);
	public ArrayList<Supplier> getSupplierListForProducerPowerSource(PowerSource powerSource);
	public ArrayList<Supplier> getSupplierListHolonCoordinator(Holon holon);
	public ArrayList<Supplier> getSupplierListForConsumerOrProducer(HolonObject holonObject);
	public int deleteAllSuppliers();
	
}