package de.schulung.quarkus.recipes.persistence.hibernate;

import de.schulung.quarkus.recipes.domain.model.Difficulty;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DifficultyConverter implements AttributeConverter<Difficulty, String> {

  @Override
  public String convertToDatabaseColumn(Difficulty attribute) {
    if (attribute == null) {
      return null;
    }
    return switch (attribute) {
      case EASY -> "easy";
      case MEDIUM -> "medium";
      case HARD -> "hard";
    };
  }

  @Override
  public Difficulty convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return switch (dbData) {
      case "easy" -> Difficulty.EASY;
      case "medium" -> Difficulty.MEDIUM;
      case "hard" -> Difficulty.HARD;
      default -> throw new IllegalArgumentException("Unknown difficulty: " + dbData);
    };
  }
}
