package de.schulung.quarkus.recipes.domain.model;

/**
 * Units for portions of ingredients.
 */
public enum IngredientUnit {
  /**
   * Generic unit for items like eggs or tomatoes.
   */
  PIECES,
  /**
   * Weight in grams (g).
   */
  GRAMS,
  /**
   * Weight in kilograms (kg).
   */
  KILOGRAMS,
  /**
   * Volume in milliliters (ml).
   */
  MILLILITERS,
  /**
   * Volume in liters (l).
   */
  LITERS,
  /**
   * Small spoon (TL/Teelöffel).
   */
  TEASPOONS,
  /**
   * Large spoon (EL/Esslöffel).
   */
  TABLESPOONS,
  /**
   * Standard cup volume.
   */
  CUPS,
  /**
   * For garlic cloves.
   */
  CLOVES,
  /**
   * Very small amount (Prise).
   */
  PINCHES,
  /**
   * For pre-packaged items like baking powder.
   */
  PACKAGES,
  /**
   * For canned goods.
   */
  CANS,
  /**
   * For bottled liquids.
   */
  BOTTLES,
  /**
   * For items like bread or ham.
   */
  SLICES,
  /**
   * For herbs like rosemary.
   */
  SPRIGS,
  /**
   * For items like celery.
   */
  STALKS,
  /**
   * For items in cube form, e.g., fresh yeast or bouillon cubes.
   */
  CUBES
}
