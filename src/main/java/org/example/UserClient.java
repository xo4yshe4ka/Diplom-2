package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.BaseApi.*;
import static org.example.RestClient.getBaseSpec;

public class UserClient {

    @Step("Send POST request to api/auth/register to create user")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(POST_CREATE_USER)
                .then();
    }

    @Step("Send DELETE request to api/auth/user for deletion user")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec(accessToken))
                .when()
                .delete(DELETE_USER)
                .then();
    }

    @Step("Send POST request to api/auth/login to login user")
    public ValidatableResponse loginUser(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(POST_LOGIN_USER)
                .then();
    }

    @Step("Send PATCH request to api/auth/user to change data of registered user")
    public ValidatableResponse changeUserData(String accessToken, User user) {
        return given()
                .spec(getBaseSpec(accessToken))
                .body(user)
                .when()
                .patch(PATCH_CHANGE_USER_DATA)
                .then();
    }

    @Step("Send PATCH request to api/auth/user to change data of unregistered user")
    public ValidatableResponse changeUserData(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch(PATCH_CHANGE_USER_DATA)
                .then();
    }
}
