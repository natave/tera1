package com.its.tera.ws.arch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
//import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.JsonSyntaxException;
import com.its.tera.constants.CM;
import com.its.tera.constants.SE;
import com.its.tera.model.Agreement;
import com.its.tera.model.Client;
import com.its.tera.model.Document;
import com.its.tera.model.TechModel;
import com.its.tera.util.AppParameters;
import com.its.tera.util.ArchiveCalls;
import com.its.tera.util.FillModelsUtil;
import com.its.tera.ws.RootWebScript;

public class ArchiveAgreements2 extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(ArchiveAgreements2.class);
	
	private AppParameters appParameters;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		
		SearchService searchService = serviceRegistry.getSearchService();
		NodeService nodeService = serviceRegistry.getNodeService();
		
		
		
		String query = "TYPE:\"" + CM.QNAME_TYPE_GENERAL_AGREEMENT_FOLDER + "\" AND ASPECT:\""+ CM.QNAME_ASPECT_FOR_ARCHIVE + "\"";	
		logger.debug("query = " + query);
		
		ResultSet resultSet = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query);
		if(resultSet == null || resultSet.getNodeRefs().isEmpty()) {
			logger.debug("Does not exists in alfresco!");
		}
		
		logger.debug("result length = " + resultSet.length());
		
		String t = "Basic " + "YXJjaGl2ZTphcmNoaXZl";
		
		 List<NodeRef> nodeRefs = resultSet.getNodeRefs();
		 
		 for (NodeRef nodeRef : nodeRefs) {
			 
			 //CLIENT REGISTRATION
			 
			 NodeRef clientRef = nodeService.getSourceAssocs(nodeRef, CM.QNAME_ASSOC_ASPECT_GEN_AGRS).get(0).getSourceRef();
			 
			 Client client = new Client();
			 client.setCreated(((Date) nodeService.getProperty(clientRef, ContentModel.PROP_CREATED)).getTime());
			 client.setCreator((String) nodeService.getProperty(clientRef, ContentModel.PROP_CREATOR));	
			 client.setClientCode((String)nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_CODE));
			 client.setClientId((String)nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_ID));
			 client.setClientName((String)nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_NAME));
			 client.setClientIsVIP((Boolean)nodeService.getProperty(clientRef, CM.QNAME_ASPECT_CLIENT_IS_VIP));
			 client.setBranch((String)nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_BRANCH));
			 
			 String response = null;
			 try {
				 response = ArchiveCalls.callServicePost(SE.REG_CLIENT, getGson().toJson(client, Client.class), appParameters, t);
				 logger.debug("response = " + response);
				 if(!nodeService.hasAspect(clientRef, CM.QNAME_ASPECT_HAS_ARCHIVE)) {
					 nodeService.addAspect(clientRef, CM.QNAME_ASPECT_HAS_ARCHIVE, null);
				 }
			} catch (IOException e) {
				//e.printStackTrace();
				throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
			}
			 
			 Client clientGet = null;
			try {
				clientGet = getGson().fromJson(response, Client.class);
			} catch (JsonSyntaxException e) {
				throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
			}
			
			NodeRef clientArchiveRef = new NodeRef(clientGet.getNodeRef());
			
			//GENERAL AGREEMENT REGISTRATION
			
			Agreement gAgrModel = FillModelsUtil.getAgreementModel(nodeRef, clientArchiveRef, nodeService);
			gAgrModel.setName((String) nodeService.getProperty(nodeRef, ContentModel.PROP_NAME));
			gAgrModel.setLiveUser(null);
			
			 String gAgrResponse = null;
			 try {
				 gAgrResponse = ArchiveCalls.callServicePost(SE.REG_GEN_AGR, getGson().toJson(gAgrModel, Agreement.class), appParameters, t);
				 logger.debug("gAgrResponse = " + gAgrResponse);
			} catch (IOException e) {
				//e.printStackTrace();
				throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
			}
			 Agreement gAgrArchiveModel = getGson().fromJson(gAgrResponse, Agreement.class);
			 NodeRef gAgrArchiveRef = new NodeRef(gAgrArchiveModel.getNodeRef());
			 logger.debug("agr = " + nodeRef + ", ");
			
			//GEN DOCUMENTS
			
			Set<QName> childDocTypeQNames = new HashSet<QName>();
			childDocTypeQNames.add(CM.QNAME_TYPE_CREDIT_DOC);
			List<ChildAssociationRef> childDocs = nodeService.getChildAssocs(nodeRef, childDocTypeQNames);
			for (ChildAssociationRef childDoc : childDocs) {
				TechModel docModel = FillModelsUtil.getDocModelFull(childDoc.getChildRef(), clientArchiveRef, serviceRegistry, req.getServiceContextPath(), appParameters);
				docModel.setParentRef(gAgrArchiveRef.toString());
				
				if(nodeService.getProperty(childDoc.getChildRef(), ContentModel.PROP_CONTENT) != null) {
					InputStream stream = serviceRegistry.getContentService().getReader(childDoc.getChildRef(), ContentModel.PROP_CONTENT).getContentInputStream();
					byte[] binaryData = null;
					try {
						binaryData = IOUtils.toByteArray(stream);
					} catch (IOException e1) {
						throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e1.getMessage());
					}
					byte[] encodedArray = Base64.getEncoder().encode(binaryData);
					String encodedString = new String(encodedArray);
					docModel.setDocContent(encodedString);
				}
				
				
				String responseDoc = null;
				try {
					responseDoc = ArchiveCalls.callServicePost(SE.REG_DOC, getGson().toJson(docModel, Document.class), appParameters, t);
					logger.debug("responseDoc = " + responseDoc);
				} catch (IOException e) {
					//e.printStackTrace();
					throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
				}
			}
			
			
			
			//GEN CHILD AGREEMENTS
			List<AssociationRef> genChildAssocs = nodeService.getTargetAssocs(nodeRef, CM.QNAME_ASSOC_ASPECT_GEN_CHILD_AGRS);
			for (AssociationRef genChildAssoc : genChildAssocs) {
				
				NodeRef agrRef = genChildAssoc.getTargetRef();
				
				
				Agreement agrRefModel = FillModelsUtil.getAgreementModel(agrRef, clientArchiveRef, nodeService);
				agrRefModel.setParentRef(gAgrArchiveRef.toString());
				
				String agrResponse = null;
				try {
					agrResponse = ArchiveCalls.callServicePost(SE.REG_AGR, getGson().toJson(agrRefModel, Agreement.class), appParameters, t);
					logger.debug("agrResponse = " + agrResponse);
				} catch (IOException e) {
					//e.printStackTrace();
					throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
				}
				
				Agreement agrArchiveModel = getGson().fromJson(agrResponse, Agreement.class);
				NodeRef agrArchiveRef = new NodeRef(agrArchiveModel.getNodeRef());
				
				
				// // GEN CHILD AGREEMENT DOCUMENTS
				
				List<ChildAssociationRef> agrChildDocs = nodeService.getChildAssocs(agrRef, childDocTypeQNames);
				for (ChildAssociationRef agrChildDoc : agrChildDocs) {
					
					TechModel docModel = FillModelsUtil.getDocModelFull(agrChildDoc.getChildRef(), clientArchiveRef, serviceRegistry, req.getServiceContextPath(), appParameters);
					docModel.setParentRef(agrArchiveRef.toString());
					
					if(nodeService.getProperty(agrChildDoc.getChildRef(), ContentModel.PROP_CONTENT) != null) {
						InputStream stream = serviceRegistry.getContentService().getReader(agrChildDoc.getChildRef(), ContentModel.PROP_CONTENT).getContentInputStream();
						byte[] binaryData = null;
						try {
							binaryData = IOUtils.toByteArray(stream);
						} catch (IOException e1) {
							throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e1.getMessage());
						}
						byte[] encodedArray = Base64.getEncoder().encode(binaryData);
						String encodedString = new String(encodedArray);
						docModel.setDocContent(encodedString);
					}
					
					
					String responseDoc = null;
					try {
						responseDoc = ArchiveCalls.callServicePost(SE.REG_DOC, getGson().toJson(docModel, Document.class), appParameters, t);
						logger.debug("responseDoc = " + responseDoc);
					} catch (IOException e) {
						//e.printStackTrace();
						throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
					}
					
				}
				
				
				
				// DELETE CHILD AGREEMENT
				nodeService.deleteNode(agrRef);
					
			}
			
			//DELETE GENERAL AGREEMENT
			
			nodeService.deleteNode(nodeRef);	
				
		 }
			
			 //SEND REQUEST WITH ALL PROPERTIES AND CLIENT DATA  -  RETURN NODEREF
			 
			 //SEND DOCS
			 
			 //FOR
			 //SEND CHILD AGREEMENT  -  RETURN NODEREF
			 	//SEND CHILD AGREEMENT'S DOCS 
			 
			 
			 //DELETE GENERAL AND CHILDREN
		
		
		 
		 String query1 = "TYPE:\"" + CM.QNAME_TYPE_AGREEMENT_FOLDER + "\" AND ASPECT:\""+ CM.QNAME_ASPECT_FOR_ARCHIVE + "\"";	
		 logger.debug("query1 = " + query1);
		
		ResultSet resultSet1 = searchService.query(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, SearchService.LANGUAGE_SOLR_ALFRESCO, query1);
		if(resultSet1 == null || resultSet1.getNodeRefs().isEmpty()) {
			logger.debug("Does not exists in alfresco!");
		}
		
		logger.debug("result1 length = " + resultSet1.length());
		
		 List<NodeRef> nodeRefs1 = resultSet1.getNodeRefs();
			 
		 for (NodeRef nodeRef : nodeRefs1) {
				 
		 //CLIENT REGISTRATION
		 
		 NodeRef clientRef = nodeService.getSourceAssocs(nodeRef, CM.QNAME_ASSOC_ASPECT_AGRS).get(0).getSourceRef();
		 
		 Client client = new Client();
		 client.setCreated(((Date) nodeService.getProperty(clientRef, ContentModel.PROP_CREATED)).getTime());
		 client.setCreator((String) nodeService.getProperty(clientRef, ContentModel.PROP_CREATOR));	
		 client.setClientCode((String)nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_CODE));
		 client.setClientId((String)nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_ID));
		 client.setClientName((String)nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_CLIENT_NAME));
		 client.setClientIsVIP((Boolean)nodeService.getProperty(clientRef, CM.QNAME_ASPECT_CLIENT_IS_VIP));
		 client.setBranch((String)nodeService.getProperty(clientRef, CM.QNAME_PROP_ASPECT_BRANCH));
		 
		 String response = null;
		 try {
			 response = ArchiveCalls.callServicePost(SE.REG_CLIENT, getGson().toJson(client, Client.class), appParameters, t);
			 logger.debug("response = " + response);
		 } catch (IOException e) {
			// e.printStackTrace();
			 throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		 }
		 
		 Client clientGet = null;
		 try {
			 clientGet = getGson().fromJson(response, Client.class);
		 } catch (JsonSyntaxException e) {
			 throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		 }
		
		 NodeRef clientArchiveRef = new NodeRef(clientGet.getNodeRef());
		
		 // AGREEMENT REGISTRATION
		
		 Agreement agrModel = FillModelsUtil.getAgreementModel(nodeRef, clientArchiveRef, nodeService);
		 agrModel.setLiveUser(null);
		
		 String agrResponse = null;
		 try {
			 agrResponse = ArchiveCalls.callServicePost(SE.REG_AGR, getGson().toJson(agrModel, Agreement.class), appParameters, t);
			 logger.debug("response = " + response);
		 } catch (IOException e) {
			//e.printStackTrace();
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
		 }
		 Agreement agrArchiveModel = getGson().fromJson(agrResponse, Agreement.class);
		 NodeRef agrArchiveRef = new NodeRef(agrArchiveModel.getNodeRef());
		
		//AGREEMENT DOCUMENTS
		
		Set<QName> childDocTypeQNames = new HashSet<QName>();
		childDocTypeQNames.add(CM.QNAME_TYPE_CREDIT_DOC);
		List<ChildAssociationRef> childDocs = nodeService.getChildAssocs(nodeRef, childDocTypeQNames);
				for (ChildAssociationRef childDoc : childDocs) {
					TechModel docModel = FillModelsUtil.getDocModelFull(childDoc.getChildRef(), clientArchiveRef, serviceRegistry, req.getServiceContextPath(), appParameters);
					docModel.setParentRef(agrArchiveRef.toString());
					
					if(nodeService.getProperty(childDoc.getChildRef(), ContentModel.PROP_CONTENT) != null) {
						InputStream stream = serviceRegistry.getContentService().getReader(childDoc.getChildRef(), ContentModel.PROP_CONTENT).getContentInputStream();
						byte[] binaryData = null;
						try {
							binaryData = IOUtils.toByteArray(stream);
						} catch (IOException e1) {
							throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e1.getMessage());
						}
						byte[] encodedArray = Base64.getEncoder().encode(binaryData);
						String encodedString = new String(encodedArray);
						docModel.setDocContent(encodedString);
					}
					
					
					String responseDoc = null;
					try {
						responseDoc = ArchiveCalls.callServicePost(SE.REG_DOC, getGson().toJson(docModel, Document.class), appParameters, t);
						logger.debug("responseDoc = " + responseDoc);
					} catch (IOException e) {
						//e.printStackTrace();
						throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, e.getMessage());
					}
				}
				
				//DELETE  AGREEMENT
				
				nodeService.deleteNode(nodeRef);	
								 
				 //SEND  AGREEMENT WITH ALL PROPERTIES AND CLIENT DATA   -  RETURN NODEREF
				 	//SEND  AGREEMENT'S DOCS 
				 
				 
				 //DELETE AGREEMENT
			}
		
		
			 Map<String, Object> model = new HashMap<String, Object>();
			 model.put("result",  "ok");
			 return model;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}
	
	

}
