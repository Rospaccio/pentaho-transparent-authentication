## pentaho-authentication-ext [![Build Status](https://travis-ci.org/Rospaccio/pentaho-authentication-ext.svg?branch=pentaho-5.4)](https://travis-ci.org/Rospaccio/pentaho-authentication-ext)
### Authentication extension mechanism for Pentaho Business Intelligence Server

**pentaho-authentication-ext** is a plugin for Pentaho BI Server that provides the platform with additional
authentication and login capabilities, in the form of a
spring security filter and a bunch of support classes. The goal of this project is to provide a transparent authentication and autologin
mechanism to let users switch between a different web application and Pentaho in an
 "Single Sign On fashion". The main focus is  the interaction
between Alfresco and Pentaho through the AAAR solution.

### Setup (manual process, installer is yet to come...)

1. Edit `pentaho-spring-beans.xml` in `$PENTAHO-ROOT/pentaho-solutions/system` and add
the line highlighted below to add the extension authentication beans in the Spring context:

		...
		<!-- fragment of pentaho-spring-beans.xml -->
		<import resource="pentahoSystemConfig.xml" />
		<import resource="adminPlugins.xml" />
		<import resource="systemListeners.xml" />
		<import resource="repository.spring.xml" />
		<import resource="applicationContext-spring-security.xml" />

		<!-- Addition for pentaho-authentication-ext - Start. -->
		<import resource="pentaho-authentication-ext.xml" />
		<!-- Addition for pentaho-authentication-ext - End. -->

		<!-- fragment of pentaho-spring-beans.xml -->
		<import resource="applicationContext-spring-security-superuser.xml" />  
		<import resource="applicationContext-pentaho-security-superuser.xml" />

		<import resource="applicationContext-common-authorization.xml" />
		<import resource="applicationContext-spring-security-memory.xml" />
		...

2. Copy `pentaho-authentication-ext.properties` and `pentaho-authentication-ext.xml` to
`$PENTAHO-ROOT/pentaho-solutions/system`.

3. Copy `pentaho-authentication-ext-<version>.jar` to `tomcat\webapps\pentaho\WEB-INF\lib`.
4. Restart Pentaho BI Server.

### Usage
An external application can request a login ticket to pentaho with a URL of the following form:

      http://<pentaho-base>/pentaho/Login?generate-ticket=1&app=test&username=admin

The request must carry 3 parameters:

1. `generate-ticket`: tells the login ticket generator to issue a login ticket and register it in the internal cache managed by the **pentaho-authentication-ext** plugin. The ticket is return in the body of the response as JSON with the following format:

        {"ticketId": "199817ae-20bf-41e1-8548-a366f99e2377"}

2. `app`: the arbitrary name of the application requesting the login ticket. The name must be known to **pentaho-authentication-ext** i.e. it must be present in the current configuration of the plugin.

3. `username`: the username of the user to log in. It must be mapped to a pentaho username in the internal configuration of **pentaho-authentication-ext**.

Once the external app has received the ticket, it can send a redirect response to the user's browser with a redirect URL in the following form:

      http://<pentaho-base>/pentaho/Home?autologin=true&ticket=857495f2-cd2c-4638-baf0-de71aacce714

Once the ticket is issued, it is valid for a certain amount of time (configurable in `pentaho-authentication-ext.properties`) and it can be used by a request sent by the user's browser to flawlessly log in to pentaho.

### Configuration
The external applications and the corresponding users that are allowed to use the mechanism can be configured in a file called mapping.json that must be placed in $PENTAHO-ROOT/pentaho-solutions/system. The file must comply to the following format:

		{
		    "testApp": {
		        "usernamesMap": {
		            "testUser": "pentahoUser"
		        },
		        "applicationName": "testApp"
		    },
		    "app1": {
		        "usernamesMap": {
		            "user0.1": "pentaho4",
		            "user0.0": "pentaho3"
		        },
		        "applicationName": "app1"
		    },
		    "showcase": {
		        "usernamesMap": {
		            "user0.2": "admin",
		            "user0.1": "pat",
		            "user0.0": "suzy",
		            "user0.3": "tiffany"
		        },
		        "applicationName": "showcase"
		    }
		}  

For security reasons, any request for a login ticket must respect some form of mutual authentication between the two peers involved (the "external app" and Pentaho). Currently, for this purpose, it is assumed that request parameter authentication is enabled in Pentaho.
Every request for a ticket must carry the authentication parameters, so the complete format should be something like the following:

    http://<pentaho-base>/pentaho/Login?userid=admin&password=password&generate-ticket=1&app=showcase&username=user0.2


### Compatibility
pentaho-authentication-ext is currently compatible with Pentaho BI v. 5.4 and 6 (preview release).
