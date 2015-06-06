package com.htc.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.htc.service.ServiceAware;

public abstract class AbstractAction extends ServiceAware {

	private static final long serialVersionUID = 1L;
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String execute() {
		return "success";
	} 

}
