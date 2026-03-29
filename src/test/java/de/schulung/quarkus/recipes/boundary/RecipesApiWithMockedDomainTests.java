package de.schulung.quarkus.recipes.boundary;

import de.schulung.quarkus.recipes.domain.RecipesService;
import de.schulung.quarkus.recipes.domain.model.Recipe;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
public class RecipesApiWithMockedDomainTests {

  @InjectMock
  RecipesService service;

  @Test
  void shouldReturn404WhenRecipeDoesNotExist() {
    final var recipeId = "non-existent-id";

    when(service.findById(recipeId))
      .thenReturn(Optional.empty());

    given()
      .when().get("/recipes/{id}", recipeId)
      .then()
      .statusCode(404);
  }

  @Test
  void shouldNotInvokeServiceWhenReceivingInvalidData() {
    given()
      .contentType(ContentType.JSON)
      .body("""
        {
          "name": "",
          "servings": 4,
          "duration": 30,
          "difficulty": "easy",
          "ingredients": [
            {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
          ],
          "preparation": "Cook it."
        }
        """)
      .when().post("/recipes")
      .then()
      .statusCode(400);

    verify(service, never())
      .create(any(Recipe.class));
  }

}
