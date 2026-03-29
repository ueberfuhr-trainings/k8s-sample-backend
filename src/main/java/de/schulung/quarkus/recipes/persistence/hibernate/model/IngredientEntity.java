package de.schulung.quarkus.recipes.persistence.hibernate.model;

import de.schulung.quarkus.recipes.domain.model.IngredientUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Ingredient")
@Table(name = "ingredients")
@Getter
@Setter
public class IngredientEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @Size(min = 1, max = 100)
  private String name;
  @Positive
  private double quantity;
  @NotNull
  private IngredientUnit unit;

}
