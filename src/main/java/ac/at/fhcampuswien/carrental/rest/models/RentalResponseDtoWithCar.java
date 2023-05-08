package ac.at.fhcampuswien.carrental.rest.models;

import ac.at.fhcampuswien.carrental.entity.models.Car;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RentalResponseDtoWithCar {
    private String id;
    private String customerId;
    private Car car;
    private LocalDate startDay;
    private LocalDate endDay;
    private float totalCost;
}