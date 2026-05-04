package maram.isett.listeetudapirest.simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

public class EtudiantSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080") // Assumes service is running on 8080
            .acceptHeader("application/json");

    ScenarioBuilder scn = scenario("Liste des étudiants")
            .exec(http("GET /api/etudiants")
                    .get("/api/etudiants") // Adjust path if necessary
                    .check(status().is(200)));

    {
        setUp(scn.injectOpen(
                rampUsers(50).during(Duration.ofSeconds(30))
        )).protocols(httpProtocol);
    }
}
