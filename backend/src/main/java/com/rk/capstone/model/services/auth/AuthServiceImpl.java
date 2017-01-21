package com.rk.capstone.model.services.auth;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Implementation of Auth Service
 */
@Service
public class AuthServiceImpl implements IAuthService{

    private int expirationHrs = 6;

    @Override
    public String getAuthToken(String userName) {
        return Jwts.builder().setSubject(userName).claim("roles", "user").
                setExpiration(generateExpirationDate()).setIssuedAt(new Date()).
                signWith(SignatureAlgorithm.HS256, "secretkey").compact();
    }

    private Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expirationHrs);
        return calendar.getTime();
    }
}
