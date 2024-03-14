package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import med.voll.api.domain.user.User;

@Service
public class TokenService {
    private static final String ISSUER = "API Voll.med";

    @Value("${api.security.token.secret}")
    private String secret;

    public String createToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getLogin())
                    .withExpiresAt(getExpirationDate())
                    .withClaim("id", user.getId()) // info key-value to be stored in the token
                    .withClaim("id2", user.getId()) // info key-value to be stored in the token
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("error creating a jwt token", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return  JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer(ISSUER)
                    // reusable verifier instance
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            throw new RuntimeException("Invalid/expired JWT Token: " + tokenJWT);
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
