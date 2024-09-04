import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.example.User;
import org.example.UserClient;
import org.example.UserCredentials;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

public class TestUserLogin {

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
    @DisplayName("User authorization")
    @Description("Positive test for endpoint POST api/auth/login")
    public void testLoginUser() {
        userClient.createUser(user);

        ValidatableResponse response = userClient.loginUser(UserCredentials.from(user));

        Assertions.validateStatusCodeResponse(response, HttpStatus.SC_OK);

        accessToken = response.extract().path("accessToken");
        assertThat("Access token should not be empty", accessToken, not(isEmptyString()));
    }

    @Test
    @DisplayName("Authorization of user with an incorrect email")
    @Description("Negative test for endpoint POST api/auth/login")
    public void testLoginUserWithIncorrectEmail() {
        user.setEmail("incorrect@incorrect.ru");
        ValidatableResponse response = userClient.loginUser(UserCredentials.from(user));

        Assertions.validateErrorResponse(response, HttpStatus.SC_UNAUTHORIZED,
                "email or password are incorrect");
    }

    @Test
    @DisplayName("Authorization of user with an incorrect password")
    @Description("Negative test for endpoint POST api/auth/login")
    public void testLoginUserWithIncorrectPassword() {
        user.setPassword("9547832102");
        ValidatableResponse response = userClient.loginUser(UserCredentials.from(user));

        Assertions.validateErrorResponse(response, HttpStatus.SC_UNAUTHORIZED,
                "email or password are incorrect");
    }
}
