package com.its.tera.ws.um;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.google.gson.reflect.TypeToken;
import com.its.tera.constants.CO;
import com.its.tera.constants.UM;
import com.its.tera.model.um.Group;
import com.its.tera.model.um.User;
import com.its.tera.mybatis.service.DBServiceBean;
import com.its.tera.ws.RootWebScript;

public class GetGroupsAndUsers extends RootWebScript {
	
	private static Log logger = LogFactory.getLog(GetGroupsAndUsers.class);
	
	private DBServiceBean dbServiceBean;
	private AuthorityService authorityService;
	private NodeService nodeService;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		authorityService = serviceRegistry.getAuthorityService();
		nodeService = serviceRegistry.getNodeService();
		
		
		String rootGroupName /* = req.getParameter("root") */;
		try {
			rootGroupName = URLEncoder.encode(req.getParameter("root"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST, e.getMessage());
		}
		
		
		List<Group> tree = new ArrayList<Group>();
		if(authorityService.authorityExists(rootGroupName))
		{
			if(rootGroupName.equalsIgnoreCase(UM.GROUP_BRANCHES))
			{
				Set<String> branches = new HashSet<String>();
				branches.addAll(authorityService.getContainedAuthorities(AuthorityType.GROUP, rootGroupName, true));
				for(String branch : branches)
				{
					if(dbServiceBean.selectBranchesByBranchId(branch.replace(CO.GROUP_ + CO.BRANCH_, "")) != null)
					{							
						Group group = dbServiceBean.selectBranchesByBranchId(branch.replace(CO.GROUP_ + CO.BRANCH_, ""));
						group.setChildren(getChildrenListForGroup(branch, AuthorityType.USER));
						tree.add(group);
					}
				}	
			}
			else if(rootGroupName.equalsIgnoreCase(UM.GROUP_RIGHTS))
			{
				Group groupRight = new Group();
				groupRight.setName(UM.GROUP_RIGHTS);
				groupRight.setTitle(UM.RIGHTS);
				groupRight.setChildren(getChildrenListForGroup(UM.GROUP_RIGHTS, AuthorityType.GROUP));
				tree.add(groupRight);
				
				Group groupVIPRight = new Group();
				groupVIPRight.setName(UM.GROUP_VIP_RIGHTS);
				groupVIPRight.setTitle(UM.VIP_RIGHTS);
				groupVIPRight.setChildren(getChildrenListForGroup(UM.GROUP_VIP_RIGHTS, AuthorityType.GROUP));
				tree.add(groupVIPRight);
				logger.debug("client group = " + UM.GROUP_LEGAL_RIGHTS + " - " + UM.LEGAL_RIGHTS);
				Group groupLegalRight = new Group();
				groupLegalRight.setName(UM.GROUP_LEGAL_RIGHTS);
				groupLegalRight.setTitle(UM.LEGAL_RIGHTS);
				groupLegalRight.setChildren(getChildrenListForGroup(UM.GROUP_LEGAL_RIGHTS, AuthorityType.GROUP));
				tree.add(groupLegalRight);
			}									
		}
		else
		{
			throw new WebScriptException("Group with name - '"+ rootGroupName +"' doesn't exists in system!");
		}		
		
		
		
		Type typeOfSrc = new TypeToken<Collection<Group>>(){}.getType();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getGson().toJson(tree, typeOfSrc));
		return model;
	}
	
	
	
	
	
	private List<Group> getChildrenListForGroup(String groupName, AuthorityType authorityType)
	{
		Set<String> children = new HashSet<String>();
		List<Group> childrenList = new ArrayList<Group>();
		children.addAll(authorityService.getContainedAuthorities(authorityType, groupName, true));
		
		
		if(authorityType.equals(AuthorityType.USER))
		{
			for(String child : children)
			{
				User u = new User();
				u.setUserName(child);
				u.setName(child);
				u.setBranchId(groupName + child);
				NodeRef personRef = serviceRegistry.getPersonService().getPerson(child, false);
				//logger.debug("user = " + child + ",  personRef = " + personRef);
				if(personRef != null)
				{
					//logger.debug("first = " + nodeService.getProperty(personRef, ContentModel.PROP_FIRSTNAME));
					u.setFirstName((String) nodeService.getProperty(personRef, ContentModel.PROP_FIRSTNAME));
					u.setLastName((String) nodeService.getProperty(personRef, ContentModel.PROP_LASTNAME));
					u.setEmail((String) nodeService.getProperty(personRef, ContentModel.PROP_EMAIL));
					u.setFullName(nodeService.getProperty(personRef, ContentModel.PROP_FIRSTNAME) 
							+ " " + nodeService.getProperty(personRef, ContentModel.PROP_LASTNAME));
				}
				childrenList.add(u);
			}			
		}
		else if(authorityType.equals(AuthorityType.GROUP))
		{
			for(String child : children)
			{
				System.out.println(child);
				Group group = new Group();
				group.setName(child);
				group.setTitle(child.replace(CO.GROUP_, ""));
				group.setChildren(getChildrenListForGroup(child, AuthorityType.USER));
				childrenList.add(group);
			}				
		}		
		return childrenList;
	}
	
	
	
	

	public void setDbServiceBean(DBServiceBean dbServiceBean) {
		this.dbServiceBean = dbServiceBean;
	}
	
	
	

}
