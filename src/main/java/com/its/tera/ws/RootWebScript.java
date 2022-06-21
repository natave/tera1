package com.its.tera.ws;

import org.alfresco.service.ServiceRegistry;
import org.springframework.extensions.webscripts.DeclarativeWebScript;

import com.google.gson.Gson;

public class RootWebScript extends DeclarativeWebScript {

	protected ServiceRegistry serviceRegistry;
	
	private Gson gson;

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public Gson getGson() {
		if(gson == null) {
			gson = new Gson();
		}
		return gson;
	}

	
	
	
	
	
	

}
