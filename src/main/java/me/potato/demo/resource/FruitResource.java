package me.potato.demo.resource;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.MultiOnItem;
import me.potato.demo.model.Fruit;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Path("/fruits")
public class FruitResource {
  private Set<Fruit> fruits=Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

  public FruitResource() {
    fruits.add(new Fruit("Apple", "Winter fruit"));
    fruits.add(new Fruit("PineApple", "Tropical fruit"));
  }

  @GET
  public Set<Fruit> list() {
    return fruits;
  }

  @POST
  public Set<Fruit> add(Fruit newFruit) {
    fruits.add(newFruit);
    return fruits;
  }

  @DELETE
  public Set<Fruit> delete(Fruit fruit) {
    fruits.removeIf(existingFruit -> existingFruit.getName()
                                                  .contentEquals(fruit.getName()));
    return fruits;
  }

  @GET
  @Path("/{name}")
  public Uni<Response> getOne(@PathParam String name) {
    var filtered=fruits.stream()
                       .filter(item -> item.getName()
                                           .equals(name))
                       .collect(Collectors.toList());
    if(filtered.size() == 0)
      return Uni.createFrom()
                .item(Response.noContent()
                              .build());

    return Uni.createFrom()
              .item(Response.ok(filtered.get(0))
                            .build());
  }

  @GET
  @Path("/multi")
  public Multi<Fruit> getAll() {
    return Multi.createFrom()
                .items(fruits.stream());
  }

}
