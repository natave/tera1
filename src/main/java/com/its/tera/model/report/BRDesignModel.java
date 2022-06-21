package com.its.tera.model.report;

import java.io.Serializable;
import java.util.List;



public class BRDesignModel /*extends BaseTreeModel*/ implements Serializable 
{
	private static final long serialVersionUID = -5871590135708764078L;
	public static final Integer RESULT_TYPE_HTML = 1;
	public static final Integer RESULT_TYPE_PDF = 2;
	public static final Integer RESULT_TYPE_XLSX = 3;
	
	
	String nodeRef;
	String name;
	String displayText;
	List<BRParameterModel> params;
	List<BRDesignModel> children;
	
	public BRDesignModel() {}

	/**
	 * NodeRef 
	 */
	public String getNodeRef() {
		return nodeRef;
	}

	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}

	/**
	 * Category(Space) - Design(file) name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 *  Category(Space) - Design(file) display text
	 */
	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	/**
	 * Parameters. When this model represents Category this property will be null
	 */
	public List<BRParameterModel> getParams() {
		return params;
	}

	public void setParams(List<BRParameterModel> params) {
		this.params = params;
	}

	
	/*public void setNodeRef(String nodeRef)
	{
		set("nodeRef", nodeRef);
	}
	
	public String getNodeRef() 
	{
		return (String) get("nodeRef");
	}

	*//*
	public void setName(String name)
	{
		set("name", name);
	}
	
	public String getName()
	{
		return (String) get("name");
	}
	
	*//*
	public void setDisplayText(String displayText)
	{
		set("displayText", displayText);
	}
	
	public String getDisplayText()
	{
		return (String) get("displayText");
	}
	
	*//*
	public void setParameters(List<BRParameterModel> params)
	{
		set("params", params);
	}
	
	public List<BRParameterModel> getParameters()
	{
		return get("params");
	}*/
	
	public List<BRDesignModel> getChildren() {
		return children;
	}

	public void setChildren(List<BRDesignModel> children) {
		this.children = children;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof BRDesignModel)
		{
			String name = ((BRDesignModel) obj).getName();
			return name.equalsIgnoreCase(getName());
		}
		else
		{
			return false;
		}
	}
}
