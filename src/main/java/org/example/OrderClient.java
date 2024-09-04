package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.BaseApi.GET_RECEIVE_ORDER_USER;
import static org.example.BaseApi.POST_CREATE_ORDER;
import static org.example.RestClient.getBaseSpec;

public class OrderClient {
    @Step
    public ValidatableResponse orderCreate(String accessToken, Order order) {
        return given()
                .spec(getBaseSpec(accessToken))
                .body(order)
                .when()
                .post(POST_CREATE_ORDER)
                .then();
    }

    @Step
    public ValidatableResponse orderCreate(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(POST_CREATE_ORDER)
                .then();
    }

    @Step
    public ValidatableResponse getOrdersUser(String accessToken) {
        return given()
                .spec(getBaseSpec(accessToken))
                .when()
                .get(GET_RECEIVE_ORDER_USER)
                .then();
    }

    @Step
    public ValidatableResponse getOrdersUser() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(GET_RECEIVE_ORDER_USER)
                .then();
    }
}
