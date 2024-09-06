package org.example;

import com.github.javafaker.Faker;

public class UserGenerator {

    public static User getUser() {
        Faker faker = new Faker();
        return new User(
                faker.internet().emailAddress(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }
}
