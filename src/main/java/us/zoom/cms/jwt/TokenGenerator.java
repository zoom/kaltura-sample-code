/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.auth0.jwt.*;
import us.zoom.cms.exception.TokenException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;


/**
 * Created by kavithakannan on 4/24/18.
 */
public class TokenGenerator {

    private static Logger logger = LoggerFactory.getLogger(TokenGenerator.class);

    public static String generateJWT(String userName,String userId, String accountId, Boolean bln ) {
        String token = null;
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            Date afterAddingMins = new Date(nowMillis + (30 * ONE_MINUTE_IN_MILLIS));
            // 30 minutes
            token = JWT.create()
                    .withClaim("userName", userName)
                    .withClaim("userId", userId)
                    .withClaim("accountId",accountId)
                    .withClaim("canConfigureES",bln)
                    .withExpiresAt(afterAddingMins)
                    .withIssuer("kalturaconnector")
                    .sign(algorithm);
        } catch (UnsupportedEncodingException exception){
            new TokenException("Unable to generate token");
            //UTF-8 encoding not supported
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            new TokenException("Unable to generate token");
        }

        return token;
    }


    public static void main(String[] args){
        String token  = TokenGenerator.generateJWT("Kavitha", "12345", "accountId", true);

        System.out.println("Generated token = "+ token);
        Map mp = TokenParser.verifyToken(token);
        Claim claim = (Claim)mp.get("userName");
        System.out.println(claim.asString());

        System.out.println(mp);
    }


}
