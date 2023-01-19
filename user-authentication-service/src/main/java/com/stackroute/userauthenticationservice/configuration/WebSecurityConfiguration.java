package com.stackroute.userauthenticationservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * This class acts as configuration for the spring security and it configure all the access to make APIs private and public
 * This class also handles exception anf generate a bean to encode the password using BCryptPasswordEncoder
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_URLS = {
            "/authenticate/generateToken",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**"
    };

    private static final String[] PRIVATE_URL_OWNER = {
            "/test/jwtTokenForOwner"
    };
    private static final String[] PRIVATE_URL_CUSTOMER = {
            "/test/jwtTokenForCustomer"
    };


    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UserDetailsService jwtService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * this function is configured to make APIs public/private and also handles the exception on authentication entry point
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors();

        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
                .antMatchers(HttpHeaders.ALLOW).permitAll()
                .antMatchers(PRIVATE_URL_OWNER).hasRole("OWNER")
                .antMatchers(PRIVATE_URL_CUSTOMER).hasRole("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            try {
                response.getWriter().write(new JSONObject()
                        .put("timestamp", LocalDateTime.now())
                        .put("message", "Access denied !! Please add valid JWT token starting with Bearer")
                        .toString());
            } catch (JSONException jsonException) {
                log.error(jsonException.getMessage());
            }
        });

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * this function act as bean and is used to encode the password using BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
    }

}
