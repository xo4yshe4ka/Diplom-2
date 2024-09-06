package org.example;

import com.google.gson.JsonObject;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderGenerator {
    public static Order getIngredients() {
        OrderClient orderClient = new OrderClient();
        Order order = new Order();
        ValidatableResponse response = orderClient.getIngredients();

        List<JsonObject> dataObjects = new ArrayList<>(response.extract().jsonPath().getList("data", JsonObject.class));

        if (dataObjects.isEmpty()) {
            order.setIngredients(new ArrayList<>());
            return order;
        }

        List<String> listIngredients = new ArrayList<>();
        Random random = new Random();
        int randomCount = random.nextInt(dataObjects.size()) + 1;

        for (int i = 0; i < randomCount; i++) {
            int randomIndex = random.nextInt(dataObjects.size());
            listIngredients.add(dataObjects.get(randomIndex).get("_id").getAsString());
            dataObjects.remove(randomIndex);
        }

        order.setIngredients(listIngredients);
        return order;
    }
}
