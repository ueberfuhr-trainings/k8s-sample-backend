package de.schulung.quarkus.recipes.persistence.hibernate.mappers;

import de.schulung.quarkus.recipes.domain.model.Recipe;
import de.schulung.quarkus.recipes.persistence.hibernate.model.RecipeEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class RecipeEntityMapper {

  private final IngredientEntityMapper ingredientMapper;

  public Recipe map(RecipeEntity source) {
    var target = new Recipe();
    if (source.getId() != null) {
      target.setId(source.getId());
    }
    if (source.getName() != null) {
      target.setName(source.getName());
    }
    if (source.getImg() != null) {
      target.setImg(source.getImg());
    }
    target.setServings(source.getServings());
    if (source.getLastEdited() != null) {
      target.setLastEdited(source.getLastEdited());
    }
    target.setDuration(source.getDuration());
    if (source.getDifficulty() != null) {
      target.setDifficulty(source.getDifficulty());
    }
    if (source.getIngredients() != null) {
      target.setIngredients(source.getIngredients().stream().map(ingredientMapper::map).toList());
    }
    if (source.getPreparation() != null) {
      target.setPreparation(source.getPreparation());
    }
    return target;
  }

  public RecipeEntity map(Recipe source) {
    var target = new RecipeEntity();
    if (source.getId() != null) {
      target.setId(source.getId());
    }
    if (source.getName() != null) {
      target.setName(source.getName());
    }
    if (source.getImg() != null) {
      target.setImg(source.getImg());
    }
    target.setServings(source.getServings());
    if (source.getLastEdited() != null) {
      target.setLastEdited(source.getLastEdited());
    }
    target.setDuration(source.getDuration());
    if (source.getDifficulty() != null) {
      target.setDifficulty(source.getDifficulty());
    }
    if (source.getIngredients() != null) {
      target.setIngredients(source.getIngredients().stream().map(ingredientMapper::map).toList());
    }
    if (source.getPreparation() != null) {
      target.setPreparation(source.getPreparation());
    }
    return target;
  }

  public void copy(RecipeEntity source, Recipe target) {
    if (source.getId() != null) {
      target.setId(source.getId());
    }
    if (source.getName() != null) {
      target.setName(source.getName());
    }
    if (source.getImg() != null) {
      target.setImg(source.getImg());
    }
    target.setServings(source.getServings());
    if (source.getLastEdited() != null) {
      target.setLastEdited(source.getLastEdited());
    }
    target.setDuration(source.getDuration());
    if (source.getDifficulty() != null) {
      target.setDifficulty(source.getDifficulty());
    }
    if (source.getIngredients() != null) {
      target.setIngredients(source.getIngredients().stream().map(ingredientMapper::map).toList());
    }
    if (source.getPreparation() != null) {
      target.setPreparation(source.getPreparation());
    }
  }

}
