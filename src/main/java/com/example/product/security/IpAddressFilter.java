package com.example.product.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class IpAddressFilter extends OncePerRequestFilter {

    // Allowed IPs (IPv4 and IPv6 for localhost)
    private static final List<String> ALLOWED_IPS = List.of(
            "127.0.0.1",       // IPv4 localhost
            "0:0:0:0:0:0:0:1"  // IPv6 localhost
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        // Get client IP address, support proxy header if available
        String clientIp = getClientIp(request);
        System.out.println("Incoming request from IP: " + clientIp);

        // Block if not in allowed list
        if (!ALLOWED_IPS.contains(clientIp)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        // Continue the request
        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isEmpty()) {
            return forwarded.split(",")[0]; // First IP in the list
        }
        return request.getRemoteAddr();
    }
}