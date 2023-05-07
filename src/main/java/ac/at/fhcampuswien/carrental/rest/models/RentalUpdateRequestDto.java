package ac.at.fhcampuswien.carrental.rest.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Builder
@Setter
@Getter
public class RentalUpdateRequestDto {

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

    @NotBlank
    @NotEmpty
    private String currentCurrency;



}
