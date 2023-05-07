package ac.at.fhcampuswien.carrental.entity.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Car {
    private Long id;
    private float dailyCost;
    private String brand;
    private String model;
    private String hp;
    private String buildDate;
    private String fuelType;
    private String imageLink;
}
