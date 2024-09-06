import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.example.User;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUserChangeData {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getUser();
    }

    @After
    public void tearDown() {
        if(accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Changing the user's email")
    @Description("Positive test for endpoint PATCH api/auth/user")
    public void testChangeUserEmail() {
        accessToken = userClient.createUser(user).extract().path("accessToken");

        String expectedEmail = "new_email@test.ru";
        user.setEmail(expectedEmail);
        ValidatableResponse response = userClient.changeUserData(accessToken, user);

        Assertions.validateStatusCodeResponse(response, HttpStatus.SC_OK);

        String actualEmail = response.extract().path("user.email");
        assertEquals("Email must be " + expectedEmail, expectedEmail, actualEmail);
    }

    @Test
    @DisplayName("Changing the user's password")
    @Description("Positive test for endpoint PATCH api/auth/user")
    public void testChangeUserPassword() {
        accessToken = userClient.createUser(user).extract().path("accessToken");

        String expectedPassword = "654321";
        user.setPassword(expectedPassword);
        ValidatableResponse response = userClient.changeUserData(accessToken, user);

        Assertions.validateStatusCodeResponse(response, HttpStatus.SC_OK);

        boolean success = response.extract().path("success");
        assertTrue("success must be true", success);
    }

    @Test
    @DisplayName("Changing the user's name")
    @Description("Positive test for endpoint PATCH api/auth/user")
    public void testChangeUserName() {
        accessToken = userClient.createUser(user).extract().path("accessToken");;

        String expectedName = "new_name";
        user.setName(expectedName);
        ValidatableResponse response = userClient.changeUserData(accessToken, user);

        Assertions.validateStatusCodeResponse(response, HttpStatus.SC_OK);

        String actualName = response.extract().path("user.name");
        assertEquals("Name must be " + expectedName, expectedName, actualName);
    }

    @Test
    @DisplayName("Changing an email from an unauthorized user")
    @Description("Negative test for endpoint PATCH api/auth/user")
    public void testChangeUserEmailWithoutAuthorization() {
        String expectedEmail = "new_email@test.ru";
        user.setEmail(expectedEmail);
        ValidatableResponse response = userClient.changeUserData(user);

        Assertions.validateErrorResponse(response, HttpStatus.SC_UNAUTHORIZED,
                "You should be authorised");
    }
}
