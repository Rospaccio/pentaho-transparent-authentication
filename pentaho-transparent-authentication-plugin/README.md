## PentahoTransparentAuthentication plugin

This project contains the source files of the PentahoTransparentAuthentication plugin
for Pentaho

### How to build it yourself

First, clone and `mvn install` **pentaho-authentication-ext**. The related artifact must be
in your local `m2` for the current build to work.

Clone **pentaho-transparent-authentication-plugin** in a folder placed at the same level
of **pentaho-authentication-ext**. Doing so you will be able to build the project
without renaiming the relative path property `java-lib-project`.

Now execute:

    mvn package

The build will produce a file named **PentahoTransparentAuthentication-&lt;version>.zip**
in the target directory. This is effectively the final artifact that is downloaded
when you install the plugin from the Pentaho Marketplace.

For a local development,
you can simply unpack the file to the system directory of Pentaho. In the project
there also is an utility Ant file that will do that for you: **dev-build.xml**.

Just modify the properties in the **dev-build.properties** file and run the target named
`deploy-unpack`.

#### Future development
In the near future this project will be probably moved to a multi module Maven project,
in order to avoid the file system dependency to pentaho-authentication-ext. Which
 is kinda ugly, I know.
