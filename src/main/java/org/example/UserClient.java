package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.BaseApi.*;
import static org.example.RestClient.getBaseSpec;

public class UserClient {

    @Step
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(POST_CREATE_USER)
                .then();
    }

    @Step
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec(accessToken))
                .when()
                .delete(DELETE_USER)
                .then();
    }

    @Step
    public ValidatableResponse loginUser(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(POST_LOGIN_USER)
                .then();
    }

    @Step
    public ValidatableResponse changeUserData(String accessToken, User user) {
        return given()
                .spec(getBaseSpec(accessToken))
                .body(user)
                .when()
                .patch(PATCH_CHANGE_USER_DATA)
                .then();
    }

    @Step
    public ValidatableResponse changeUserData(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch(PATCH_CHANGE_USER_DATA)
                .then();
    }
}
