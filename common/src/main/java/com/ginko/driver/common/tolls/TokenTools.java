package com.ginko.driver.common.tolls;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;

public class TokenTools {
    public  Integer userId = 0;
    /**
     * 获取userId
     * @param token
     */
    public static Integer getUserIdFromToken(String tokenP){
        DecodedJWT decode = JWT.decode(tokenP);
        Claim userIdr = decode.getClaim("data");
        Map<String,Object> map = userIdr.asMap();
        return  (Integer) map.get("userId");
    }

}
