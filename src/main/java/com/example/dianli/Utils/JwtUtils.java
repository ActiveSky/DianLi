package com.example.dianli.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

/**
 * @Author JiKuiXie
 * @Date 2023/06/01
 */

@Component
public class JwtUtils {
	public static String SECRET= "JiKuiXie";

	public static String getToken(Map<String, Integer> map){
		JWTCreator.Builder builder= JWT.create();
		map.forEach(builder::withClaim);
		Calendar instance= Calendar.getInstance();
		instance.add(Calendar.HOUR, 24*7);  //默认7*24小时过期

		builder.withExpiresAt(instance.getTime());
		String token= builder.sign(Algorithm.HMAC256(SECRET));
		return token;
	}

	public static DecodedJWT verfy(String token){
		DecodedJWT decodedJWT= JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
		return decodedJWT;
	}

}
