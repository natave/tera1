package com.its.tera.ws.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.constants.CM;
import com.its.tera.constants.CO;
import com.its.tera.constants.Path;
import com.its.tera.model.Client;
import com.its.tera.util.AppParameters;
import com.its.tera.ws.RootWebScript;

public class SearchForClient2 extends RootWebScript {

	private static Log logger = LogFactory.getLog(SearchForClient2.class);
	
	AppParameters appParameters;
	
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		NodeService nodeService = serviceRegistry.getNodeService();
		
		String clientCode = req.getParameter("clientCode");
		String clientNameEncoded = req.getParameter("clientName");
		String clientName = (clientNameEncoded == null || clientNameEncoded.isEmpty()) ? "" : new String(Base64.getDecoder().decode(clientNameEncoded));
		String clientId = req.getParameter("clientId");
		
		
		List<Client> clients = new ArrayList<Client>();
		try {
			clients.addAll(searchInALTA(clientCode, clientName, clientId));
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WebScriptException(Status.STATUS_SERVICE_UNAVAILABLE, "Tera service error : " + e.getMessage());			
		}
		logger.debug("searchInALTA clients list size = " + clients.size());
			
		List<Client> clientsAlf = new ArrayList<Client>();
		for (Client client : clients) {
			String code = client.getClientCode();
			String query = "TYPE:\""+ CM.QNAME_TYPE_CLIENT_FOLDER  +"\" AND @cm\\:name:\"" + CO.CLIENT_PREFIX + code + "\" AND " + Path.QUERY_SME_ALL_CHILDREN;	
			logger.debug("searchForClients query = " + query);
			
			ResultSet results = serviceRegistry.getSearchService().query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query );
			if(results != null && results.length() != 0)
			{
				logger.debug("result code = " + code);
				NodeRef nodeRef = results.getNodeRefs().get(0);
				client.setNodeRef(nodeRef.toString());
				client.setCreated(((Date) nodeService.getProperty(nodeRef, ContentModel.PROP_CREATED)).getTime());
				client.setClientIsVIP(nodeService.hasAspect(nodeRef, CM.QNAME_ASPECT_CLIENT_IS_VIP));
				client.setIsEmpty(nodeService.hasAspect(nodeRef, CM.QNAME_ASPECT_ITEM_IS_EMPTY));
				if(client.getClientName() != null && 
						!client.getClientName().equalsIgnoreCase((String) nodeService.getProperty(nodeRef, CM.QNAME_PROP_ASPECT_CLIENT_NAME)));
				{
					client.setNameIsDifferent(true);
				}
			}
			results.close();
			clientsAlf.add(client);
		}
		
		logger.debug("searchInALTA clients list size = " + clients.size());
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(clientsAlf));
		return model;
	}
	
	
	private List<Client> searchInALTA(String clientCode, String clientName, String clientId) throws RemoteException
	{
		List<Client> list = new ArrayList<Client>();
		System.out.println("PROP_ASPECT_CLIENT_NAME = " + clientName + "  PROP_ASPECT_CLIENT_ID" + clientId);
		
		if(clientName != null && clientName.equalsIgnoreCase("null")) {
			return list;
		}
		
		if(clientCode != null && clientCode.equalsIgnoreCase("100")) {
			Client client = new Client();
			client.setClientCode(clientCode.length() > 5 ? clientCode : (new Date()).getTime() + "");
			client.setClientName(clientName.length() > 6 ? clientName.replace("%", "") : " კლიენტის დასახელება");
			client.setClientId(clientId.length() > 8 ? clientId : (new Date()).getTime() + "");
			client.setBranch("0");			
			client.setClientIsVIP(true);
			//logger.debug("client code = " + c[i].getCLIENT_NO() + "  dept_number = " + c[i].getDEPT_NO() + "  vip = " + c[i].getIS_VIP());
			list.add(client);
		}
		if(clientCode != null && clientCode.equalsIgnoreCase("99")) {
			for(int i = 0; i < 9; i++)
			{
				Client client = new Client();
				client.setClientCode(clientCode.length() > 5 ? clientCode + i : (new Date()).getTime() + "" +i);
				client.setClientName(clientName.length() > 6 ? clientName.replace("%", "") : " კლიენტის დასახელება");
				client.setClientId(clientId.length() > 8 ? clientId + i : (new Date()).getTime() + "" + i);
				client.setBranch(i > 5 ? "7" : "0");			
				client.setClientIsVIP(i == 5 ? true : false);
				//logger.debug("client code = " + c[i].getCLIENT_NO() + "  dept_number = " + c[i].getDEPT_NO() + "  vip = " + c[i].getIS_VIP());
				list.add(client);
			}
			
		}
		else
		{
			Client client = new Client();
			client.setClientCode(clientCode.length() > 5 ? clientCode : (new Date()).getTime() + "");
			client.setClientName(clientName.length() > 6 ? clientName.replace("%", "") : " კლიენტის დასახელება");
			client.setClientId(clientId.length() > 8 ? clientId : (new Date()).getTime() + "");
			client.setBranch(clientCode != null && clientCode.startsWith("7") ? "7" : "0");			
			client.setClientIsVIP(false);
			//logger.debug("client code = " + c[i].getCLIENT_NO() + "  dept_number = " + c[i].getDEPT_NO() + "  vip = " + c[i].getIS_VIP());
			list.add(client);
		}
		
//		ITSResultClient clientsRes = iTSServiceSoapProxy.getClient(clientCode,
//				clientName, clientId, appParameters.getKsbAPIServiceSoapAddress());
//		com.its.webservices.ksb.Client[] c = clientsRes.getClients();
//		Integer length = c.length;logger.debug("ALTA result length = " + length);
//		if(length > 50)
//		{
//			length = 50;
//		}
//		for(int i = 0; i < length; i++)
//		{
//			Client client = new Client();
//			client.setClientCode(c[i].getCLIENT_NO());
//			client.setClientName(c[i].getDESCRIP());
//			client.setClientId((c[i].getPERSONAL_ID() == "" || c[i].getPERSONAL_ID() == null) ? c[i].getTAX_INSP_CODE() : c[i].getPERSONAL_ID());
//			client.setBranch(c[i].getDEPT_NO());			
//			client.setClientIsVIP(new Boolean(c[i].getIS_VIP()));
//			logger.debug("client code = " + c[i].getCLIENT_NO() + "  dept_number = " + c[i].getDEPT_NO() + "  vip = " + c[i].getIS_VIP());
//			list.add(client);
//		}
		return list;
	}

	
	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	
	
	

}
