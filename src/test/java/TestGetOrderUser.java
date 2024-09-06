import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestGetOrderUser {
    private OrderClient orderClient;
    private Order order;
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGenerator.getIngredients();
        userClient = new UserClient();
        user = UserGenerator.getUser();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Receiving all orders of a specific user")
    @Description("Positive test for endpoint GET api/orders")
    public void testGetOrdersUser() {
        accessToken = userClient.createUser(user).extract().path("accessToken");

        orderClient.orderCreate(accessToken, order);
        ValidatableResponse response = orderClient.getOrdersUser(accessToken);

        Assertions.validateStatusCodeResponse(response, HttpStatus.SC_OK);

        boolean success = response.extract().path("success");
        assertTrue("Success must be true", success);

        List<String> orders = response.extract().path("orders");
        assertThat("Orders should not be empty", orders, is(not(empty())));
    }

    @Test
    @DisplayName("Receiving all orders from an unauthorized user")
    @Description("Negative test for endpoint GET api/orders")
    public void testGetOrderUserWithoutAuthorization() {
        ValidatableResponse response = orderClient.getOrdersUser();

        Assertions.validateErrorResponse(response, HttpStatus.SC_UNAUTHORIZED, "You should be authorised");
    }
}
