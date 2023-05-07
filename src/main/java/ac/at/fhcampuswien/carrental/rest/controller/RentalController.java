package ac.at.fhcampuswien.carrental.rest.controller;


import ac.at.fhcampuswien.carrental.entity.models.Car;
import ac.at.fhcampuswien.carrental.entity.models.Rental;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/allBookings")
    @Operation(
            summary = "Lists all bookings.",
            tags = {"Bookings"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))),
                    @ApiResponse(description = "Currency Service is not available!", responseCode = "500", content = @Content)
            })
    public ResponseEntity<List<RentalResponseDtoWithCar>> getBookings(@Valid @RequestHeader(value = "Auth") String token,
                                                                      @RequestParam String currentCurrency,
                                                                      HttpServletRequest request) throws CurrencyServiceNotAvailableException {
        //TODO check jwt token rest call

        String url = "http://localhost:8081/api/v1/allBookings?currentCurrency=" + currentCurrency;
        List<RentalResponseDtoWithCar> rentalResponseDtosWithCar = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(url, RentalResponseDtoWithCar[].class))).toList();

        return new ResponseEntity<>(rentalResponseDtosWithCar, HttpStatus.OK);
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
    public ResponseEntity<RentalResponseDto> createBooking(@Valid @RequestHeader(value = "Auth") String token,
                                                           @RequestBody RentalRequestDto rentalBooking,
                                                           HttpServletRequest request) throws CarNotAvailableException, CurrencyServiceNotAvailableException {
        //TODO check jwt token rest call

        String url = "http://localhost:8081/api/v1/booking";

        RentalResponseDto rentalResponseDto = restTemplate.postForObject(url, rentalBooking, RentalResponseDto.class);
        return new ResponseEntity<>(rentalResponseDto, HttpStatus.CREATED);
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
    public void removeBooking(@Valid @RequestHeader(value = "Auth") String token, @Valid @PathVariable Long bookingId) {
        //TODO check jwt token rest call

        String url = "http://localhost:8081/api/v1/booking/" + bookingId;
        restTemplate.delete(url);
    }
}
