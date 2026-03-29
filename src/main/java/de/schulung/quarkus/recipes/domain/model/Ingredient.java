package de.schulung.quarkus.recipes.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient {

  /**
   * The ingredient name.
   */
  @NotNull
  @Size(min = 1, max = 100)
  private String name;
  /**
   * The quantity of the ingredient.
   */
  @Positive
  private double quantity;
  /**
   * The unit of the ingredient.
   */
  @NotNull
  private IngredientUnit unit;

}
