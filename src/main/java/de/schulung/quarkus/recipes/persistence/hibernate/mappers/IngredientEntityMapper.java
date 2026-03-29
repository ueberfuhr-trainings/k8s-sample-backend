package de.schulung.quarkus.recipes.persistence.hibernate.mappers;

import de.schulung.quarkus.recipes.domain.model.Ingredient;
import de.schulung.quarkus.recipes.persistence.hibernate.model.IngredientEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IngredientEntityMapper {

  public Ingredient map(IngredientEntity source) {
    var target = new Ingredient();
    if (source.getName() != null) {
      target.setName(source.getName());
    }
    target.setQuantity(source.getQuantity());
    if (source.getUnit() != null) {
      target.setUnit(source.getUnit());
    }
    return target;
  }

  public IngredientEntity map(Ingredient source) {
    var target = new IngredientEntity();
    if (source.getName() != null) {
      target.setName(source.getName());
    }
    target.setQuantity(source.getQuantity());
    if (source.getUnit() != null) {
      target.setUnit(source.getUnit());
    }
    return target;
  }

}
