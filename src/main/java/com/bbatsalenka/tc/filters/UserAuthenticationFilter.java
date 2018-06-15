package com.bbatsalenka.tc.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.NestedServletException;

import com.bbatsalenka.tc.dtos.ResponseDecorator;
import com.bbatsalenka.tc.dtos.ResponseDecorator.Status;
import com.bbatsalenka.tc.exceptions.AuthenticationException;
import com.bbatsalenka.tc.services.JWTAuthenticationService;
import com.bbatsalenka.tc.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Log
    private Logger logger;

    @Autowired
    private JWTAuthenticationService jWTAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
    		throws IOException, ServletException {
        try {
            Authentication authentication = jWTAuthenticationService.getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (AuthenticationException | NestedServletException e){
        	logger.warn("Authentication exception ", e);
        	ResponseDecorator<?> responseDecorator = new ResponseDecorator<>();
        	responseDecorator.setStatus(Status.ERROR);
            responseDecorator.setMessage("Authentication failed");
            sendAuthenticationErrorMessage(responseDecorator, response);
        } finally {
        	SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
    
    private void sendAuthenticationErrorMessage(ResponseDecorator<?> responseDecorator, HttpServletResponse response){
        ObjectMapper mapper = new ObjectMapper();
        try {
        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(mapper.writeValueAsString(responseDecorator));
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("Failed to print response" + e.getMessage());
        }
    }

}
