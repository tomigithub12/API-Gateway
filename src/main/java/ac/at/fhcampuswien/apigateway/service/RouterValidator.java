package ac.at.fhcampuswien.apigateway.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpointsGet = List.of(
            "/api/v1/cars/**",
            "/api/v1/cars",
            "/api/v1/currency/**",
            "/api/v1/currency",
            "/api/v1/customers/**",
            "/api/v1/customers",
            "/api/v1/allBookings",
            "/api/v1/booking/**",
            "/api/v1/booking"
    );

    public static final List<String> openApiEndpointsPost= List.of(
            "/api/v1/customers/auth/login",
            "/api/v1/customers/auth/registration",
            "/api/v1/customers/auth/refreshtoken",
            "/api/v1/booking/**",
            "/api/v1/booking"

    );

    public static final Predicate<ServerHttpRequest> isSecuredGet =
            request -> openApiEndpointsGet
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri) && Objects.equals(request.getMethod(), HttpMethod.GET)
                    );

    public static final Predicate<ServerHttpRequest> isSecuredPost =
            request -> openApiEndpointsPost
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri) && Objects.equals(request.getMethod(), HttpMethod.POST)
                    );

}