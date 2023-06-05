package com.example.dianli.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author JiKuiXie
 * @Date 2023/06/05
 */


@Component
public class OkHttpUtil {
	private static final OkHttpClient client = new OkHttpClient();

	/**
	 * 发送GET请求
	 *
	 * @param url 请求地址
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doGet(String url) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	/**
	 * 发送POST请求
	 *
	 * @param url    请求地址
	 * @param params 请求参数
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doPost(String url, RequestBody params) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.post(params)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}


	public static void put(String json) throws IOException {
		String url = "http://106.14.125.121:8889/block/upload";
//		String json = "{\n" +
//				"    \"key\":7,\n" +
//				"    \"value\":{\n" +
//				"        \"l\":\"s\",\n" +
//				"        \"s\":\"l\"\n" +
//				"    }\n" +
//				"}";
		System.out.println(json);
		RequestBody body = RequestBody.create(json, okhttp3.MediaType.parse("application/json; charset=utf-8"));
		String res = doPost(url, body);
		System.out.println(res);
	}


//	@Test
	public static String query(String hash) throws IOException {
		String url = "http://106.14.125.121:8889/block/query";
		String param = "key="+hash;
		String jsonStr = doGet(url + "?" + param);

		System.out.println(jsonStr);  //没有解析的json字符串
		// 将json字符串解析为json对象
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		// 获取json对象中的data数组
		JSONObject data = jsonObj.getJSONObject("data");
		// 获取data数组中的第一个对象
//		JSONObject dataObj = data.getJSONObject("value");
		System.out.println(data);
		// 将json对象转换为字符串输出
		String json = JSON.toJSONString(data);
		System.out.println(json);
		// 将json字符串解析为json对象
		return json;

	}

}
