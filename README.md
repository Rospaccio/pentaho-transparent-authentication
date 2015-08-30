## pentaho-authentication-ext
### Authentication extension mechanism for Penthao Business Intelligence Server

**pentaho-authentication-ext** is a plugin for Pentaho BI Server that provides the platform with additional
authentication and login capabilities, in the form of a
spring security filter and a bunch of support classes. The goal of this project is to provide a transparent authentication and autologin
mechanism to let users switch between a different web application and Pentaho in an
 "Single Sign On fashion". The main focus is  the interaction
between Alfresco and Pentaho through the AAAR solution.

### Setup (manual process, installer is yet to come...)

1. Edit `pentaho-spring-beans.xml` in `$PENTAHO-ROOT/pentaho-solutions/system` and add
the line hilighted below to add the extension authentication beans in the Spring context:


		<!-- fragment of pentaho-spring-beans.xml -->
		<import resource="pentahoSystemConfig.xml" />
		<import resource="adminPlugins.xml" />
		<import resource="systemListeners.xml" />
		<import resource="repository.spring.xml" />
		<import resource="applicationContext-spring-security.xml" />
		
		<!-- Autologin support through pentaho-authentication-ext -->
		<import resource="pentaho-authentication-ext.xml" />
		
		<import resource="applicationContext-spring-security-superuser.xml" />  
		<import resource="applicationContext-pentaho-security-superuser.xml" />
		
		<import resource="applicationContext-common-authorization.xml" />
		<import resource="applicationContext-spring-security-memory.xml" />

2. Copy `pentaho-authentication-ext.properties` and `pentaho-authentication-ext.xml` to
`$PENTAHO-ROOT/pentaho-solutions/system`.

3. Copy `pentaho-authentication-ext-<version>.jar` to `tomcat\webapps\pentaho\WEB-INF\lib`.
4. Restart Pentaho BI Server.
