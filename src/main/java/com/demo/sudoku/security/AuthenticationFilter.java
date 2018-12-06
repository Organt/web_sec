package com.demo.sudoku.security;

import com.demo.sudoku.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Enumeration;
import java.util.Optional;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    TokenService tokenService;

    public AuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Optional<String> token = Optional.ofNullable(httpRequest.getHeader("MyToken"));

        SecurityContext context = SecurityContextHolder.getContext();

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication a = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("User name before token check:" + a.getName());
            // Password deleted by default
            // Order of filters matters
        }

        if (token.isPresent() && tokenService.exists(token.get())) {
            System.out.println("Token is: " + token.get());

            String username = tokenService.getUser(token.get());
            String password = tokenService.getPassowrd(token.get());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (username, password);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}