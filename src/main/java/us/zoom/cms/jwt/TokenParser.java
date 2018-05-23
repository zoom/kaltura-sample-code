/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.zoom.cms.exception.TokenException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kavithakannan on 4/24/18.
 */
public class TokenParser {
    private static Logger logger = LoggerFactory.getLogger(TokenParser.class);

   public static Map verifyToken(String token) {
       Map map = new HashMap<String,String>();
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("kalturaconnector")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            map = jwt.getClaims();

        } catch (UnsupportedEncodingException exception){
            throw new TokenException("Invalid  Token");

        } catch (JWTVerificationException exception){
            throw new TokenException("Invalid  Token");
        }
        return map;
    }

}
