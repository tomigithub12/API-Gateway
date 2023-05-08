package ac.at.fhcampuswien.carrental.exception;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

public class ApiException {
    public String message = "";
    public HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    public ZonedDateTime timestamp = ZonedDateTime.now();

    public ApiException(String message,
                        HttpStatus httpStatus,
                        ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public ApiException() {}

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
