package com.example.securitypractice.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

/*
    This is a filter for username and password authentication, in short it checks whether the passed
    parameters have a match in the user database and if yes, it puts a JWT into the response header
*/

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

/*
    This method maps the JSON passed in the request (username and password) into authentication request (my custom class)
    to then pass it to authenticationManager and attempt authentication
*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

/*
    This method is called when the authentication request is passed successfully, it constructs the token and puts it
    in the response header.
*/
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String key = "9812hdwa9s78adghsajfbasf8912gfasgf6217fgbas-sadasd-sa";
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
