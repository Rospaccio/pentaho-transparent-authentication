package org.merka.pentaho.ext.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.pentaho.platform.web.http.api.resources.AbstractJaxRSResource;

@Path( "/paext/" )
public class ProbeService extends AbstractJaxRSResource
{
	@GET
	@Path("/ping")
	@Produces({MediaType.TEXT_PLAIN})
	public Response doPing()
	{
		ResponseBuilder pingResponse = Response.ok();
		return pingResponse.build();
	}
}
