package de.schulung.quarkus.recipes.domain.model;

import de.schulung.quarkus.recipes.shared.validation.ValidUrl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class Recipe {

  /**
   * The recipe ID (read-only, assigned by the server).
   */
  private String id;
  /**
   * The recipe name.
   */
  @NotNull
  @Size(min = 1, max = 100)
  private String name;
  /**
   * URL of the recipe image, absolute or relative.
   */
  @ValidUrl
  private String img;
  /**
   * The number of servings.
   */
  @Min(1)
  private int servings;
  /**
   * The last edited timestamp (read-only, assigned by the server).
   */
  private OffsetDateTime lastEdited;
  /**
   * Preparation time in minutes.
   */
  @Min(1)
  private int duration;
  /**
   * Difficulty level of the recipe.
   */
  @NotNull
  private Difficulty difficulty = Difficulty.MEDIUM;
  /**
   * List of ingredients.
   */
  @NotNull
  @Size(max = 100)
  private List<@Valid @NotNull Ingredient> ingredients;
  /**
   * Preparation instructions.
   */
  @NotNull
  @Size(max = 2000)
  private String preparation;

}
