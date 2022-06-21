package com.its.tera.model.report;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RunReportModel implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	String nodeRef;
	Map<String, Object> params;
	Integer resultType;
	Boolean paginate;
	
	List<BRParameterModel> parameters;
	
	
	public String getNodeRef() {
		return nodeRef;
	}
	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Integer getResultType() {
		return resultType;
	}
	public void setResultType(Integer resultType) {
		this.resultType = resultType;
	}
	public Boolean getPaginate() {
		return paginate;
	}
	public void setPaginate(Boolean paginate) {
		this.paginate = paginate;
	}
	public List<BRParameterModel> getParameters() {
		return parameters;
	}
	public void setParameters(List<BRParameterModel> parameters) {
		this.parameters = parameters;
	}
	
	
	

}
