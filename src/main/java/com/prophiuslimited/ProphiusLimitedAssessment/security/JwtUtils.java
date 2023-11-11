package com.prophiuslimited.ProphiusLimitedAssessment.security;

import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.UserNotFoundException;
import com.prophiuslimited.ProphiusLimitedAssessment.exceptions.ValidationException;
import com.prophiuslimited.ProphiusLimitedAssessment.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${app.jwt_secret}")
     private String JWT_SECRET;

    private final UserRepository userRepository;
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public boolean hasClaim(String token, String claimName){
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }
    public Object getClaim(String token, String claimName){
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) ;
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        Claims claims;
        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String email, String userId) {
        Map<String, Object> claims = new HashMap<>();
      claims.put("userId", userId);
        return createToken(claims, email);
    }
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
