import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class ApiTest {

    private String firstHuman = "https://swapi.co/api/people/1/";
    private String homeworld = "";
    private String firstFilm = "";


    @BeforeClass
    public void preSteps() {
        homeworld = given().when().get(firstHuman).then()
                .body("name", equalTo("Luke Skywalker")).extract().path("homeworld");
        firstFilm = given().when().get(homeworld).then().extract().path("films[0]");
    }

    @Test
    public void firsHumanHasCorrectName() {
        given().when().get(firstHuman).then()
                .body("name", equalTo("Luke Skywalker"));
    }

    @Test
    public void homeWorldHasCorrectPlanetName() {
        given().when().get(homeworld).then()
                .body("name", equalTo("Tatooine"));
    }

    @Test
    public void homeWorldHasCorrectPlanetPopulation() {
        given().when().get(homeworld).then()
                .body("population", equalTo("200000"));
    }

    @Test
    public void firstFilmOfPlanetHasCorrectTitle() {
        given().when().get(firstFilm).then()
                .body("title", equalTo("Attack of the Clones"));
    }

    @Test
    public void isFirstFilmHasFirstHuman() {
        given().when().get(firstFilm).then()
                .body("characters", hasItem(firstHuman));
    }

    @Test
    public void isFirstFilmHashomeWorldPlanet() {
        given().when().get(firstFilm).then()
                .body("planets", hasItem(homeworld));
    }
}
