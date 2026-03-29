package de.schulung.quarkus.recipes.persistence.hibernate;

import de.schulung.quarkus.recipes.domain.RecipesDao;
import de.schulung.quarkus.recipes.domain.model.Recipe;
import de.schulung.quarkus.recipes.persistence.hibernate.mappers.RecipeEntityMapper;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@IfBuildProperty(
  name = "quarkus.hibernate-orm.active",
  stringValue = "true",
  enableIfMissing = true
)
@ApplicationScoped
@Typed(RecipesDao.class)
@RequiredArgsConstructor
public class RecipesDaoHibernateImpl implements RecipesDao {

  private final RecipesRepository repository;
  private final RecipeEntityMapper mapper;

  @Override
  public Stream<Recipe> findAll() {
    return repository
      .listAll()
      .stream()
      .map(mapper::map);
  }

  @Override
  public Optional<Recipe> findById(String id) {
    return repository
      .findByIdOptional(id)
      .map(mapper::map);
  }

  @Override
  @Transactional
  public void save(Recipe recipe) {
    var entity = mapper.map(recipe);
    repository.persist(entity);
    mapper.copy(entity, recipe);
  }
}
