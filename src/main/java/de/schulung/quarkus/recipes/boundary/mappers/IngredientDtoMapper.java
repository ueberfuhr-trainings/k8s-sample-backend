package de.schulung.quarkus.recipes.boundary.mappers;

import de.schulung.quarkus.recipes.boundary.model.IngredientDto;
import de.schulung.quarkus.recipes.boundary.model.IngredientUnitDto;
import de.schulung.quarkus.recipes.domain.model.Ingredient;
import de.schulung.quarkus.recipes.domain.model.IngredientUnit;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IngredientDtoMapper {

  public IngredientDto map(Ingredient source) {
    var target = new IngredientDto();
    if (source.getName() != null) {
      target.setName(source.getName());
    }
    target.setQuantity(source.getQuantity());
    if (source.getUnit() != null) {
      target.setUnit(mapIngredientUnit(source.getUnit()));
    }
    return target;
  }

  public Ingredient map(IngredientDto source) {
    var target = new Ingredient();
    if (source.getName() != null) {
      target.setName(source.getName());
    }
    target.setQuantity(source.getQuantity());
    if (source.getUnit() != null) {
      target.setUnit(mapIngredientUnit(source.getUnit()));
    }
    return target;
  }

  public IngredientUnitDto mapIngredientUnit(IngredientUnit source) {
    if (source == null) {
      return null;
    }
    return switch (source) {
      case PIECES -> IngredientUnitDto.pieces;
      case GRAMS -> IngredientUnitDto.grams;
      case KILOGRAMS -> IngredientUnitDto.kilograms;
      case MILLILITERS -> IngredientUnitDto.milliliters;
      case LITERS -> IngredientUnitDto.liters;
      case TEASPOONS -> IngredientUnitDto.teaspoons;
      case TABLESPOONS -> IngredientUnitDto.tablespoons;
      case CUPS -> IngredientUnitDto.cups;
      case CLOVES -> IngredientUnitDto.cloves;
      case PINCHES -> IngredientUnitDto.pinches;
      case PACKAGES -> IngredientUnitDto.packages;
      case CANS -> IngredientUnitDto.cans;
      case BOTTLES -> IngredientUnitDto.bottles;
      case SLICES -> IngredientUnitDto.slices;
      case SPRIGS -> IngredientUnitDto.sprigs;
      case STALKS -> IngredientUnitDto.stalks;
      case CUBES -> IngredientUnitDto.cubes;
    };
  }

  public IngredientUnit mapIngredientUnit(IngredientUnitDto source) {
    if (source == null) {
      return null;
    }
    return switch (source) {
      case pieces -> IngredientUnit.PIECES;
      case grams -> IngredientUnit.GRAMS;
      case kilograms -> IngredientUnit.KILOGRAMS;
      case milliliters -> IngredientUnit.MILLILITERS;
      case liters -> IngredientUnit.LITERS;
      case teaspoons -> IngredientUnit.TEASPOONS;
      case tablespoons -> IngredientUnit.TABLESPOONS;
      case cups -> IngredientUnit.CUPS;
      case cloves -> IngredientUnit.CLOVES;
      case pinches -> IngredientUnit.PINCHES;
      case packages -> IngredientUnit.PACKAGES;
      case cans -> IngredientUnit.CANS;
      case bottles -> IngredientUnit.BOTTLES;
      case slices -> IngredientUnit.SLICES;
      case sprigs -> IngredientUnit.SPRIGS;
      case stalks -> IngredientUnit.STALKS;
      case cubes -> IngredientUnit.CUBES;
    };
  }

}
