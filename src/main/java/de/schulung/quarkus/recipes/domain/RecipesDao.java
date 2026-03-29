package de.schulung.quarkus.recipes.domain;

import de.schulung.quarkus.recipes.domain.model.Recipe;

import java.util.Optional;
import java.util.stream.Stream;

public interface RecipesDao {

  Stream<Recipe> findAll();

  default Optional<Recipe> findById(String id) {
    return findAll()
      .filter(r -> id.equals(r.getId()))
      .findFirst();
  }

  void save(Recipe recipe);
}
