package com.its.tera.constants;

public class Path {
	
	public static final String ORGANISATION = "cm:Company";	
	
	public static final String QUERY_SME_ALL_CHILDREN = "PATH:\"/app:company_home/cm:SME//*\"";
	public static final String QUERY_SCANS_FOLDER = "PATH:\"/app:company_home/cm:Scans\"";
	
	public static final String QUERY_LEGAL_FOLDER = "PATH:\"/app:company_home/cm:Legal\"";
	
	
	//File upload default space
	public static final String QUERY_PATH_UPLOAD_DEF_SPACE = "PATH:\"/app:company_home/" + ORGANISATION + "/cm:Uploaded_x0020_Files/cm:Temp\"";
	
	
	
	//SCANS
	public static final String QUERY_SCANS_BY_BRANCH = "PATH:\"/app:company_home/cm:Scans/cm:scansBranch-{0}/cm:ScansPDF\"";
	
	
	
	//Reporting - BirtDesignFiles
	public static final String QUERY_PATH_RPT_BIRT_REPORT_WITH_ALL_PARAMETERS = "PATH:\"/app:company_home/" + ORGANISATION + "/cm:Reporting/cm:Special_x0020_Reports/cm:ChooseParametersReport.rptdesign\"";
	public static final String QUERY_PATH_RPT_BIRT_REPORT_WITH_CLIENT_DOCS = "PATH:\"/app:company_home/" + ORGANISATION + "/cm:Reporting/cm:Special_x0020_Reports/cm:ClientDocumentsReport.rptdesign\"";
	public static final String QUERY_PATH_RPT_BIRT_REPORT_USERS_GROUPS = "PATH:\"/app:company_home/" + ORGANISATION + "/cm:Reporting/cm:Special_x0020_Reports/cm:UsersGroupsReport.rptdesign\"";
	public static final String QUERY_PATH_RPT_BIRT_REPORT_CLIENTS_LIST = "PATH:\"/app:company_home/" + ORGANISATION + "/cm:Reporting/cm:Special_x0020_Reports/cm:ClientsListReport.rptdesign\"";
	
	public static final String QUERY_PATH_RPT_BIRT_RESULTS_TEMP_SPACE = "PATH:\"/app:company_home/" + ORGANISATION + "/cm:Reporting/cm:Birt_x0020_Results/cm:Results_Temp\"";
		
	

}
