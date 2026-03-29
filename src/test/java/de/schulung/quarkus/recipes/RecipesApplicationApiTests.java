package de.schulung.quarkus.recipes;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestTransaction
class RecipesApplicationApiTests {

  // --- Content Negotiation ---

  @Test
  void shouldReturn406WhenAcceptIsNotJsonOnGetAll() {
    given()
      .accept(ContentType.XML)
      .when().get("/recipes")
      .then()
      .statusCode(406);
  }

  @Test
  void shouldReturn406WhenAcceptIsNotJsonOnGetSingle() {
    given()
      .accept(ContentType.XML)
      .when().get("/recipes/some-id")
      .then()
      .statusCode(406);
  }

  @Test
  void shouldReturn415WhenContentTypeIsNotJsonOnPost() {
    given()
      .contentType(ContentType.XML)
      .body("<recipe/>")
      .when().post("/recipes")
      .then()
      .statusCode(415);
  }

  @Test
  void shouldReturn406WhenAcceptIsNotJsonOnPost() {
    given()
      .contentType(ContentType.JSON)
      .accept(ContentType.XML)
      .body("""
            {
              "name": "Valid Recipe",
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
      .statusCode(406);
  }

  // --- GET /recipes ---

  @Test
  void shouldReturnAListWhenGettingAllRecipes() {
    given()
      .when().get("/recipes")
      .then()
      .statusCode(200)
      .body("$", instanceOf(List.class));
  }

  // --- POST /recipes + GET /recipes/{id} ---

  @Test
  void shouldCreateRecipeAndBeRetrievableViaLocation() {
    final var recipeJson = """
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
      """;

    final var response = given()
      .contentType(ContentType.JSON)
      .body(recipeJson)
      .when().post("/recipes")
      .then()
      .statusCode(201)
      .body("id", notNullValue())
      .body("name", is("Tomato Soup"))
      .body("img", is("/my-image"))
      .body("servings", is(4))
      .body("duration", is(30))
      .body("difficulty", is("easy"))
      .body("lastEdited", notNullValue())
      .body("ingredients", hasSize(1))
      .body("ingredients[0].unit", is("pieces"))
      .body("ingredients[0].quantity", is(2.0f))
      .body("ingredients[0].name", is("Tomato"))
      .body("preparation", is("Boil tomatoes."))
      .extract().response();

    final var id = response.path("id").toString();
    final var location = response.header("Location");

    assertThat(location, endsWith("/recipes/" + id));

    given()
      .when().get(location)
      .then()
      .statusCode(200)
      .body("id", is(id))
      .body("name", is("Tomato Soup"))
      .body("img", is("/my-image"))
      .body("servings", is(4))
      .body("duration", is(30))
      .body("difficulty", is("easy"))
      .body("lastEdited", notNullValue())
      .body("ingredients", hasSize(1))
      .body("ingredients[0].unit", is("pieces"))
      .body("ingredients[0].quantity", is(2.0f))
      .body("ingredients[0].name", is("Tomato"))
      .body("preparation", is("Boil tomatoes."));
  }

  @Test
  void shouldContainCreatedRecipeInAllRecipesList() {
    final var recipeJson = """
      {
        "name": "List Check Soup",
        "img": null,
        "servings": 2,
        "duration": 10,
        "difficulty": "easy",
        "ingredients": [
          { "unit": "pieces", "quantity": 1.0, "name": "Tomato" }
        ],
        "preparation": "Mix and heat."
      }
      """;

    final var createdId = given()
      .contentType(ContentType.JSON)
      .body(recipeJson)
      .when().post("/recipes")
      .then()
      .statusCode(201)
      .extract()
      .path("id").toString();

    given()
      .when().get("/recipes")
      .then()
      .statusCode(200)
      .body("id", hasItem(createdId));
  }

  // --- POST /recipes default values ---

  @Test
  void shouldApplyDefaultImgWhenNotProvided() {
    final var recipeJson = """
      {
        "name": "No Image Recipe",
        "servings": 2,
        "duration": 15,
        "difficulty": "easy",
        "ingredients": [
          { "unit": "pieces", "quantity": 1.0, "name": "Tomato" }
        ],
        "preparation": "Cook it."
      }
      """;

    final var location = given()
      .contentType(ContentType.JSON)
      .body(recipeJson)
      .when().post("/recipes")
      .then()
      .statusCode(201)
      .body("img", is("/recipe_pictures/default.jpg"))
      .extract().header("Location");

    given()
      .when().get(location)
      .then()
      .statusCode(200)
      .body("img", is("/recipe_pictures/default.jpg"));
  }

  @Test
  void shouldApplyDefaultDifficultyWhenNotProvided() {
    final var recipeJson = """
      {
        "name": "No Difficulty Recipe",
        "servings": 2,
        "duration": 15,
        "ingredients": [
          { "unit": "pieces", "quantity": 1.0, "name": "Tomato" }
        ],
        "preparation": "Cook it."
      }
      """;

    final var location = given()
      .contentType(ContentType.JSON)
      .body(recipeJson)
      .when().post("/recipes")
      .then()
      .statusCode(201)
      .body("difficulty", is("medium"))
      .extract().header("Location");

    given()
      .when().get(location)
      .then()
      .statusCode(200)
      .body("difficulty", is("medium"));
  }

  static Stream<Arguments> invalidRecipeRequests() {
    return Stream.of(
      // --- unknown / read-only properties ---
      Arguments.of(
        "unknown property",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it.",
            "unknownProperty": "should not be accepted"
          }
          """
      ),
      Arguments.of(
        "read-only id",
        """
          {
            "id": "client-provided-id",
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "read-only lastEdited",
        """
          {
            "id": "client-provided-id",
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "lastEdited": "2021-01-01T00:00:00Z",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      // --- Recipe: name (required, minLength 1, maxLength 100) ---
      Arguments.of(
        "missing name",
        """
          {
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "empty name",
        """
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
          """
      ),
      Arguments.of(
        "name too long",
        """
          {
            "name": "%s",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """.formatted("A".repeat(101))
      ),
      // --- Recipe: img (maxLength 255, pattern ^(/|https?://).+) ---
      Arguments.of(
        "img invalid pattern",
        """
          {
            "name": "Test Recipe",
            "img": "not-a-valid-url",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "img too long",
        """
          {
            "name": "Test Recipe",
            "img": "/%s",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """.formatted("a".repeat(255))
      ),
      // --- Recipe: servings (required, minimum 1) ---
      Arguments.of(
        "missing servings",
        """
          {
            "name": "Test Recipe",
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "servings zero",
        """
          {
            "name": "Test Recipe",
            "servings": 0,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      // --- Recipe: duration (required, minimum 1) ---
      Arguments.of(
        "missing duration",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "duration zero",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 0,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      // --- Recipe: difficulty (enum: easy, medium, hard) ---
      Arguments.of(
        "invalid difficulty",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "impossible",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      // --- Recipe: ingredients (required) ---
      Arguments.of(
        "missing ingredients",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "preparation": "Cook it."
          }
          """
      ),
      // --- Recipe: preparation (required, maxLength 2000) ---
      Arguments.of(
        "missing preparation",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ]
          }
          """
      ),
      Arguments.of(
        "preparation too long",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "%s"
          }
          """.formatted("A".repeat(2001))
      ),
      // --- Ingredient: unit (required, enum) ---
      Arguments.of(
        "ingredient: missing unit",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "ingredient: invalid unit",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "bushels", "quantity": 2.0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      // --- Ingredient: quantity (required, exclusiveMinimum 0) ---
      Arguments.of(
        "ingredient: missing quantity",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "ingredient: quantity zero",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 0, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "ingredient: quantity negative",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": -1, "name": "Tomato"}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      // --- Ingredient: name (required, minLength 1, maxLength 100) ---
      Arguments.of(
        "ingredient: missing name",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "ingredient: empty name",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": ""}
            ],
            "preparation": "Cook it."
          }
          """
      ),
      Arguments.of(
        "ingredient: name too long",
        """
          {
            "name": "Test Recipe",
            "servings": 4,
            "duration": 30,
            "difficulty": "easy",
            "ingredients": [
              {"unit": "pieces", "quantity": 2.0, "name": "%s"}
            ],
            "preparation": "Cook it."
          }
          """.formatted("A".repeat(101))
      )
    );
  }

  @ParameterizedTest(name = "try to create invalid recipe - {0}")
  @MethodSource("invalidRecipeRequests")
  void shouldReturn400WhenCreatingRecipeWithInvalidData(String description, String json) {
    given()
      .contentType(ContentType.JSON)
      .body(json)
      .when().post("/recipes")
      .then()
      .statusCode(400);
  }

  // --- GET /recipes/{id} error cases ---

  @Test
  void shouldReturn404WhenRecipeDoesNotExist() {
    given()
      .when().get("/recipes/non-existent-id")
      .then()
      .statusCode(404);
  }

}
