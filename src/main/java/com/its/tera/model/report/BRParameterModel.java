package com.its.tera.model.report;

import java.io.Serializable;
import java.util.List;

public class BRParameterModel /*extends BaseModelData*/ implements Serializable 
{
	private static final long serialVersionUID = 7032567709984512008L;
	
	public static final int TEXT_BOX = 0;
	public static final int LIST_BOX = 1;
	public static final int RADIO_BUTTON = 2;
	public static final int CHECK_BOX = 3;
  
	public static final int TYPE_ANY = 0;
	public static final int TYPE_STRING = 1;
	public static final int TYPE_FLOAT = 2;
	public static final int TYPE_DECIMAL = 3;
	public static final int TYPE_DATE_TIME = 4;
	public static final int TYPE_BOOLEAN = 5;
	public static final int TYPE_INTEGER = 6;
	public static final int TYPE_DATE = 7;
	public static final int TYPE_TIME = 8;
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String TIME_FORMAT = "HH:mm:ss.SSS";
	
	String name;
	String promptText;
	String helpText;
	boolean required;
	boolean echoInput;
	boolean hidden;
	Integer dataType;
	Integer displayType;
	Object defaultValue;
	List<Object> selectionListValues;
	
	
	Object value;
  
	public BRParameterModel() 
	{
		setHidden(false);
		setRequired(false);
		setEchoInput(true);
	}

	/**
	 * Parameter name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 *  Prompt text
	 */
	public String getPromptText() {
		return promptText;
	}

	public void setPromptText(String promptText) {
		this.promptText = promptText;
	}

	/**
	 *  Help text
	 */
	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	/**
	 *  Is equired
	 */
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Do not echo input
	 */
	public boolean isEchoInput() {
		return echoInput;
	}

	public void setEchoInput(boolean echoInput) {
		this.echoInput = echoInput;
	}

	/**
	 * Hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 *  Data type
	 */
	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	/**
	 *  Display type
	 */
	public Integer getDisplayType() {
		return displayType;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	/**
	 * Default value
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 *  Selection list values
	 */
	public List<Object> getSelectionListValues() {
		return selectionListValues;
	}

	public void setSelectionListValues(List<Object> selectionListValues) {
		this.selectionListValues = selectionListValues;
	}
	
	
	/**
	 *  Parameter Value for json input
	 */
	

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Parameter name
	 *//*
	public void setName(String name)
	{
		set("name", name);
	}
	
	public String getName()
	{
		return (String) get("name");
	}
	
	*//**
	 *  Prompt text
	 *//*
	public void setPromptText(String promptText)
	{
		set("promptText", promptText);
	}
	
	public String getPromptText()
	{
		return (String) get("promptText");
	}
	
	*//**
	 *  Help text
	 *//*
	public void setHelpText(String helpText)
	{
		set("helpText", helpText);
	}
	
	public String getHelpText()
	{
		return (String) get("helpText");
	}
	
	*//**
	 *  Is equired
	 *//*
	public void setAsRequired(boolean required)
	{
		set("required", required);
	}
	
	public boolean isRequired()
	{
		return get("required");
	}
	
	*//**
	 * Do not echo input
	 *//*
	public void setEchoInput(boolean echoInput)
	{
		set("echoInput", echoInput);
	}
	
	public boolean getEchoInput()
	{
		return get("echoInput");
	}
	
	*//**
	 * Hidden
	 *//*
	public void setHidden(boolean hidden)
	{
		set("hidden", hidden);
	}
	
	public boolean isHidden()
	{
		return get("hidden");
	}
	
	*//**
	 *  Data type
	 *//*
	public void setDataType(Integer dataType)
	{
		set("dataType", dataType);
	}
	
	public Integer getDataType()
	{
		return (Integer) get("dataType");
	}
	
	*//**
	 *  Display type
	 *//*
	public void setDisplayType(Integer displayType)
	{
		set("displayType", displayType);
	}

	public Integer getDisplayType()
	{
		return (Integer) get("displayType");
	}
	
	*//**
	 * Default value
	 *//*
	public void setDefaultValue(Object defaultValue)
	{
		set("defaultValue", defaultValue);
	}
	
	public Object getDefaultValue()
	{
		return get("defaultValue");
	}
	
	*//**
	 *  Selection list values
	 *//*
	public void setSelectionListValues(List<Object> selectionListValues)
	{
		set("selectionListValues", selectionListValues);
	}
	
	public List<Object> getSelectionListValues()
	{
		return get("selectionListValues");
	}*/

	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof BRParameterModel)
		{
			String name = ((BRParameterModel) obj).getName();
			return name.equalsIgnoreCase(getName());
		}
		else
		{
			return false;
		}
	}
}
