package ac.at.fhcampuswien.carrental.entity.models;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Rental {
    private Long id;
    private Long customerId;
    private Long carId;
    private LocalDate startDay;
    private LocalDate endDay;
    private float totalCost;
}



