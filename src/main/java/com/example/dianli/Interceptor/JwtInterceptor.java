package com.example.dianli.Interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.dianli.Utils.JwtUtils;
import com.example.dianli.common.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author JiKuiXie
 * @Date 2023/06/01
 */
@Component
public class JwtInterceptor implements HandlerInterceptor{
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token= request.getHeader("token");
		Map<String, Object> map= new HashMap<>();
		if (token== null || token.trim().equals("")){
			map.put("message", "无token值!");
		} else{
			try{

				Integer id=JwtUtils.verfy(token).getClaim("id").asInt();
				System.out.println("id:"+id);
				request.setAttribute("id", id);
				return true;
			} catch (SignatureVerificationException e){
				e.printStackTrace();
				map.put("message", "签名不一致!");
			} catch (TokenExpiredException e){
				e.printStackTrace();
				map.put("message","令牌过期!");
			} catch (AlgorithmMismatchException e){
				e.printStackTrace();
				map.put("message", "算法不匹配!");
			} catch (InvalidClaimException e){
				e.printStackTrace();
				map.put("message","失效payload!");
			} catch (Exception e){
				e.printStackTrace();
				map.put("message","token无效");
			}
		}
		map.put("state", false);
		String json= JSON.toJSONString(map) ;
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(json);

		return false;


	}

}
