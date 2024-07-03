package petstore.test.performance;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PetStoreDbSimulation extends Simulation {

    private static final int USER_COUNT = Integer.parseInt(System.getProperty("USERS", "5"));
    private static final int RAMP_DURATION = Integer.parseInt(System.getProperty("RAMP_DURATION", "10"));

    private static final FeederBuilder.FileBased<Object> feeder = jsonFile("feederdata/petJsonFile.json").random();
    private static final FeederBuilder<String> csvFeeder = csv("feederdata/petCsvFile.csv").random();

    private final HttpProtocolBuilder httpProtocolBuilder = http
            .baseUrl("https://petstore.swagger.io/v2/")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    public void before() {
        System.out.printf("For Ramp scenario - Running test with %d users%n", USER_COUNT);
        System.out.printf("For Ramp scenario - Ramping users over with %d seconds%n", RAMP_DURATION);
    }
    private static final ChainBuilder createPet =
            feed(feeder)
                    .exec(http("Create new pet - #{name}")
                    .post("pet")
                    .asJson()
                    .body(ElFileBody("bodies/petTemplate.json"))
                    .check(
                            status().is(200),
                            jmesPath("id").isEL("#{id}")
                    ));

    private static final ChainBuilder createPetFromCsv =
            feed(csvFeeder)
                    .exec(http("Create new pet from csv- #{name}")
                            .post("pet")
                            .body(ElFileBody("bodies/petTemplate.json")).asJson()
                            .check(
                                    status().is(200),
                                    jmesPath("name").isEL("#{name}"),
                                    jmesPath("id").isEL("#{id}")

                            ));

    private static final ChainBuilder getPet =
            exec(http("Get last created pet - #{name}")
                    .get("pet/#{id}")
                    .check(
                            status().is(200),
                            jmesPath("name").isEL("#{name}"),
                            jmesPath("id").isEL("#{id}")
                    ));

    private static final ChainBuilder deleteGame =
            exec(http("Delete last created pet - #{name}")
                    .delete(("pet/#{id}"))
                    .header("Authorization", "api_key: special_key")
                    .check(
                            status().is(200)
                    ));

    private static final ChainBuilder uploadPetImage =
            exec(http("Upload image for a pet - #{name}")
                    .post("pet/#{id}/uploadImage")
                    .header("Content-Type", "multipart/form-data")
                    .header("Authorization", "api_key: special-key")
                    .bodyPart(RawFileBodyPart("file", "icon.jpg"))
                            .check(
                                    status().is(200),
                                    jmesPath("type").isEL("unknown"),
                                    jmesPath("message").exists(),
                                    regex(".*File uploaded to.*").findAll()
                            ));


    private static final ChainBuilder updatePet =
            feed(feeder)
                    .exec(http("Create new pet for pet update - #{name}")
                            .post("pet")
                            .asJson()
                            .body(ElFileBody("bodies/petTemplate.json"))
                            .check(
                                    status().is(200),
                                    jmesPath("id").isEL("#{id}"))
                    )

                    .exec(http("Update pet information - #{name} to Puszek")
                            .put("pet")
                            .header("Authorization", "api-key: special-key")
                            .asJson()
                            .body(ElFileBody("bodies/petExample.json"))
                            .check(
                                    status().is(200),
                                    jmesPath("name").isEL("Puszek")
                            ));

    private final ScenarioBuilder scenarioBuilder = scenario("Pet store DB stress test")
            .exec(createPet)
            .pause(4)
            .exec(createPetFromCsv)
            .pause(4)
            .exec(getPet)
            .pause(4)
            .exec(deleteGame)
            .pause(4)
            .exec(uploadPetImage)
            .pause(4)
            .exec(updatePet)
            .pause(4);

    {
        setUp(
                scenarioBuilder.injectOpen(
                        nothingFor(4),
                        atOnceUsers(10),
                        rampUsers(10).during(5),
                        constantUsersPerSec(20).during(15),
                        constantUsersPerSec(20).during(15).randomized(),
                        rampUsers(USER_COUNT).during(RAMP_DURATION),
                        constantUsersPerSec(20).during(15),
                        stressPeakUsers(300).during(20)
                ))
                .protocols(httpProtocolBuilder)
                .assertions(
                        global().responseTime().max().lt(1300),
                        global().successfulRequests().percent().gt(90.0));
    }
}
