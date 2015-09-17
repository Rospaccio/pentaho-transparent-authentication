package org.merka.pentaho.ext.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.merka.pentaho.ext.exception.MappingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/web-test-context.xml" })
public class InMemoryUsernameProviderTest
{
	public static final String APP = "testApp";
	public static final String EXTERNAL_USER = "testUser";
	public static final String PENTAHO_USER = "pentahoUser";

	@Autowired
	private InMemoryUsernameProvider inMemoryProvider;

	@Test
	public void testGetUsername()
	{
		inMemoryProvider.addMapping(APP, EXTERNAL_USER, PENTAHO_USER);
		String username = inMemoryProvider.getUsername(APP, EXTERNAL_USER);
		assertEquals(PENTAHO_USER, username);
	}

	@Test(expected = MappingNotFoundException.class)
	public void testGetUsernameNotFound()
	{
		inMemoryProvider.getUsername(APP, "iDoNotExist");
	}

	@Test
	public void testToJson() throws IOException
	{
		inMemoryProvider.addMapping("app0", "user0.0", "pentaho0");
		inMemoryProvider.addMapping("app0", "user0.1", "pentaho1");
		inMemoryProvider.addMapping("app0", "user0.2", "pentaho2");
		inMemoryProvider.addMapping("app1", "user0.0", "pentaho3");
		inMemoryProvider.addMapping("app1", "user0.1", "pentaho4");

		String json = inMemoryProvider.toJson();
		System.out.println(json);
		ObjectMapper mapper = new ObjectMapper();
		Object result = mapper.readValue(json, new TypeReference <Map<String, ApplicationMappings>>()
		{
		});
		assertNotNull(result);
		@SuppressWarnings("unchecked")
		Map<String, ApplicationMappings> maps = (Map<String, ApplicationMappings>) result;
		InMemoryUsernameProvider checkProvider = new InMemoryUsernameProvider();
		checkProvider.setApplicationsMap(maps);
		
		String pentahoUser = checkProvider.getUsername("app0", "user0.0");
		assertNotNull(pentahoUser);
		assertEquals("pentaho0", pentahoUser);
		
		pentahoUser = checkProvider.getUsername("app1", "user0.1");
		assertNotNull(pentahoUser);
		assertEquals("pentaho4", pentahoUser);
	}

	@Test
	public void testLoadJsonMappings() throws JsonParseException, JsonMappingException, IOException
	{
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("mappings.json");
		assertNotNull(stream);
		
		InMemoryUsernameProvider provider = new InMemoryUsernameProvider();
		provider.loadJsonMappings(stream);
		
		String pentahoUser = provider.getUsername("showcase", "user0.3");
		assertNotNull(pentahoUser);
		assertEquals("tiffany", pentahoUser);
	}
	
	@Test
	public void testLoadJsonMappingsFromFile() throws JsonParseException, JsonMappingException, IOException
	{
		String filename = "mappings.json";
		inMemoryProvider.loadJsonMappingsFromFile(filename);
		
		String pentahoUser = inMemoryProvider.getUsername("showcase", "user0.3");
		assertNotNull(pentahoUser);
		assertEquals("tiffany", pentahoUser);
	}
	
}
