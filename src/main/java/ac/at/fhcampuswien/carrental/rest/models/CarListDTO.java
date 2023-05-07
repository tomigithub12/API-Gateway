package ac.at.fhcampuswien.carrental.rest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarListDTO {

    private Long id;
    private float dailyCost;
    private String brand;
    private String model;
    private String hp;
    private String buildDate;
    private String fuelType;
    private String imageLink;
    private float totalCosts;
}
