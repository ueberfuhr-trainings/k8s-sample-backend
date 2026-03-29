package de.schulung.quarkus.recipes.boundary.mappers;

import de.schulung.quarkus.recipes.boundary.model.DifficultyDto;
import de.schulung.quarkus.recipes.boundary.model.RecipeDto;
import de.schulung.quarkus.recipes.domain.model.Difficulty;
import de.schulung.quarkus.recipes.domain.model.Recipe;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class RecipeDtoMapper {

  private final IngredientDtoMapper ingredientMapper;

  public RecipeDto map(Recipe source) {
    var target = new RecipeDto();
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
      target.setDifficulty(mapDifficulty(source.getDifficulty()));
    }
    if (source.getIngredients() != null) {
      target.setIngredients(source.getIngredients().stream().map(ingredientMapper::map).toList());
    }
    if (source.getPreparation() != null) {
      target.setPreparation(source.getPreparation());
    }
    return target;
  }

  public Recipe map(RecipeDto source) {
    var target = new Recipe();
    if (source.getName() != null) {
      target.setName(source.getName());
    }
    if (source.getImg() != null) {
      target.setImg(source.getImg());
    }
    target.setServings(source.getServings());
    target.setDuration(source.getDuration());
    if (source.getDifficulty() != null) {
      target.setDifficulty(mapDifficulty(source.getDifficulty()));
    }
    if (source.getIngredients() != null) {
      target.setIngredients(source.getIngredients().stream().map(ingredientMapper::map).toList());
    }
    if (source.getPreparation() != null) {
      target.setPreparation(source.getPreparation());
    }
    return target;
  }

  public DifficultyDto mapDifficulty(Difficulty source) {
    if (source == null) {
      return null;
    }
    return switch (source) {
      case EASY -> DifficultyDto.easy;
      case MEDIUM -> DifficultyDto.medium;
      case HARD -> DifficultyDto.hard;
    };
  }

  public Difficulty mapDifficulty(DifficultyDto source) {
    if (source == null) {
      return null;
    }
    return switch (source) {
      case easy -> Difficulty.EASY;
      case medium -> Difficulty.MEDIUM;
      case hard -> Difficulty.HARD;
    };
  }

}
