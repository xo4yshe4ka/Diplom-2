package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.BaseApi.*;
import static org.example.RestClient.getBaseSpec;

public class OrderClient {
    @Step("Send POST request to api/orders for creating order by authorized user")
    public ValidatableResponse orderCreate(String accessToken, Order order) {
        return given()
                .spec(getBaseSpec(accessToken))
                .body(order)
                .when()
                .post(POST_CREATE_ORDER)
                .then();
    }

    @Step("Send POST request to api/orders for creating order by unauthorized user")
    public ValidatableResponse orderCreate(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(POST_CREATE_ORDER)
                .then();
    }

    @Step("Send GET request to api/orders for get order by authorized user")
    public ValidatableResponse getOrdersUser(String accessToken) {
        return given()
                .spec(getBaseSpec(accessToken))
                .when()
                .get(GET_RECEIVE_ORDER_USER)
                .then();
    }

    @Step("Send GET request to api/orders for get order by unauthorized user")
    public ValidatableResponse getOrdersUser() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(GET_RECEIVE_ORDER_USER)
                .then();
    }

    @Step("Send GET request to api/ingredients for get ingredients")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(GET_INGREDIENTS)
                .then();
    }
}
