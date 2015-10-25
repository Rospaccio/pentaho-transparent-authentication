## PentahoTransparentAuthentication

Pentaho Transparent Authentication is a plugin for Pentaho BA Server that provides the platform with additional authentication and login capabilities, in the form of a spring security filter and a bunch of support classes. The goal of this project is to provide a transparent authentication and autologin mechanism, to let users switch between an external web application and Pentaho in an “Single Sign On" fashion.

### Learn more
Coauthor [Francesco Corti](http://fcorti.com) has written [an excelent guide](http://fcorti.com/pentaho-transparent-authentication/) on how
to install, use and test the plugin. Check it out!

### How to build it yourself
Clone the project and just `mvn package`. The command produces a zip file in `pentaho-transparent-authentication-plugin/target` that can be unzipped to the
`pentaho-solutions/system` folder of your Pentaho instance
