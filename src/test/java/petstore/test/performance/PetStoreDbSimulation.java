package petstore.test.performance;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PetStoreDbSimulation extends Simulation {

    private HttpProtocolBuilder httpProtocolBuilder = http
            .baseUrl("https://petstore.swagger.io/v2/")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private static FeederBuilder.FileBased<Object> feederBuilderPost = jsonFile("feederdata/postPetJsonFile.json").circular();
    private static FeederBuilder<String> csvFeeder = csv("feederdata/postPetCsvFile.csv").random();



    private static ChainBuilder createPet =
            feed(feederBuilderPost)
                    .exec(http("Create new pet - #{name}")
                    .post("pet")
                    .asJson()
                    .body(ElFileBody("bodies/petTemplate.json"))
                    .check(
                            status().is(200),
                            jmesPath("id").isEL("#{id}")
                    ));

    private static ChainBuilder createPetFromCsv =
            feed(csvFeeder)
                    .exec(http("Create new pet - csv")
                            .post("pet")
                            .body(ElFileBody("bodies/petTemplate.json")).asJson()
                            .check(
                                    status().is(200),
                                    jmesPath("name").isEL("#{name}"),
                                    jmesPath("id").isEL("#{id}")

                            ));




    private ScenarioBuilder scenarioBuilder = scenario("Pet store DB stress test")
            .exec(createPet)
            .pause(1)
            .exec(createPetFromCsv)
            .pause(1);

    {
        setUp(
                scenarioBuilder.injectOpen(
                        nothingFor(2),
                        atOnceUsers(1))
        )
                .protocols(httpProtocolBuilder)
                .assertions(
                        global().responseTime().max().lt(1000),
                        global().successfulRequests().percent().gt(95.0));
    }
}
