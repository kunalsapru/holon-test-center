package com.htc.action;

import org.apache.log4j.Logger;

import com.htc.service.ServiceAware;

public abstract class AbstractAction extends ServiceAware {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(AbstractAction.class);
	
	public String execute() {
		log.debug("Inside AbstractAction");
		return "success";
	}

}
