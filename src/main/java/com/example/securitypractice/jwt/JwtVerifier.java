package com.example.securitypractice.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
    This is a filter for verifying the token validity at each user request (the token is placed in the Http request header).
    If the token is valid, the request and response is passed along to the filter chain.
*/

public class JwtVerifier extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String key = "9812hdwa9s78adghsajfbasf8912gfasgf6217fgbas-sadasd-sa";

        // extracting the token
        String authHeader = httpServletRequest.getHeader("Authorization");
        if(Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = authHeader.replace("Bearer ", "");

        try{
            // extracting data from the token (username and authorities)
            Jws<Claims> claimsJws =  Jwts.parserBuilder()
                    .setSigningKey(key.getBytes())
                    .build().parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();
            var authoritiesMap = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> authoritiesSet = authoritiesMap.stream()
                    .map(a -> new SimpleGrantedAuthority(a.get("authority")))
                    .collect(Collectors.toSet());

            // creating authentication based on the extracted data
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authoritiesSet
            );

            // setting the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(JwtException e) {
            throw new IllegalStateException("Invalid token: " + token);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
