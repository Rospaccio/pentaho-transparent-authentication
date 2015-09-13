package org.merka.pentaho.ext.service;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.merka.pentaho.ext.exception.MappingNotFoundException;

public class InMemoryUsernameProvider implements UsernameProvider
{
	private Map<String, ApplicationMappings> applicationsMap;
	
	public Map<String, ApplicationMappings> getApplicationsMap()
	{
		return applicationsMap;
	}

	public void setApplicationsMap(Map<String, ApplicationMappings> applicationsMap)
	{
		this.applicationsMap = applicationsMap;
	}

	public InMemoryUsernameProvider()
	{
		setApplicationsMap(new java.util.Hashtable<String, ApplicationMappings>());
	}
	
	@Override
	public String getUsername(String externalApplicationName, String externalUsername)
	{
		ApplicationMappings existentMapping = getApplicationsMap().get(externalApplicationName);
		if(existentMapping == null)
		{
			throw new MappingNotFoundException("Application named '" + externalApplicationName + "' is not present in the current mappings");
		}
		
		String pentahoUsername = existentMapping.getUsernamesMap().get(externalUsername);
		if(pentahoUsername == null){
			throw new MappingNotFoundException("External username '" + externalUsername 
					+ "' not found in the mappings for application '" + externalApplicationName + "'");
		}
		
		return pentahoUsername;
	}

	/**
	 * Adds a mapping between an external application - username pair and a pentaho username.
	 * @param app The name of the external application.
	 * @param externalUsername The external username.
	 * @param pentahoUser The Pentaho username that is mapped to the pair {@code (app, externalUsername)}.
	 */
	public void addMapping(String app, String externalUsername, String pentahoUser)
	{
		ApplicationMappings existentMapping = getApplicationsMap().get(app);
		if(existentMapping == null)
		{
			existentMapping = new ApplicationMappings(app);
			getApplicationsMap().put(app, existentMapping);
		}
		
		existentMapping.getUsernamesMap().put(externalUsername, pentahoUser);
	}

	/**
	 * Removes a mapping between a {@code (app, externalUsername)} pair and a Pentaho username, if it exists.
	 * @param app The external application name.
	 * @param externalUsername The external username.
	 * @return The Pentaho username removed from the mapping, if any. {@code null} otherwise.
	 */
	public String removeMapping(String app, String externalUsername)
	{

		return null;
		
	}

	public String toJson() throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String json = mapper.writeValueAsString(this.getApplicationsMap());
		return json;
	}

}
