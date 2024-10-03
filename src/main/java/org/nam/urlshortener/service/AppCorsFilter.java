package org.nam.urlshortener.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
@Slf4j
public class AppCorsFilter extends CorsFilter {

    public static final String API_METHODS = "POST, GET, OPTIONS, DELETE";

//    public static final List<String> ALLOWED_ORIGINS = List.of("http://localhost:3001", "https://shorten.haina.id.vn");

    public AppCorsFilter(CorsConfigurationSource configSource) {
        super(configSource);
        log.info("AppCorsFilter created");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Enumeration<String> headerNames = request.getHeaderNames();
        log.info("----------------- Filtering request headers");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("{} = {}", headerName, request.getHeader(headerName));
        }

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", API_METHODS);
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Authorization");

        filterChain.doFilter(request, response);
    }

}