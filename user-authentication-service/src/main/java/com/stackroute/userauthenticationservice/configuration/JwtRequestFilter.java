package com.stackroute.userauthenticationservice.configuration;

import com.stackroute.userauthenticationservice.service.UserAuthenticationService;
import com.stackroute.userauthenticationservice.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class implements OncePerRequestFilter and extract the token from header and further verify it using the JWT utils and service classes
 */
@Component
@Generated
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserAuthenticationService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // extracting the header
        final String header = request.getHeader("Authorization");

        String jwtToken = null;
        String userName = null;

        // checking if the header is not null
        if (header != null && header.startsWith("Bearer ")) {
            // extracting the token
            jwtToken = header.substring(7);
            try {
                // extracting the username(email) from the jwt token
                userName = jwtUtil.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException exception) {
                log.error("Unable to get JWT token");
            } catch (ExpiredJwtException exception) {
                log.error("JWT token is expired");
            }
        } else {
            log.error("JWT Token does not start with Bearer");
        }
        // if the username is not null then validating the token
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                log.error("Token is expired !!");
            }
        } else {
            log.error("user name is null");
        }

        filterChain.doFilter(request, response);
    }
}
