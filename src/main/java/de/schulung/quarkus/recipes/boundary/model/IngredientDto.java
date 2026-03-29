package de.schulung.quarkus.recipes.boundary.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDto {

  @NotNull
  @Size(min = 1, max = 100)
  private String name;
  @Positive
  private double quantity;
  @NotNull
  private IngredientUnitDto unit;

}
