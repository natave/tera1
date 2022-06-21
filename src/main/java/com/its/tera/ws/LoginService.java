package com.its.tera.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.MutableAuthenticationService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.sync.events.types.Event;
import org.alfresco.sync.events.types.RepositoryEventImpl;
import org.alfresco.sync.repo.events.EventPreparator;
import org.alfresco.sync.repo.events.EventPublisher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.surf.util.Content;
import org.springframework.extensions.surf.util.I18NUtil;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.its.tera.constants.CM;
import com.its.tera.constants.CO;
import com.its.tera.constants.UM;
import com.its.tera.model.um.User;
import com.its.tera.mybatis.service.DBServiceBean;
import com.its.tera.util.AppParameters;

public class LoginService extends RootWebScript{
	
	private static Log logger = LogFactory.getLog(LoginService.class);
	
	//private ServiceRegistry serviceRegistry;
	private MutableAuthenticationService authenticationService;
	private AuthorityService authorityService;
	private NodeService nodeService;
	private EventPublisher eventPublisher;
	private AppParameters appParameters;
	private DBServiceBean dbServiceBean;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		authenticationService = serviceRegistry.getAuthenticationService();
		authorityService = serviceRegistry.getAuthorityService();
		nodeService = serviceRegistry.getNodeService();
		
