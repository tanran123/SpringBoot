package com.ginko.driver.common.tolls;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;

public class TokenTools {
    public static Integer userId = 0;

    /**
     * 获取userId
     * @param token
     */
    public static void getUserIdFromToken(String token){
        DecodedJWT decode = JWT.decode(token);
        Claim userIdr = decode.getClaim("data");
        Map<String,Object> map = userIdr.asMap();
         userId = (Integer) map.get("userId");
    }
}
