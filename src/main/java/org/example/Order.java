package org.example;

import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private List<String> ingredients;

}
