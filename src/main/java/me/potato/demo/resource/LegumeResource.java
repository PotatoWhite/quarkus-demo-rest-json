package me.potato.demo.resource;

import me.potato.demo.model.Legume;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/legumes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LegumeResource {
  private Set<Legume> legumes=Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

  public LegumeResource() {
    legumes.add(new Legume("Carrot", "Root Vegetable"));
    legumes.add(new Legume("Zucchini", "Summer squash"));
  }

  @GET
  public Response list() {
    return Response.ok(legumes)
                   .build();
  }

}
