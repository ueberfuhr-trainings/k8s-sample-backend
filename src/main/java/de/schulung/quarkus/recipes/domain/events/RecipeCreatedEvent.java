package de.schulung.quarkus.recipes.domain.events;

import de.schulung.quarkus.recipes.domain.model.Recipe;

public record RecipeCreatedEvent(
  Recipe recipe
) implements RecipeEvent {
}
