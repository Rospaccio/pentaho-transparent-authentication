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

### Usage
An external application can request a login ticket to pentaho with a URL of the following form:

    http://<pentho-base>/pentaho/Login?generate-ticket=1&app=test&username=admin

The request must carry 3 parameters:

1. `generate-ticket` => tells the `LoginTicketFilterGenerator` to issue a login ticket and register it in the internal cache managed by the **pentaho-authentication-ext** plugin. The ticket is return in the body of the response as JSON with the following format:

    {"ticketId": "199817ae-20bf-41e1-8548-a366f99e2377"}

2. `app`: the arbitrary name of the application requesting the login ticket. The name must be known to **pentaho-authentication-ext** i.e. it must be present in the current configuration of the plugin.

3. `username`: the username of the user to log in. It must be mapped to a pentaho username in the internal configuration of **pentaho-authentication-ext**.

Once the ticket is issued, it is valid for a certain amount of time and it can be used by a request sent by the user's browser to flawlessly  log in to pentaho.
