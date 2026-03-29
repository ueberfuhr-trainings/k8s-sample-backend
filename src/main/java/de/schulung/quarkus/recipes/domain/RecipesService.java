package de.schulung.quarkus.recipes.domain;

import de.schulung.quarkus.recipes.domain.events.RecipeCreatedEvent;
import de.schulung.quarkus.recipes.domain.model.Difficulty;
import de.schulung.quarkus.recipes.domain.model.Recipe;
import de.schulung.quarkus.recipes.shared.interceptors.FireEvent;
import de.schulung.quarkus.recipes.shared.interceptors.LogPerformance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
@RequiredArgsConstructor
public class RecipesService {

  private final RecipesDao dao;

  @LogPerformance(Logger.Level.DEBUG)
  public Stream<Recipe> findAll() {
    return dao.findAll();
  }

  public Optional<Recipe> findById(String id) {
    return dao.findById(id);
  }

  @LogPerformance(Logger.Level.DEBUG)
  @FireEvent(RecipeCreatedEvent.class)
  public void create(
    @Valid
    Recipe recipe
  ) {
    recipe.setLastEdited(OffsetDateTime.now());

    if (null == recipe.getImg()) {
      recipe.setImg("/recipe_pictures/default.jpg");
    }

    if (null == recipe.getDifficulty()) {
      recipe.setDifficulty(Difficulty.MEDIUM);
    }

    dao.save(recipe);

  }
}
