package com.book.book_network.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;

@Service
@RequiredArgsConstructor
public class jwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if(request.getServletPath().contains("/api/v1/auth")){
            filterChain.doFilter(request,response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){//bearer token
            filterChain.doFilter(request,response);
            return;
            //no longer need to execute the rest of the code

        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication() == null){ //we check that the user is not already authenticated
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail); // fetch the userEmail from the DB
            if(jwtService.isTokenValid(jwt, userDetails)){
                //used by spring security to check authenticatin holder later on
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)

                );
                SecurityContextHolder.getContext().setAuthentication(authToken);//saljneh c bon el user(userDetails)

            }
        }
        filterChain.doFilter(request,response);


    }
}
