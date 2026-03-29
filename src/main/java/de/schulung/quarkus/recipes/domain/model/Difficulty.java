package de.schulung.quarkus.recipes.domain.model;

/**
 * Difficulty level of the recipe, reflecting required cooking skills.
 */
public enum Difficulty {
  /**
   * Basic skills: Simple techniques, few ingredients, and minimal preparation time.
   * Suitable for beginners.
   */
  EASY,
  /**
   * Intermediate skills: Requires some experience, handling of more complex techniques
   * or timing, and a variety of ingredients.
   */
  MEDIUM,
  /**
   * Advanced skills: Demanding recipes requiring precise techniques, advanced equipment,
   * or extensive preparation and timing.
   */
  HARD
}
