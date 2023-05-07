package ac.at.fhcampuswien.apigateway.filter;

import ac.at.fhcampuswien.apigateway.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthentificationFilter implements HandlerInterceptor {

    JwtService jwtService;

    public AuthentificationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Adding .excludePathPatterns in AppConfig didn't work therefore this workaround.
        if (request.getRequestURL().toString().contains("customers") || request.getRequestURL().toString().contains("swagger-ui") || request.getRequestURL().toString().contains("v3")) {
            return true;
        }

        String accessToken = request.getHeader("Auth");

        jwtService.isTokenExpiredOrInvalid(accessToken);

        return true;
    }
}
