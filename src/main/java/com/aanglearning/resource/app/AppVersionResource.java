package com.aanglearning.resource.app;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aanglearning.authentication.Secured;
import com.aanglearning.model.app.AppVersion;
import com.aanglearning.service.app.AppVersionService;

@Path("/appversion")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppVersionResource {
	
	AppVersionService service = new AppVersionService();
	
	@Secured
	@GET
	public List<AppVersion> getAppVersions() {
		return service.getAppVersions();
	}
	
	@Secured
	@GET
	@Path("{versionId}/{appName}")
	public AppVersion getAppVersion(@PathParam("versionId") int versionId,
			@PathParam("appName") String appName) {
		return service.getAppVersion(versionId, appName);
	}
	
	@Secured
	@POST
	public void add(AppVersion appVersion) {
		service.add(appVersion);
	}
	
	@Secured
	@PUT
	public void update(AppVersion appVersion) {
		service.update(appVersion);
	}

	@Secured
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") int id) {
		service.delete(id);
	}

}
