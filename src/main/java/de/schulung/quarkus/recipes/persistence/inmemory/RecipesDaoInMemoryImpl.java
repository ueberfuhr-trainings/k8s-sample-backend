package de.schulung.quarkus.recipes.persistence.inmemory;

import de.schulung.quarkus.recipes.domain.RecipesDao;
import de.schulung.quarkus.recipes.domain.model.Recipe;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@ApplicationScoped
@Typed(RecipesDao.class)
@DefaultBean
public class RecipesDaoInMemoryImpl implements RecipesDao {

  private final Map<String, Recipe> recipes = new ConcurrentHashMap<>();

  @Override
  public Stream<Recipe> findAll() {
    return recipes
      .values()
      .stream();
  }

  @Override
  public void save(Recipe recipe) {
    if (recipe.getId() == null) {
      recipe.setId(UUID.randomUUID().toString());
    }
    recipes.put(recipe.getId(), recipe);
  }
}
