package ac.at.fhcampuswien.carrental.rest.controller;


import ac.at.fhcampuswien.carrental.exception.ApiException;
import ac.at.fhcampuswien.carrental.exception.exceptions.CustomerAlreadyExistsException;
import ac.at.fhcampuswien.carrental.exception.exceptions.CustomerNotFoundException;
import ac.at.fhcampuswien.carrental.exception.exceptions.InvalidPasswordException;
import ac.at.fhcampuswien.carrental.exception.exceptions.InvalidTokenException;
import ac.at.fhcampuswien.carrental.rest.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nonapi.io.github.classgraph.json.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.json.JSONException;
import org.json.JSONObject;

@Validated
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/v1/customers/")
@Tag(name = "Customers", description = "Endpoints for managing customers")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerController {

    RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://ec2-18-184-54-212.eu-central-1.compute.amazonaws.com:8080/api/v1/";
    private final String authUrl = "http://ec2-3-73-127-140.eu-central-1.compute.amazonaws.com:8080/api/v1/";

    @PostMapping("/auth/login")
    @Operation(
            summary = "Customer Login.",
            tags = {"Customers"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class))),
                    @ApiResponse(description = "Email or password is incorrect.", responseCode = "400", content = @Content)
            })
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginData) throws InvalidPasswordException, CustomerNotFoundException {
        try {
            LoginResponseDTO loginResponseDTO = restTemplate.postForObject(
                    this.url + "customers/auth/login",
                    loginData,
                    LoginResponseDTO.class
            );
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
        } catch (HttpClientErrorException httpClientErrorException) {
            ApiException apiException = httpClientErrorException.getResponseBodyAs(ApiException.class);

            return new ResponseEntity<>(apiException, apiException.httpStatus);
        }
    }

    @PostMapping("/auth/registration")
    @Operation(
            summary = "Creates a customers in the database.",
            tags = {"Customers"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationResponseDto.class))),
                    @ApiResponse(description = "A user for this email is already existing. Please try to log in.", responseCode = "409", content = @Content)
            })
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDto registrationRequestDto) throws CustomerAlreadyExistsException {
        try {
            RegistrationResponseDto registrationResponseDto = restTemplate.postForObject(
                    this.url + "customers/auth/registration",
                    registrationRequestDto,
                    RegistrationResponseDto.class
            );

            return new ResponseEntity<>(registrationResponseDto, HttpStatus.CREATED);
        } catch (HttpClientErrorException httpClientErrorException) {
            ApiException apiException = httpClientErrorException.getResponseBodyAs(ApiException.class);

            return new ResponseEntity<>(apiException, apiException.httpStatus);
        }
    }

    @PostMapping("/auth/refreshtoken")
    @Operation(
            summary = "Refresh access token.",
            tags = {"Customers"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenDTO.class))),
                    @ApiResponse(description = "Invalid Token", responseCode = "401", content = @Content)
            })
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenDTO token) throws InvalidTokenException, CustomerNotFoundException {
        try {
            String accessTokenJson = restTemplate.postForObject(
                    this.authUrl + "auth/refreshtoken",
                    token,
                    String.class
            );
            JSONObject jsonObject = new JSONObject(accessTokenJson);
            RefreshTokenResponseDTO refreshTokenResponseDTO = new RefreshTokenResponseDTO(jsonObject.getString("accessToken"));
            return new ResponseEntity<>(refreshTokenResponseDTO, HttpStatus.OK);
        } catch (HttpClientErrorException httpClientErrorException) {
            ApiException apiException = httpClientErrorException.getResponseBodyAs(ApiException.class);

            return new ResponseEntity<>(apiException, apiException.httpStatus);
        }
    }
}
