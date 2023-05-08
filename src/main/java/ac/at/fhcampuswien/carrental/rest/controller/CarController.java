package ac.at.fhcampuswien.carrental.rest.controller;


import ac.at.fhcampuswien.carrental.entity.models.Car;
import ac.at.fhcampuswien.carrental.exception.ApiException;
import ac.at.fhcampuswien.carrental.exception.exceptions.CarNotAvailableException;
import ac.at.fhcampuswien.carrental.exception.exceptions.CarNotFoundException;
import ac.at.fhcampuswien.carrental.exception.exceptions.CurrencyServiceNotAvailableException;
import ac.at.fhcampuswien.carrental.exception.exceptions.InvalidTokenException;
import ac.at.fhcampuswien.carrental.rest.models.CarListDTO;
import ac.at.fhcampuswien.carrental.rest.models.CurrencyResponseDto;
import ac.at.fhcampuswien.carrental.rest.models.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Validated
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/v1/cars")
@Tag(name = "Cars", description = "Endpoints for managing car inventory")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CarController {

    RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://ec2-3-76-36-50.eu-central-1.compute.amazonaws.com:8080/api/v1/";

    @GetMapping()
    @Operation(
            summary = "Lists all available cars.",
            tags = {"Cars"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
                    @ApiResponse(description = "No cars available in this time period", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Currency Service is not available!", responseCode = "500", content = @Content)
            })
    public ResponseEntity<?> listAvailableCars(@Valid @RequestHeader(value = "Auth") String token,
                                                              @RequestParam("currentCurrency") String currentCurrency,
                                                              @RequestParam("chosenCurrency") String chosenCurrency,
                                                              @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                                              @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) throws CarNotAvailableException, CurrencyServiceNotAvailableException {
        try {
            Car[] allAvailableCars = restTemplate.getForObject(this.url + "cars?currentCurrency=" + currentCurrency +  "&chosenCurrency=" + chosenCurrency + "&from=" + from + "&to=" + to, Car[].class);

            return new ResponseEntity<>(allAvailableCars, HttpStatus.OK);
        } catch (HttpClientErrorException httpClientErrorException) {
            ApiException apiException = httpClientErrorException.getResponseBodyAs(ApiException.class);

            return new ResponseEntity<>(apiException, apiException.httpStatus);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lists specific car.",
            tags = {"Cars"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
                    @ApiResponse(description = "The Car with this ID could not be found!", responseCode = "404", content = @Content)
            })
    public ResponseEntity<?> getCar(@Valid @RequestHeader(value = "Auth") String token,
                                                              @PathVariable("id") long carId) throws CarNotFoundException {
        try {
            String url = this.url + "cars/" + carId;
            Car specificCar = restTemplate.getForObject(url, Car.class);

            return new ResponseEntity<>(specificCar, HttpStatus.OK);
        } catch (HttpClientErrorException httpClientErrorException) {
            ApiException apiException = httpClientErrorException.getResponseBodyAs(ApiException.class);

            return new ResponseEntity<>(apiException, apiException.httpStatus);
        }
    }
}
