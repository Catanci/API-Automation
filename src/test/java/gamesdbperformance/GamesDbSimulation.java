package gamesdbperformance;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class GamesDbSimulation extends Simulation {

    private HttpProtocolBuilder httpProtocolBuilder = http
            .baseUrl("https://videogamedb.uk/api/")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private static final int USER_COUNT = Integer.parseInt(System.getProperty("USERS", "5"));

    private static final int RAMP_DURATION = Integer.parseInt(System.getProperty("RAMP_DURATION", "10"));

    private static FeederBuilder.FileBased<Object> feederBuilder = jsonFile("feederdata/gameJsonFile.json").random();

    public void before() {
        System.out.printf("Running test with %d users%n", USER_COUNT);
        System.out.printf("Ramping users over with %d seconds%n", RAMP_DURATION);
    }

    public void after() {
        System.out.printf("Simulation is finished!%n");
    }

    private static ChainBuilder getAllGames =
            exec(http("Get all users")
                    .get("v2/videogame"));

    private static ChainBuilder authenticate =
            exec(http("Authenticate")
                    .post("authenticate")
                    .body(ElFileBody("bodies/authenticate.json")).asJson()
                    .check(jmesPath("token").saveAs("jwtToken")));

    private static ChainBuilder createNewGame =
            feed(feederBuilder)
                    .exec(http("Create new game - #{name}")
                    .post("v2/videogame")
                    .header("Authorization", "Bearer #{jwtToken}")
                    .body(ElFileBody("bodies/newGameTemplate.json")).asJson());

    private static ChainBuilder getLastCreatedGame =
            exec(http("Get last created game - #{name}")
                    .get("v2/videogame/#{id}")
                    .check(jmesPath("name").isEL("#{name}")));

    private static ChainBuilder deleteLastPostedGame =
            exec(http("Delete game - #{name}")
                    .delete("/videogame/#{id}")
                    .header("Authorization", "Bearer #{jwtToken}")
                    .check(bodyString().is("Video game deleted")));


    private ScenarioBuilder scenarioBuilder = scenario("Videogames DB stress test")
            .exec(getAllGames)
            .pause(2)
            .exec(authenticate)
            .pause(2)
            .exec(createNewGame)
            .pause(2)
            .exec(getLastCreatedGame)
            .pause(2)
            .exec(deleteLastPostedGame);

    {
setUp(
        scenarioBuilder.injectOpen(
                nothingFor(5),
                atOnceUsers(10),
                rampUsers(USER_COUNT).during(RAMP_DURATION),
                constantUsersPerSec(20).during(15),
                stressPeakUsers(50).during(5)
        ))
            .protocols(httpProtocolBuilder)
            .assertions(global().failedRequests().count().is(0L));
    }
}
