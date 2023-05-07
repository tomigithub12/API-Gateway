package ac.at.fhcampuswien.carrental.rest.controller;

import ac.at.fhcampuswien.carrental.entity.models.Currency;
import ac.at.fhcampuswien.carrental.exception.exceptions.CurrencyServiceNotAvailableException;
import ac.at.fhcampuswien.carrental.rest.models.CurrencyResponseDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/currencyCodes")
    @Operation(
            summary = "Lists all currency codes.",
            tags = {"Currency"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Currency.class))),
                    @ApiResponse(description = "Currency Service is not available!", responseCode = "500", content = @Content)
            })
    public ResponseEntity<CurrencyResponseDto> getAllCurrencies(@Valid @RequestHeader(value = "Auth") String token) throws CurrencyServiceNotAvailableException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth", token);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        List<String> currencyCodes = restTemplate.exchange("http://localhost:8081/api/v1/currency/currencyCodes", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<String>>() {
        }).getBody();
        CurrencyResponseDto currencyResponseDto = new CurrencyResponseDto();
        currencyResponseDto.setCurrencyCodes(currencyCodes);
        return ResponseEntity.ok(currencyResponseDto);
    }
}
