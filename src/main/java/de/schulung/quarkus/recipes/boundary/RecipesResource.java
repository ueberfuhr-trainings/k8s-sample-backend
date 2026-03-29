package de.schulung.quarkus.recipes.boundary;

import de.schulung.quarkus.recipes.boundary.mappers.RecipeDtoMapper;
import de.schulung.quarkus.recipes.boundary.model.RecipeDto;
import de.schulung.quarkus.recipes.domain.RecipesService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class RecipesResource {

  private final RecipesService service;
  private final RecipeDtoMapper mapper;

  @GET
  public Collection<RecipeDto> getAllRecipes() {
    return service
      .findAll()
      .map(mapper::map)
      .toList();
  }

  @POST
  public Response createRecipe(
    @Valid
    RecipeDto recipeDto,
    @Context
    UriInfo uriInfo
  ) {
    var recipe = mapper.map(recipeDto);
    service.create(recipe);
    final var location = uriInfo
      .getAbsolutePathBuilder()
      .path(recipe.getId())
      .build();
    return Response
      .created(location)
      .entity(mapper.map(recipe))
      .build();
  }

  @GET
  @Path("/{id}")
  public RecipeDto getRecipe(
    @PathParam("id")
    String id
  ) {
    return service
      .findById(id)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }
}
