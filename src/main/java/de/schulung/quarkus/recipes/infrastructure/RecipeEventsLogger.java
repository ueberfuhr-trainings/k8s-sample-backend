package de.schulung.quarkus.recipes.infrastructure;

import de.schulung.quarkus.recipes.domain.events.RecipeCreatedEvent;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

@ApplicationScoped
public class RecipeEventsLogger {

  @LoggerName("customer-events")
  Logger log;

  public void logRecipeCreatedEvent(
    @Observes
    RecipeCreatedEvent event
  ) {
    log.infov(
      "Recipe created with id={0}",
      event.recipe().getId()
    );
  }

}
