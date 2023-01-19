package com.stackroute.userauthenticationservice.service;

import com.stackroute.userauthenticationservice.entity.JwtRequest;
import com.stackroute.userauthenticationservice.entity.UserCredentials;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public interface UserAuthenticationService extends UserDetailsService {
    String createJwtToken(JwtRequest jwtRequest) throws UsernameNotFoundException;

    Set<SimpleGrantedAuthority> getAuthorities(UserCredentials userCredentials);
}
