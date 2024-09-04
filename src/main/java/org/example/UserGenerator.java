package org.example;

public class UserGenerator {

    public static User getUser() {
        User user = new User();
        user.setEmail("test2003@test.ru");
        user.setPassword("123456");
        user.setName("testerovich");
        return user;
    }
}
