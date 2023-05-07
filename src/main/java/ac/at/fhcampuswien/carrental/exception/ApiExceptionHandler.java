package ac.at.fhcampuswien.carrental.exception;

import ac.at.fhcampuswien.carrental.exception.exceptions.*;
import ac.at.fhcampuswien.carrental.rest.models.ResponseMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    private final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Europe/Vienna"));

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiException> handleInvalidTokenException(InvalidTokenException invalidTokenException) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ApiException apiException = new ApiException(
                invalidTokenException.getMessage(),
                httpStatus,
                timestamp
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiException> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                invalidPasswordException.getMessage(),
                httpStatus,
                timestamp
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiException> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                customerNotFoundException.getMessage(),
                httpStatus,
                timestamp
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ApiException> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException customerAlreadyExistsException) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ApiException apiException = new ApiException(
                customerAlreadyExistsException.getMessage(),
                httpStatus,
                timestamp
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(CurrencyServiceNotAvailableException.class)
    public ResponseEntity<?> handleSOAPServiceConnectionError(CurrencyServiceNotAvailableException currencyServiceNotAvailableException) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(
                currencyServiceNotAvailableException.getMessage(),
                httpStatus,
                timestamp
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(CarNotAvailableException.class)
    public ResponseEntity<?> handleCarNotAvailablenError(CarNotAvailableException carNotAvailableException) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                carNotAvailableException.getMessage(),
                httpStatus,
                timestamp
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<?> handleCarNotFoundError(CarNotFoundException carNotFoundException) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                carNotFoundException.getMessage(),
                httpStatus,
                timestamp
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<?> handleBookingNotFoundError(BookingNotFoundException bookingNotFoundException) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                bookingNotFoundException.getMessage(),
                httpStatus,
                timestamp
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
