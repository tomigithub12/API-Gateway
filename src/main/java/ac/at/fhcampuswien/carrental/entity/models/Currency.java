package ac.at.fhcampuswien.carrental.entity.models;

import lombok.*;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Currency {

    private long id;

    private final Map<String, String> exchangeRates = new HashMap<>();

}
