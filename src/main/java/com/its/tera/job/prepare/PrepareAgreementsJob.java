package com.its.tera.job.prepare;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PrepareAgreementsJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap jobData = context.getJobDetail().getJobDataMap();
		
		Object archiveJob = jobData.get("agreementsArchivePrepare");
		
		
		 if (archiveJob == null || !(archiveJob instanceof AgreementsArchivePrepare))
	      {
	          throw new AlfrescoRuntimeException(
	                  "TrashcanCleanerJob data must contain valid 'AgreementsArchivePrepare' reference");
	      }
		
		final AgreementsArchivePrepare agreementsArchivePrepare = (AgreementsArchivePrepare) archiveJob;
		
		
		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
	    {
			public Object doWork() throws Exception
			{
				agreementsArchivePrepare.execute();
	            return null;
			}
	    }, AuthenticationUtil.getSystemUserName());
		
		
		
	}
	
	
	

}
