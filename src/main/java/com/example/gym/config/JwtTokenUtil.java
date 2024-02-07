package com.example.gym.config;




import java.util.Date;


import javax.crypto.SecretKey;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Service
public class JwtTokenUtil {
	
	
	private static String secretKey="pt2WQDm9jMHRRbnv5iI6Hc69LBa8LwKI";
	
	
    private static  long expiration=864000000;
   

    // create token
    
 
    public static String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))// firma del token
                .compact();
    }
    
    
    
    // validate token
    public static boolean validateToken(String token) {
        try {
        	Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes())).build().parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
 
    
    

    
    
    //Extrac Username
	public String getUserNameFromJwtToken(String token) {
		
		 return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes())).build().parseSignedClaims(token).getPayload().getSubject();
		
	}
}



