package ac.at.fhcampuswien.apigateway.config;

import ac.at.fhcampuswien.apigateway.filter.AuthentificationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    AuthentificationFilter filter;

    public GatewayConfig(AuthentificationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AUTHENTICATION", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTHENTICATION-SERVICE/"))

                .route("CAR", r -> r.path("/api/v1/cars/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://CAR-SERVICE/"))

                .route("ORDER", r -> r.path("/api/v1/booking/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://BOOKING-SERVICE/"))

                .route("CURRENCY", r -> r.path("/api/v1/currency/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://CURRENCY-SERVICE/"))

                .build();
    }

}

