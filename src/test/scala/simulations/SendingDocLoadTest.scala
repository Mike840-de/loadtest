package simulations

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import dto.DocDto
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class SendingDocLoadTest extends Simulation {

  /* Place for arbitrary Scala code that is to be executed before the simulation begins. */
  before {
    println("***** My simulation is about to begin! *****")
  }

  /* Place for arbitrary Scala code that is to be executed after the simulation has ended. */
  after {
    println("***** My simulation has ended! ******")
  }

  /*
   * A HTTP protocol builder is used to specify common properties of request(s) to be sent,
   * for instance the base URL, HTTP headers that are to be enclosed with all requests etc.
   */
  val theHttpProtocolBuilder: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8092")

  /*
   * A scenario consists of one or more requests. For instance logging into a e-commerce
   * website, placing an order and then logging out.
   * One simulation can contain many scenarios.
   */
  /* Scenario1 is a name that describes the scenario. */
  val objectMapper = new ObjectMapper
  objectMapper.registerModule(DefaultScalaModule)

  val json: String = objectMapper.writeValueAsString(DocDto("qwe", "qwe", "qwe", "qwe", "qwe"))
  println("json: " + json)



  val theScenarioBuilder: ScenarioBuilder = scenario("Scenario1")
    .exec(
      http("qwe")
        .post("/api/v1/doc/send")
        .header("Content-Type", "application/json")
        .body(StringBody(json))
    )

  /*
   * Define the load simulation.
   * Here we can specify how many users we want to simulate, if the number of users is to increase
   * gradually or if all the simulated users are to start sending requests at once etc.
   * We also specify the HTTP protocol builder to be used by the load simulation.
   */
  setUp(
    theScenarioBuilder.inject(atOnceUsers(1))
  ).protocols(theHttpProtocolBuilder)

}
