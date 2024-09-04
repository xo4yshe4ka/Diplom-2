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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class TestOrderCreate {

    private OrderClient orderClient;
    private Order order;
    private String accessToken;
    private UserClient userClient;
    private User user;

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
    @DisplayName("Creating order by authorized user")
    @Description("Positive test for endpoint POST api/orders")
    public void testOrderCreate() {
        accessToken = userClient.createUser(user).extract().path("accessToken");

        ValidatableResponse response = orderClient.orderCreate(accessToken, order);

        Assertions.validateStatusCodeResponse(response, HttpStatus.SC_OK);

        int numberOrder = response.extract().path("order.number");
        assertThat("Order number must not be zero", numberOrder, is(not(0)));
    }

    @Test
    @DisplayName("Creating order by authorized user and invalid hash of the ingredient")
    @Description("Negative test for endpoint POST api/orders")
    public void testOrderCreateWithInvalidHashIngredient() {
        order.setIngredients(List.of("123654789dfdgdfg45df"));
        ValidatableResponse response = orderClient.orderCreate(order);

        Assertions.validateStatusCodeResponse(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Creating order by authorized user and without ingredient")
    @Description("Negative test for endpoint POST api/orders")
    public void testOrderCreateWithoutIngredient() {
        order.setIngredients(List.of());
        ValidatableResponse response = orderClient.orderCreate(order);

        Assertions.validateErrorResponse(response, HttpStatus.SC_BAD_REQUEST,
                "Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Creating order by unauthorized user")
    @Description("Positive test for endpoint POST api/orders")
    public void testOrderCreateWithoutLoginUser() {
        ValidatableResponse response = orderClient.orderCreate(order);

        Assertions.validateStatusCodeResponse(response, HttpStatus.SC_OK);

        boolean success = response.extract().path("success");
        assertTrue("Success must be true", success);
    }
}
