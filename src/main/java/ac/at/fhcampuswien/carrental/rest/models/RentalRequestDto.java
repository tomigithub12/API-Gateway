package ac.at.fhcampuswien.carrental.rest.models;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RentalRequestDto {
    private Long carId;
    private LocalDate startDay;
    private LocalDate endDay;
    private float totalCost;
    private String currentCurrency;
}
