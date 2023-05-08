package ac.at.fhcampuswien.carrental.rest.controller;

import ac.at.fhcampuswien.carrental.entity.models.Currency;
import ac.at.fhcampuswien.carrental.exception.ApiException;
import ac.at.fhcampuswien.carrental.exception.exceptions.CurrencyServiceNotAvailableException;
import ac.at.fhcampuswien.carrental.rest.models.CurrencyResponseDto;
import ac.at.fhcampuswien.carrental.rest.models.RefreshTokenResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Validated
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/v1/currency")
@Tag(name = "Currency", description = "Endpoints for managing currency.")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CurrencyController {

    RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://ec2-52-59-248-197.eu-central-1.compute.amazonaws.com:8080/api/v1/";

    @GetMapping("/currencyCodes")
    @Operation(
            summary = "Lists all currency codes.",
            tags = {"Currency"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Currency.class))),
                    @ApiResponse(description = "Currency Service is not available!", responseCode = "500", content = @Content)
            })
    public ResponseEntity<?> getAllCurrencies(@Valid @RequestHeader(value = "Auth") String token) throws CurrencyServiceNotAvailableException {
        try {
            String currencyCodesJson = restTemplate.getForObject(url + "currency/currencyCodes", String.class);
            JSONObject currencyCodes = new JSONObject(currencyCodesJson);
            CurrencyResponseDto currencyResponseDto = new CurrencyResponseDto();
            currencyResponseDto.setCurrencyCodes(currencyCodes.getJSONArray("currencyCodes").toList().stream().map(Object::toString).toList());
            return ResponseEntity.ok(currencyResponseDto);
        } catch (HttpClientErrorException httpClientErrorException) {
            ApiException apiException = httpClientErrorException.getResponseBodyAs(ApiException.class);

            return new ResponseEntity<>(apiException, apiException.httpStatus);
        }
    }
}
