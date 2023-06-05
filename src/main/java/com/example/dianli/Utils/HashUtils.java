package com.example.dianli.Utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class HashUtils {

	/**
	 * 对数据进行hash加密
	 *
	 * @param data 待加密数据
	 * @return 加密后的结果
	 */
	public static String hash(String data) {
		Digester digester = new Digester(DigestAlgorithm.MD5);
		String newData = data + new Date().getTime();
		System.out.println(newData);
		return digester.digestHex(newData);
	}

	@Test
	public void test() {
		System.out.println(hash("123456"));
		System.out.println(new Date());
	}

}