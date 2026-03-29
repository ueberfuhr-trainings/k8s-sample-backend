package de.schulung.quarkus.recipes.persistence.hibernate;

import de.schulung.quarkus.recipes.domain.model.IngredientUnit;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IngredientUnitConverter implements AttributeConverter<IngredientUnit, String> {

  @Override
  public String convertToDatabaseColumn(IngredientUnit attribute) {
    if (attribute == null) {
      return null;
    }
    return switch (attribute) {
      case PIECES -> "pieces";
      case GRAMS -> "grams";
      case KILOGRAMS -> "kilograms";
      case MILLILITERS -> "milliliters";
      case LITERS -> "liters";
      case TEASPOONS -> "teaspoons";
      case TABLESPOONS -> "tablespoons";
      case CUPS -> "cups";
      case CLOVES -> "cloves";
      case PINCHES -> "pinches";
      case PACKAGES -> "packages";
      case CANS -> "cans";
      case BOTTLES -> "bottles";
      case SLICES -> "slices";
      case SPRIGS -> "sprigs";
      case STALKS -> "stalks";
      case CUBES -> "cubes";
    };
  }

  @Override
  public IngredientUnit convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return switch (dbData) {
      case "pieces" -> IngredientUnit.PIECES;
      case "grams" -> IngredientUnit.GRAMS;
      case "kilograms" -> IngredientUnit.KILOGRAMS;
      case "milliliters" -> IngredientUnit.MILLILITERS;
      case "liters" -> IngredientUnit.LITERS;
      case "teaspoons" -> IngredientUnit.TEASPOONS;
      case "tablespoons" -> IngredientUnit.TABLESPOONS;
      case "cups" -> IngredientUnit.CUPS;
      case "cloves" -> IngredientUnit.CLOVES;
      case "pinches" -> IngredientUnit.PINCHES;
      case "packages" -> IngredientUnit.PACKAGES;
      case "cans" -> IngredientUnit.CANS;
      case "bottles" -> IngredientUnit.BOTTLES;
      case "slices" -> IngredientUnit.SLICES;
      case "sprigs" -> IngredientUnit.SPRIGS;
      case "stalks" -> IngredientUnit.STALKS;
      case "cubes" -> IngredientUnit.CUBES;
      default -> throw new IllegalArgumentException("Unknown ingredient unit: " + dbData);
    };
  }
}
