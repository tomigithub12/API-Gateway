package ac.at.fhcampuswien.apigateway.service;

import java.util.Date;


public class JwtService {

    public static final String SECRET = "58703273357638792F423F4528472B4B6250655368566D597133743677397A24";

    public enum Token {
        AccessToken,
        RefreshToken
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);


    }

}
