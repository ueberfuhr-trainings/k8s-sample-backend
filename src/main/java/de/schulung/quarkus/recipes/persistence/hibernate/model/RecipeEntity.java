package de.schulung.quarkus.recipes.persistence.hibernate.model;

import de.schulung.quarkus.recipes.domain.model.Difficulty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity(name = "Recipe")
@Table(name = "recipes")
@Getter
@Setter
public class RecipeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @NotNull
  @Size(min = 1, max = 100)
  private String name;
  private String img;
  @Min(1)
  private int servings;
  private OffsetDateTime lastEdited;
  @Min(1)
  private int duration;
  private Difficulty difficulty;
  @NotNull
  @Size(max = 100)
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "recipe_id")
  private List<@Valid @NotNull IngredientEntity> ingredients;
  @NotNull
  @Size(max = 2000)
  @Column(length = 2000)
  private String preparation;

}
