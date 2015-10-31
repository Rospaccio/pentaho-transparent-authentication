## PentahoTransparentAuthentication [![Build Status](https://travis-ci.org/Rospaccio/pentaho-transparent-authentication.svg?branch=master)](https://travis-ci.org/Rospaccio/pentaho-transparent-authentication)

Pentaho Transparent Authentication is a plugin for Pentaho BA Server that provides the platform with additional authentication and login capabilities, in the form of a spring security filter and a bunch of support classes. The goal of this project is to provide a transparent authentication and autologin mechanism, to let users switch between an external web application and Pentaho in an “Single Sign On" fashion.

### Learn more
Coauthor [Francesco Corti](http://fcorti.com) has written [an excelent guide](http://fcorti.com/pentaho-transparent-authentication/) on how
to install, use and test the plugin. Check it out!

### How to build it yourself
Clone the project and just `mvn package`.

The command will produce a file named **PentahoTransparentAuthentication-&lt;version>.zip**
in `pentaho-transparent-authentication-plugin/target`. This is effectively the artifact that is downloaded
from the Pentaho Marketplace.

To manually install the plugin (to allow local development or testing),
you can simply unpack the file to the system directory of Pentaho. In the project
there is also an utility Ant file that will do that for you: **dev-build.xml**.

Just modify the properties in the **dev-build.properties** file and run the target named
`deploy-unpack`.


### Authors
Alberto Mercati and Francesco Corti
