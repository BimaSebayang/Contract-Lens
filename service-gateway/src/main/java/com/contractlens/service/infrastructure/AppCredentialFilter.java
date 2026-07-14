package com.contractlens.service.infrastructure;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("DataFlowIssue")
@Component
public class AppCredentialFilter extends OncePerRequestFilter {

    private static final Logger log =
            LoggerFactory.getLogger(AppCredentialFilter.class);




    public AppCredentialFilter() {

    }


    @SuppressWarnings("java:S2259")
    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {



        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        "anyuser",
                        "anykey",
                        List.of(new SimpleGrantedAuthority("APPLICATION_STICK"))
                );


        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);



    }


}
