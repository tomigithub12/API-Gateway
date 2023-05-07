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
public class RentalResponseDto {

    private String eMail;
    private Long carId;
    private LocalDate startDay;
    private LocalDate endDay;
    private float totalCost;
}
