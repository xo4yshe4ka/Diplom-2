package org.example;

import java.util.List;

public class OrderGenerator {
    public static Order getIngredients() {
        Order  order = new Order();
        order.setIngredients(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa71"));
        return order;
    }
}
