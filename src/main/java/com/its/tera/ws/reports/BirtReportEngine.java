package com.its.tera.ws.reports;

import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

public class BirtReportEngine 
{
	public static final String NAME = "birtReportingEngine";
	
	private static final Log logger = LogFactory.getLog(BirtReportEngine.class); 
	
	private IReportEngine engine;
	private String logDirectory;
	private String logLevel;
	
	public BirtReportEngine() {}
	
	public void init()
	{
		try 
		{
			logger.debug("Starting birt report platform ...");
			
			EngineConfig config = new EngineConfig();
			config.setLogConfig(logDirectory, Level.FINE);
			logger.debug("LogDirectory = " + config.getLogDirectory() + " level = " + config.getLogLevel());

			Platform.startup(config);
			IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			if(factory == null){
				logger.debug("factory == null");
			}
			
			engine = factory.createReportEngine(config);
			
			Level level = Level.OFF;
			
			if("WARNING".equalsIgnoreCase(logLevel))
			{
				level = Level.WARNING;
			}
			else if("SEVERE".equalsIgnoreCase(logLevel))
			{
				level = Level.SEVERE;
			}
			else if("INFO".equalsIgnoreCase(logLevel))
			{
				level = Level.INFO;
			}
			else if("FINEST".equalsIgnoreCase(logLevel))
			{
				level = Level.FINEST;
			}
			else if("FINER".equalsIgnoreCase(logLevel))
			{
				level = Level.FINER;
			}
			else if("FINE".equalsIgnoreCase(logLevel))
			{
				level = Level.FINE;
			}
			else if("CONFIG".equalsIgnoreCase(logLevel))
			{
				level = Level.CONFIG;
			}
			
			engine.changeLogLevel(level);
			
			logger.debug("Birt report platform startup done ?...");

		} 
		catch (BirtException e) 
		{
			e.printStackTrace();
		}
	}

	public IReportEngine getEngine() 
	{
		return engine;
	}

	public void setLogDirectory(String logDirectory) 
	{
		this.logDirectory = logDirectory;
	}

	public void setLogLevel(String logLevel) 
	{
		this.logLevel = logLevel;
	}
	
}
