package com.rk.capstone.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

/**
 * Filter for parsing auth-token header
 */
public class AuthTokenFilter extends GenericFilterBean{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authToken = request.getHeader("auth-token");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (authToken == null) {
                throw new ServletException("Missing or invalid Authorization Header");
            }
            try {
                final Claims claims = Jwts.parser().setSigningKey("secretkey").
                        parseClaimsJws(authToken).getBody();
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid Token");
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
