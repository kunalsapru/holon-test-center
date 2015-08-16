package com.htc.utilities;

import java.util.Random;

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
	
	
	public static int getPercent(int x, int y)
	{
		float xF=new Float(x);
		float yF=new Float(y);
		float perc= ((xF/yF)*100);
		log.info("x is "+x);
		log.info("y is "+y);
		log.info("percent is "+perc);
		return (int) perc;
		
	}
	
	public static String getLineColor(int percentCap)
	{
		String color="brown";
		
		if(0<percentCap && percentCap<=20)
		{
			color = "red";
		}else if(20<percentCap && percentCap<=50)
		{
			color = "yellow";
		}else if(percentCap>50 && percentCap<100)
		{
			color ="green";
		}else if(percentCap==100)
		{
			color ="black";
		}
		
		return color;
	}
	
/*	public static float randomCapGenerator(float maxCap)
	{
		float finalX=0.0f;
		
		float minX = 00.0f;
		float maxX = maxCap;

		Random rand = new Random();

		 finalX = rand.nextFloat() * (maxX - minX) + minX;
		
		return finalX;
	}
*/	

}
