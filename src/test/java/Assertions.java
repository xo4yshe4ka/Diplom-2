import io.restassured.response.ValidatableResponse;

import static org.junit.Assert.*;

public class Assertions {

    public static void validateErrorResponse(ValidatableResponse response, int expectedStatusCode, String expectedMessage) {
        int actualStatusCode = response.extract().statusCode();
        assertEquals("Status code must be: " + expectedStatusCode, expectedStatusCode, actualStatusCode);

        boolean success = response.extract().path("success");
        assertFalse("Success must be false", success);

        String actualMessage = response.extract().path("message");
        assertEquals("Message must be: " + expectedMessage, expectedMessage, actualMessage);
    }

    public static void validateStatusCodeResponse(ValidatableResponse response, int expectedStatusCode) {
        int actualStatusCode = response.extract().statusCode();
        assertEquals("Status code must be: " + expectedStatusCode, expectedStatusCode, actualStatusCode);
    }
}
