package com.stackroute.userauthenticationservice.service.impl;

import com.stackroute.userauthenticationservice.entity.JwtRequest;
import com.stackroute.userauthenticationservice.entity.UserCredentials;
import com.stackroute.userauthenticationservice.repository.UserCredentialsRepository;
import com.stackroute.userauthenticationservice.service.UserAuthenticationService;
import com.stackroute.userauthenticationservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * this class acts as JwtService and it implements the UserDetailsService from org.springframework.security.core.userdetails
 */
@Service
public class UserAuthenticationServiceImpl implements UserDetailsService, UserAuthenticationService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * this method extract the user details using the username in this case email and then returns the new user where User is a
     * predefined class from org.springframework.security.core.userdetails
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCredentials user = userCredentialsRepository.findById(email).orElse(null);
        if (user != null) {
            return new User(user.getEmailId(), user.getPassword(), getAuthorities(user));
        } else {
            throw new UsernameNotFoundException("Email or password is not valid");
        }
    }

    /**
     * this method is used to create the jwt token by using the jwt utility
     *
     * @param jwtRequest
     * @return
     * @throws Exception
     */
    @Override
    public String createJwtToken(JwtRequest jwtRequest) throws UsernameNotFoundException {
        try {
            // authentication based on email and password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmailId(), jwtRequest.getPassword()));
            final UserCredentials userCredentials = userCredentialsRepository.findById(jwtRequest.getEmailId()).get();
            // generating the token
            return jwtUtil.generateToken(userCredentials);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    /**
     * helper method to extract all the Roles of the given user and then return a SET of Roles
     *
     * @param userCredentials
     * @return
     */
    @Override
    public Set<SimpleGrantedAuthority> getAuthorities(UserCredentials userCredentials) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userCredentials.getRole().getRoleName()));
        return authorities;
    }
}