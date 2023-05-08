package ac.at.fhcampuswien.carrental.rest.controller;


import ac.at.fhcampuswien.carrental.entity.models.Car;
import ac.at.fhcampuswien.carrental.entity.models.Rental;
import ac.at.fhcampuswien.carrental.exception.ApiException;
import ac.at.fhcampuswien.carrental.exception.exceptions.BookingNotFoundException;
import ac.at.fhcampuswien.carrental.exception.exceptions.CarNotAvailableException;
import ac.at.fhcampuswien.carrental.exception.exceptions.CurrencyServiceNotAvailableException;
import ac.at.fhcampuswien.carrental.rest.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "Bookings", description = "Endpoints for managing bookings.")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RentalController {

    RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://ec2-52-59-188-132.eu-central-1.compute.amazonaws.com:8080/api/v1/";
    private final String authUrl = "http://ec2-3-73-127-140.eu-central-1.compute.amazonaws.com:8080/api/v1/";

    @GetMapping("/allBookings")
    @Operation(
            summary = "Lists all bookings.",
            tags = {"Bookings"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))),
                    @ApiResponse(description = "Currency Service is not available!", responseCode = "500", content = @Content)
            })
    public ResponseEntity<?> getBookings(@Valid @RequestHeader(value = "Auth") String token,
                                                                      @RequestParam String currentCurrency,
                                                                      HttpServletRequest request) throws CurrencyServiceNotAvailableException {
        try {
            String emailJson = restTemplate.getForObject(this.authUrl + "auth/eMail?token=" + token, String.class);
            JSONObject email = new JSONObject(emailJson);

            String url = this.url + "allBookings?eMail=" + email.getString("email") + "&currentCurrency=" + currentCurrency;
            RentalResponseDtoWithCar[] rentalResponseDtosWithCar = restTemplate.getForObject(url, RentalResponseDtoWithCar[].class);

            return new ResponseEntity<>(rentalResponseDtosWithCar, HttpStatus.OK);
        } catch (HttpClientErrorException httpClientErrorException) {
            ApiException apiException = httpClientErrorException.getResponseBodyAs(ApiException.class);

            return new ResponseEntity<>(apiException, apiException.httpStatus);
        }
    }

    @PostMapping("/booking")
    @Operation(
            summary = "Creates a booking in the database.",
            tags = {"Bookings"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDto.class))),
                    @ApiResponse(description = "This Car is not available in this time period!", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Currency Service is not available!", responseCode = "500", content = @Content)
            })
    public ResponseEntity<?> createBooking(@Valid @RequestHeader(value = "Auth") String token,
                                                           @RequestBody RentalRequestDto rentalBooking,
                                                           HttpServletRequest request) throws CarNotAvailableException, CurrencyServiceNotAvailableException {

        try {
            String emailJson = restTemplate.getForObject(this.authUrl + "auth/eMail?token=" + token, String.class);
            JSONObject email = new JSONObject(emailJson);

            String url = this.url + "booking?eMail=" + email.getString("email");

            RentalResponseDto rentalResponseDto = restTemplate.postForObject(url, rentalBooking, RentalResponseDto.class);
            return new ResponseEntity<>(rentalResponseDto, HttpStatus.CREATED);
        } catch (HttpClientErrorException httpClientErrorException) {
            ApiException apiException = httpClientErrorException.getResponseBodyAs(ApiException.class);

            return new ResponseEntity<>(apiException, apiException.httpStatus);
        }
    }

    /*@PutMapping("/booking")
    @Operation(
            summary = "Update a booking in the database.",
            tags = {"Bookings"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDto.class))),
                    @ApiResponse(description = "Booking with this ID does not exist!", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Currency Service is not available!", responseCode = "500", content = @Content)
            })
    public ResponseEntity<RentalUpdateResponseDto> updateBooking(@Valid @RequestHeader(value = "Auth") String token,
                                                                 RentalUpdateRequestDto rentalUpdateRequestDto) throws CurrencyServiceNotAvailableException, BookingNotFoundException {
        //TODO check jwt token rest call

        String url = "http://localhost:8081/api/v1/booking";

        // .put does not have a return value
        RentalUpdateResponseDto rentalUpdateResponseDto = restTemplate.put(url, rentalUpdateRequestDto, RentalUpdateResponseDto.class);
        return new ResponseEntity<>(rentalUpdateResponseDto, HttpStatus.OK);
    }*/

    @DeleteMapping("/booking/{bookingId}")
    @Operation(summary = "Delete booking entry from database.", tags = {"Bookings"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBooking(@Valid @RequestHeader(value = "Auth") String token, @Valid @PathVariable String bookingId) {
        String url = this.url + "booking/" + bookingId;
        restTemplate.delete(url);
    }
}
