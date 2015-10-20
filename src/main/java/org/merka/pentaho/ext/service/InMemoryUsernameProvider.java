package org.merka.pentaho.ext.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.merka.pentaho.ext.exception.MappingNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryUsernameProvider implements UsernameProvider
{
	private static final Logger logger = LoggerFactory.getLogger(InMemoryUsernameProvider.class);

	public String initFileLocation;

	public String getInitFileLocation()
	{
		return initFileLocation;
	}

	public void setInitFileLocation(String initFileLocation)
	{
		this.initFileLocation = initFileLocation;
	}

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
		if (existentMapping == null)
		{
			throw new MappingNotFoundException(
					"Application named '" + externalApplicationName + "' is not present in the current mappings");
		}

		String pentahoUsername = existentMapping.getUsernamesMap().get(externalUsername);
		if (pentahoUsername == null)
		{
			throw new MappingNotFoundException("External username '" + externalUsername
					+ "' not found in the mappings for application '" + externalApplicationName + "'");
		}

		return pentahoUsername;
	}

	/**
	 * Adds a mapping between an external application - username pair and a
	 * pentaho username.
	 * 
	 * @param app
	 *            The name of the external application.
	 * @param externalUsername
	 *            The external username.
	 * @param pentahoUser
	 *            The Pentaho username that is mapped to the pair
	 *            {@code (app, externalUsername)}.
	 */
	public void addMapping(String app, String externalUsername, String pentahoUser)
	{
		ApplicationMappings existentMapping = getApplicationsMap().get(app);
		if (existentMapping == null)
		{
			existentMapping = new ApplicationMappings(app);
			getApplicationsMap().put(app, existentMapping);
		}

		existentMapping.getUsernamesMap().put(externalUsername, pentahoUser);
	}

	/**
	 * Removes a mapping between a {@code (app, externalUsername)} pair and a
	 * Pentaho username, if it exists.
	 * 
	 * @param app
	 *            The external application name.
	 * @param externalUsername
	 *            The external username.
	 * @return The Pentaho username removed from the mapping, if any.
	 *         {@code null} otherwise.
	 */
	public String removeMapping(String app, String externalUsername)
	{
		throw new IllegalStateException("Not implemented!");
	}

	public String toJson() throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String json = mapper.writeValueAsString(this.getApplicationsMap());
		return json;
	}

	public void loadJsonMappings(String json) throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, ApplicationMappings> maps = (Map<String, ApplicationMappings>) mapper.readValue(json,
				new TypeReference<Map<String, ApplicationMappings>>()
				{
				});
		setApplicationsMap(maps);
	}

	public void loadJsonMappings(InputStream stream) throws JsonParseException, JsonMappingException, IOException
	{
		try
		{
			int length = 256;
			char[] buffer = new char[length];
			InputStreamReader reader = new InputStreamReader(stream);
			int readCount = -1;
			int offset = 0;
			StringBuilder builder = new StringBuilder();
			do
			{
				readCount = reader.read(buffer, offset, length);
				builder.append(buffer, offset, readCount);
			}
			while (readCount == length);

			logger.info("json = " + builder);
			loadJsonMappings(builder.toString());
		}
		finally
		{
			if (stream != null)
			{
				stream.close();
			}
		}
	}

	public void initFromConfiguration() throws JsonParseException, JsonMappingException, IOException
	{
		logger.info("\n\n\n" + getInitFileLocation() + "\n\n\n");
		loadJsonMappingsFromFile(getInitFileLocation());		
	}

	public void loadJsonMappingsFromFile(String fileName) throws JsonParseException, JsonMappingException, IOException
	{
		String text = null;
		try
		{
			File file = new File(fileName);
			text = FileUtils.readFileToString(file);
		}
		catch (Exception e)
		{
			logger.error("Error", e);
			//TODO: decide how to manage this exception
			throw e;
		}
		loadJsonMappings(text);
	}

	@Override
	public boolean isAppNameMapped(String externalAppName)
	{
		if(StringUtils.isBlank(externalAppName))
		{
			return false;
		}
		
		ApplicationMappings mapping = getApplicationsMap().get(externalAppName);
		return mapping != null;
	}
}
