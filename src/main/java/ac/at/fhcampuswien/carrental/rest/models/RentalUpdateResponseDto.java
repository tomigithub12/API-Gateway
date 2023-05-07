package ac.at.fhcampuswien.carrental.rest.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RentalUpdateResponseDto {
    @NotBlank
    @NotEmpty
    private Long id;

    @NotBlank
    @NotEmpty
    private Long carId;

    @NotBlank
    @NotEmpty
    private LocalDate startDay;

    @NotBlank
    @NotEmpty
    private LocalDate endDay;

    @NotBlank
    @NotEmpty
    private float totalCost;

}
