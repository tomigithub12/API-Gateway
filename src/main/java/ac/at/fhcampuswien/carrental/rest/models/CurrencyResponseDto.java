package ac.at.fhcampuswien.carrental.rest.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@Setter
@Getter
public class CurrencyResponseDto {

    @NotBlank
    @NotEmpty
    private List<String> currencyCodes;
}

