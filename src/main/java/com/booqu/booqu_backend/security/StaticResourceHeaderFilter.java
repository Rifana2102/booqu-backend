package com.booqu.booqu_backend.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StaticResourceHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        res.setHeader("Cross-Origin-Resource-Policy", "cross-origin");

        chain.doFilter(request, response);
    }
}