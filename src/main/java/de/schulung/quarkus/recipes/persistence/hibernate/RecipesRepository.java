package de.schulung.quarkus.recipes.persistence.hibernate;

import de.schulung.quarkus.recipes.persistence.hibernate.model.RecipeEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RecipesRepository
  implements PanacheRepositoryBase<RecipeEntity, String> {
}
