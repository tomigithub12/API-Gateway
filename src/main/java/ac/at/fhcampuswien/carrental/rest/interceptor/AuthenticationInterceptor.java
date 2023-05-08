package ac.at.fhcampuswien.carrental.rest.interceptor;

import ac.at.fhcampuswien.carrental.exception.ApiException;
import ac.at.fhcampuswien.carrental.exception.exceptions.CustomerNotFoundException;
import ac.at.fhcampuswien.carrental.exception.exceptions.InvalidTokenException;
import ac.at.fhcampuswien.carrental.rest.models.LoginResponseDTO;
import ac.at.fhcampuswien.carrental.rest.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    RestTemplate restTemplate = new RestTemplate();
    private final String authUrl = "http://ec2-3-73-127-140.eu-central-1.compute.amazonaws.com:8080/api/v1/";

    public AuthenticationInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Adding .excludePathPatterns in AppConfig didn't work therefore this workaround.
        if (request.getRequestURL().toString().contains("customers") || request.getRequestURL().toString().contains("swagger-ui") || request.getRequestURL().toString().contains("v3")) {
            return true;
        }

        String accessToken = request.getHeader("Auth");

        try {
            restTemplate.getForObject(
                    this.authUrl + "auth/validation?token=" + accessToken,
                    Void.class
            );

            return true;
        } catch (HttpClientErrorException httpClientErrorException) {
            ApiException apiException = httpClientErrorException.getResponseBodyAs(ApiException.class);

            throw new InvalidTokenException(apiException.getMessage());
        }
    }
}
