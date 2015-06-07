package com.htc.utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.htc.action.AbstractAction;
import com.opensymphony.xwork2.ActionContext;

public class CommonUtilities extends AbstractAction{

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(CommonUtilities.class);
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected HttpSession httpSession;

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpSession getHttpSession() {
		return getRequest().getSession();
	}
	
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

}
