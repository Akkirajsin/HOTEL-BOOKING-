package com.ums.SERVICE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ums.ENTITY.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.Key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expireDuration}")
    private int expireTime;

    private Algorithm algorithm;

    private final static String USER_NAME="name";

    @PostConstruct
    public void postConstruct(){
      algorithm=  Algorithm.HMAC256(algorithmKey);

    }
    public String generateToken(AppUser user){
      return  JWT.create().withClaim(USER_NAME,user.getName())
              .withExpiresAt(new Date(System.currentTimeMillis()+expireTime))
        .withIssuer(issuer)
              .sign(algorithm);
    }
    public  String getUserName(String token){
      DecodedJWT decodedJWT= JWT.require(algorithm).withIssuer(issuer).build().verify(token);
       return decodedJWT.getClaim(USER_NAME).asString();
    }






    }