		// Extract content from JSON POST
		Content content = req.getContent();
		if (content == null) {
			logger.error("error_missing_request_body");
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, I18NUtil.getMessage("error_missing_request_body"));
		}
		
		// extract username and password from JSON object
		JSONObject json;
		try {
			
			json = new JSONObject(content.getContent());
			String userName = json.getString("username");logger.debug("userName - " + userName);
			String password = json.getString("password");
			
			try {
				
				eventPublisher.publishEvent(new EventPreparator() {
					@Override
					public Event prepareEvent(String user, String networkId, String transactionId) {logger.debug("prepareEvent ");
						return new RepositoryEventImpl(-1l, "login", transactionId, networkId, new Date().getTime(), userName, null);
					}
				});
				logger.debug("1 ");
				RunAsWork<User> runAsWork = new RunAsWork<User>() {

					@Override
					public User doWork() throws Exception {
						if(!authorityService.authorityExists(userName)){
							logger.error("error_login_failed authority doesn't exists.  "  + userName);
							throw new WebScriptException(Status.STATUS_BAD_REQUEST, I18NUtil.getMessage("error_login_failed"));
						}
						
						final User userInfo = new User();
						final NodeRef personRef = authorityService.getAuthorityNodeRef(userName);//personService.getPerson(username);
						if(personRef == null){							
							logger.error("error_login_failed personRef == null.  "  + userName);
							throw new WebScriptException(Status.STATUS_BAD_REQUEST, I18NUtil.getMessage("error_login_failed"));
						}
						
						logger.debug("2 ");
						authenticationService.authenticate(userName, password.toCharArray());
						logger.debug("User auth - " + userName);
						
						userInfo.setUserName((String) nodeService.getProperty(personRef, ContentModel.PROP_USERNAME));
						userInfo.setFirstName((String) nodeService.getProperty(personRef, ContentModel.PROP_FIRSTNAME));
						userInfo.setLastName((String) nodeService.getProperty(personRef, ContentModel.PROP_LASTNAME));
						userInfo.setEmail((String) nodeService.getProperty(personRef, ContentModel.PROP_EMAIL));
						userInfo.setJobTitle((String) nodeService.getProperty(personRef, ContentModel.PROP_JOBTITLE));
						
										        
						// get ticket
				        String ticket = authenticationService.getCurrentTicket();
						String authData = "ROLE_TICKET:" + ticket;
						String authData64 = Base64.getEncoder().encodeToString(authData.getBytes());
						
						
						userInfo.setTicket("Basic " + authData64);
						
						
						List<String> roles = new ArrayList<String>();
						
						String branch = null;
						
						if(authorityService.isAdminAuthority(userName))
						{
//							vipRight = 6;
//							right = 6;
//							legalRight = 6;
							
//							roles.add("admin");
							//userInfo.addAllowed(CO.KEY_ALL);
							userInfo.addAllowed(CO.KEY_GROUP_MANAGE);
							userInfo.addAllowed(CO.KEY_CORR_TYPE_MANAGE);
							userInfo.addAllowed(CO.KEY_CLIENT_ADD);
							userInfo.addAllowed(CO.KEY_NEW_LEGAL_F);

							Set<String> auth = authorityService.getContainingAuthorities(AuthorityType.GROUP, userName, true);	
							logger.debug(" authoritiesFor admin = " + auth);
							
							for (String gr : auth) {
								
								if(gr.startsWith(PermissionService.GROUP_PREFIX + CO.BRANCH_) &&
										 dbServiceBean.selectBranchesByBranchId(authorityService.getShortName(gr.replaceFirst(PermissionService.GROUP_PREFIX + CO.BRANCH_, ""))) != null)
								{
									branch = authorityService.getShortName(gr);
								}
								
							}
							
							if(branch == null && authorityService.authorityExists(appParameters.getRootBranch()))
							{
								branch = authorityService.getShortName(appParameters.getRootBranch());
							}
							else if(branch == null && authorityService.authorityExists(appParameters.getBranchesParentGroup()))
							{
								Set<String> branchesForAdmin = authorityService.getContainedAuthorities(AuthorityType.GROUP, appParameters.getBranchesParentGroup(), true);
								if(branchesForAdmin.size() != 0)
								{
									branch = authorityService.getShortName(branchesForAdmin.iterator().next());									
								}
							}
							
						}
						else 
						{
							Set<String> auth = authorityService.getContainingAuthorities(AuthorityType.GROUP, userName, true);	
							logger.debug(" authoritiesFor user = " + auth);
							
							for (String gr : auth) {
								logger.debug("gr1 = " + gr);
								logger.debug("gr2 = " + PermissionService.GROUP_PREFIX + CO.BRANCH_);
								
								if(gr.startsWith(PermissionService.GROUP_PREFIX + CO.BRANCH_) 
								/*
								 * && dbServiceBean.selectBranchesByBranchId(authorityService.getShortName(gr.
								 * replaceFirst(PermissionService.GROUP_PREFIX + CO.BRANCH_, ""))) != null
								 */)
								{
									branch = authorityService.getShortName(gr);
								}
								
							}
							
							if(auth.contains(PermissionService.GROUP_PREFIX + UM.VIP_COORDINATOR)) {
								userInfo.addAllowed(CO.KEY_GROUP_MANAGE);
								userInfo.addAllowed(CO.KEY_CORR_TYPE_MANAGE);
								userInfo.addAllowed(CO.KEY_CLIENT_ADD);
								userInfo.addAllowed(CO.KEY_HAS_ARCHIVE);
//								roles.add("groupManagement");
//								roles.add("corrTypeManagement");
							}
							else if(auth.contains(PermissionService.GROUP_PREFIX + UM.RIGHTS_COORDINATOR)
									|| auth.contains(PermissionService.GROUP_PREFIX + UM.VIP_COLLABORATOR)
									|| auth.contains(PermissionService.GROUP_PREFIX + UM.VIP_CONTRIBUTOR)
									|| auth.contains(PermissionService.GROUP_PREFIX + UM.RIGHTS_COLLABORATOR)
									|| auth.contains(PermissionService.GROUP_PREFIX + UM.RIGHTS_CONTRIBUTOR)) {
								userInfo.addAllowed(CO.KEY_CLIENT_ADD);
							}
							
							if(auth.contains(PermissionService.GROUP_PREFIX + UM.LEGAL_COORDINATOR)
									|| auth.contains(PermissionService.GROUP_PREFIX + UM.LEGAL_COLLABORATOR)
									|| auth.contains(PermissionService.GROUP_PREFIX + UM.LEGAL_CONTRIBUTOR)) {
								userInfo.addAllowed(CO.KEY_NEW_LEGAL_F);
							}
						}
						if(branch == null) {logger.debug("Branch is null!!!");
							throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Branch is null!");
						}
						
						String branchId = branch.replace(UM.BRANCH_START, "");
						logger.debug(" branch = " + branch + ", branchId = " + branchId);
						
						
						userInfo.setBranchId(branchId);
						
						
						
						// TODO Auto-generated method stub
						return userInfo;
					}
					
				};
				
				Map<String, Object> model = new HashMap<String, Object>();
				User u = AuthenticationUtil.runAs(runAsWork, AuthenticationUtil.getAdminUserName()/*AuthenticationUtil.SYSTEM_USER_NAME*/);
				if(u == null){
					logger.error("error_user_disabled  "  + userName);
					throw new WebScriptException(Status.STATUS_UNAUTHORIZED, I18NUtil.getMessage("error_user_disabled"));
				}
								
				model.put("userInfo", getGson().toJson(u));
				return model;
				
			} catch (AuthenticationException e) {
				logger.error("error_login_failed - Login failed. Username or Password is Incorrect.  " + userName);
				throw new WebScriptException(Status.STATUS_UNAUTHORIZED, I18NUtil.getMessage("error_login_failed"));
			} finally {logger.debug("3 ");
				AuthenticationUtil.clearCurrentSecurityContext();
			}
			
			
			
		} catch (JSONException jErr) {//logger.debug("jErr");
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, "Unable to parse JSON POST body: " + jErr.getMessage());
		} catch (IOException ioErr) {//logger.debug("ioErr");
			throw new WebScriptException(Status.STATUS_INTERNAL_SERVER_ERROR, "Unable to retrieve POST body: " + ioErr.getMessage());
		}
		
		
		
		
		
	}

	public void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public void setAppParameters(AppParameters appParameters) {
		this.appParameters = appParameters;
	}

	public void setDbServiceBean(DBServiceBean dbServiceBean) {
		this.dbServiceBean = dbServiceBean;
	}
	
	

}
