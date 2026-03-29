package de.schulung.quarkus.recipes.infrastructure;

import de.schulung.quarkus.recipes.test.CaptureOutput;
import de.schulung.quarkus.recipes.test.CaptureOutput.CapturedOutput;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestTransaction
@CaptureOutput
class RecipeEventsLoggerTests {

  @Test
  void whenCreateRecipe_thenLogEvent(CapturedOutput output) {
    var response = given()
      .contentType(ContentType.JSON)
      .body("""
        {
          "name": "Tomato Soup",
          "img": "/my-image",
          "servings": 4,
          "duration": 30,
          "difficulty": "easy",
          "ingredients": [
            { "unit": "pieces", "quantity": 2.0, "name": "Tomato" }
          ],
          "preparation": "Boil tomatoes."
        }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/recipes");
    response
      .then()
      .statusCode(201)
      .header("Location", is(notNullValue()));
    var id = response.body().jsonPath().getString("id");

    assertTrue(
      output.toString().matches("(?si).*Recipe created.*" + id + ".*"),
      "Expected log output to contain 'Recipe created' with id=" + id
    );
  }

}
