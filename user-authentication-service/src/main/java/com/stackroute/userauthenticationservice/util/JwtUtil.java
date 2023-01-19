package com.stackroute.userauthenticationservice.util;

import com.stackroute.userauthenticationservice.entity.UserCredentials;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Generated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class acts as JWT utility and provides function to generate token, validate token and extract username from token
 */
@Component
@Generated
public class JwtUtil {

    private static final String SECRET_KEY = "hotel_app";

    private static final int TOKEN_VALIDITY = 3600 * 5;

    /**
     * function to extract the username from the token
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * function to validate the token
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);
        return userDetails.getUsername().equals(userName) && !isTokenExpired(token);
    }

    /**
     * function to generate the JWT token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserCredentials userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Role",userDetails.getRole().getRoleName());
        return generateToken(userDetails, claims);

    }

    public Object extractRole(String token) {
        Claims claim = getAllClaimsFromToken(token);
        return claim.get("Role");
    }



    // helper function
    private String generateToken(UserCredentials userDetails, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getEmailId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // helper function
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    // helper function
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // helper function
    private boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    // helper function
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}
