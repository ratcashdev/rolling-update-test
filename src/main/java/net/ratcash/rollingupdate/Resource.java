/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ratcash.rollingupdate;

import com.airhacks.porcupine.execution.boundary.Dedicated;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("test")
public class Resource {
	@EJB
	Teacher teacher;
	
	@Inject
	Configuration configuration;
	
	@Inject
	SessionDataHolder sessionHolder;
	
	@Inject
    @Dedicated
    ExecutorService heavy;
	
	
	@GET
	@Path("subject")
	public String getSubject() {
		return teacher.getSubject();
	}
	
	@GET
	@Path("experiment")
	public String getExperimentResult() {
		return teacher.getExperimentResult();
	}
	
	@POST
	@Path("config")
	public String configuration(String name) {
		return configuration.getConfig(name);
	}
	
	@GET
	@Path("temperature")
	public int getTemperature() {
		if(sessionHolder.getTemperature() == 0)
			System.out.println("Session lost!");
		return sessionHolder.getAndChage();
	}
	
	@GET
	@Path("complex")
	public String complex(@QueryParam("name") String name) {
		return String.format("The %s teacher's laborant had a %s experiment with the following config: {%s} and temperature: %d", 
				teacher.getSubject(), teacher.getExperimentResult(), configuration.getConfig(name), getTemperature());
	}
	
	@GET
	@Path("complexAsync")
	public void complexAsync(@QueryParam("name") String name,  @Suspended AsyncResponse response) {
		response.setTimeout(3, TimeUnit.SECONDS);
		final int temp = getTemperature();
		CompletableFuture.supplyAsync(() -> {
			return String.format("The %s teacher's laborant had a %s experiment with the following config: {%s} and temperature: %d", 
				teacher.getSubject(), teacher.getExperimentResult(), configuration.getConfig(name), temp);}, heavy)
				.thenAccept(response::resume)
				.exceptionally((t) -> {
					t.printStackTrace();
                    response.resume(t.getMessage());
					return null;
				});
	}
	
}
