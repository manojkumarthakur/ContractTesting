package mockapi;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class WireMockTest {

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8089))
            .build();

    @Test
    public void testStubbedUserApi() {
        // Arrange: setup stub
        wiremock.stubFor(get(urlEqualTo("/users/123"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": 123, \"name\": \"John Doe\" }")));

        // Act + Assert: test against mock API
        given().baseUri("http://localhost:8089")
                .when().get("/users/123")
                .then().statusCode(200)
                .body("name", equalTo("John Doe"));
        System.out.println("Successfully completed the test...");
    }
}
