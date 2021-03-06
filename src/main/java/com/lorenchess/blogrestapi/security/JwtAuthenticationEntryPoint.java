package com.lorenchess.blogrestapi.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is call whenever an exception is thrown due to an unauthenticated user tries to access a resource
     * that  requires authentication
     * */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
       response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
