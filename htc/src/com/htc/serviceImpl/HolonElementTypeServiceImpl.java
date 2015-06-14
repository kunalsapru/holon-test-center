package com.htc.serviceImpl;

import com.htc.hibernate.pojo.HolonElementType;
import com.htc.service.AbstractService;
import com.htc.service.HolonElementTypeService;

public class HolonElementTypeServiceImpl extends AbstractService implements HolonElementTypeService {

	@Override
	public Integer persist(HolonElementType transientInstance) {
		return getHolonElementTypeDao().persist(transientInstance);
	}

}
