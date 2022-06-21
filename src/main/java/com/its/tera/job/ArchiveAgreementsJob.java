package com.its.tera.job;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ArchiveAgreementsJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap jobData = context.getJobDetail().getJobDataMap();
		
		Object archiveJob = jobData.get("agreementsArchiveMove");
		
		
		 if (archiveJob == null || !(archiveJob instanceof AgreementsArchiveMove))
	      {
	          throw new AlfrescoRuntimeException(
	                  "TrashcanCleanerJob data must contain valid 'AgreementsArchiveMove' reference");
	      }
		
		final AgreementsArchiveMove agreementsArchiveMove = (AgreementsArchiveMove) archiveJob;
		
		
		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
	    {
			public Object doWork() throws Exception
			{
				agreementsArchiveMove.execute();
	            return null;
			}
	    }, AuthenticationUtil.getSystemUserName());
		
		
		
	}
	
	
	

}
