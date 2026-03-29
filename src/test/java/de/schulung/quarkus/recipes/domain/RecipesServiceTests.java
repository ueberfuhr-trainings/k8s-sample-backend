package de.schulung.quarkus.recipes.domain;

import de.schulung.quarkus.recipes.domain.model.Recipe;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@TestTransaction
public class RecipesServiceTests {

  @Inject
  RecipesService service;

  @Test
  void shouldValidateRecipe() {
    final var recipe = new Recipe();

    assertThrows(
      Exception.class,
      () -> service.create(recipe)
    );

  }

}
