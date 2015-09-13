package org.merka.pentaho.ext.service;

import java.util.Hashtable;
import java.util.Map;

public class ApplicationMappings
{
	private Map<String, String> usernamesMap;
	private String applicationName;
	public Map<String, String> getUsernamesMap()
	{
		return usernamesMap;
	}
	public void setUsernamesMap(Map<String, String> usernamesMap)
	{
		this.usernamesMap = usernamesMap;
	}
	public String getApplicationName()
	{
		return applicationName;
	}
	public void setApplicationName(String applicationName)
	{
		this.applicationName = applicationName;
	}
	
	public ApplicationMappings(){}
	
	public ApplicationMappings(String name)
	{
		setApplicationName(name);
		setUsernamesMap(new Hashtable<String, String>());
	}
}
