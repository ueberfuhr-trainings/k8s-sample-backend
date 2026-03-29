package de.schulung.quarkus.recipes.boundary.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RecipeDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String id;
  @NotNull
  @Size(min = 1, max = 100)
  private String name;
  @ValidUrl
  private String img;
  @Min(1)
  private int servings;
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private OffsetDateTime lastEdited;
  @Min(1)
  private int duration;
  private DifficultyDto difficulty;
  @NotNull
  @Size(max = 100)
  private List<@Valid @NotNull IngredientDto> ingredients;
  @NotNull
  @Size(max = 2000)
  private String preparation;

}
