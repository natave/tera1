package com.its.tera.job.send;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendToArchiveAgreementsJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap jobData = context.getJobDetail().getJobDataMap();
		
		Object archiveJob = jobData.get("agreementsArchiveSend");
		
		
		 if (archiveJob == null || !(archiveJob instanceof AgreementsArchiveSend))
	      {
	          throw new AlfrescoRuntimeException(
	                  "TrashcanCleanerJob data must contain valid 'AgreementsArchiveMove' reference");
	      }
		
		final AgreementsArchiveSend agreementsArchiveSend = (AgreementsArchiveSend) archiveJob;
		
		
		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
	    {
			public Object doWork() throws Exception
			{
				agreementsArchiveSend.execute();
	            return null;
			}
	    }, AuthenticationUtil.getSystemUserName());
		
		
		
	}
	
	
	

}
