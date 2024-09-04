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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestUserCreate {
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
    @DisplayName("Create new user with valid data")
    @Description("Positive test for endpoint POST api/auth/register")
    public void testCreateUser() {
        ValidatableResponse response = userClient.createUser(user);

        Assertions.validateStatusCodeResponse(response, HttpStatus.SC_OK);

        accessToken = response.extract().path("accessToken");
        assertThat("Access token should not be empty", accessToken, not(isEmptyString()));

        String actualEmail = response.extract().path("user.email");
        assertThat("Email must be: " + user.getEmail(), actualEmail, equalTo(user.getEmail()));
    }

    @Test
    @DisplayName("Create two identical users")
    @Description("Negative test for endpoint POST api/auth/register")
    public void testCreateTwoIdenticalUser() {
        accessToken = userClient.createUser(user).extract().path("accessToken");

        ValidatableResponse response = userClient.createUser(user);

        Assertions.validateErrorResponse(response, HttpStatus.SC_FORBIDDEN, "User already exists");
    }

    @Test
    @DisplayName("Create new user without email")
    @Description("Negative test for endpoint POST api/auth/register")
    public void testCreateUserWithoutEmail() {
        user.setEmail("");
        ValidatableResponse response = userClient.createUser(user);

        Assertions.validateErrorResponse(response, HttpStatus.SC_FORBIDDEN,
                "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Create new user without password")
    @Description("Negative test for endpoint POST api/auth/register")
    public void testCreateUserWithoutPassword() {
        user.setPassword("");
        ValidatableResponse response = userClient.createUser(user);

        Assertions.validateErrorResponse(response, HttpStatus.SC_FORBIDDEN,
                "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Create new user without name")
    @Description("Negative test for endpoint POST api/auth/register")
    public void testCreateUserWithoutName() {
        user.setName("");
        ValidatableResponse response = userClient.createUser(user);

        Assertions.validateErrorResponse(response, HttpStatus.SC_FORBIDDEN,
                "Email, password and name are required fields");
    }
}
