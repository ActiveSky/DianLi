package com.example.dianli.Service.Impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dianli.Entity.Customer;
import com.example.dianli.Entity.Seller;
import com.example.dianli.Mapper.SellerMapper;
import com.example.dianli.Service.SellerService;
import com.example.dianli.Utils.JwtUtils;
import com.example.dianli.common.R;
import com.example.dianli.common.constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author JiKuiXie
 * @Date 2023/05/26
 */
@Service
public class SellerServiceImpl extends ServiceImpl<SellerMapper, Seller> implements SellerService {

	@Autowired
	private SellerMapper sellerMapper;
	@Autowired
	private OSSClient ossClient;
	@Autowired
	private Environment env;


	@Override
	public R updateSellerMsgById(Seller seller) {
//		UpdateWrapper<Seller> updatewapper = new UpdateWrapper<>();
//		updatewapper.eq("name", seller.getSeller_name());
		if (sellerMapper.updateById(seller) > 0) {
			return R.success("修改成功");
		} else {
			return R.error("修改失败");
		}
	}

	@Override
	public R updateSellerLogoById(MultipartFile logoFile) {
//		System.out.println(id);
		//1、创建oss客户端连接
		String url = null;
		try {
			//2、获取文件输入流
			InputStream inputStream = logoFile.getInputStream();
			//3、获取原始文件名
			String originFileName = logoFile.getOriginalFilename();
			//4、防止文件重名
			String uuidFileName = cn.hutool.core.lang.UUID.randomUUID().toString(true) + originFileName;
			//4.1、精确到日
			String dateTime = DateTime.now().toString("yyyy-MM-dd");
			//5、拼接文件名
			String realFileName = dateTime + uuidFileName;
			//拼接dir根目录
			String dirFileName = env.getProperty("aliyun.oss.dir.prefix") + realFileName;

			//6、创建oss请求，传入三个参数
			ossClient.putObject(env.getProperty("aliyun.oss.bucketName"), dirFileName, inputStream);

			//7、拼接图片url路径，方便后续入库
			url = "https://" + env.getProperty("aliyun.oss.bucketName") + "." + env.getProperty("aliyun.oss.endpoint") + "/" + dirFileName;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭client连接
			ossClient.shutdown();
		}

		Map<String, Object> map = new HashMap<>();
		System.out.println(url);
		map.put("url", url);
		JSONObject jsonObject = (JSONObject) JSON.toJSON(map);


		return R.success("上传成功", jsonObject);
	}

	public R getSellerMsg(Integer id) {
		Seller seller = sellerMapper.selectById(id);

		JSONObject jsonObject = (JSONObject) JSON.toJSON(seller);
		jsonObject.remove("sellerPassword");
//		System.out.println("这是http://localhost:8888/customer/getCustomerMsg调用的方法返回值:");
		System.out.println(jsonObject);

		if (seller != null) {
			return R.success("查询成功", jsonObject);
		} else {
			return R.error("查询失败");
		}
	}

	@Override
	public R login(String email, String password) {
		LambdaQueryWrapper<Seller> queryWrapper = new LambdaQueryWrapper<Seller>();
		queryWrapper.eq(Seller::getSellerEmail, email);
		queryWrapper.eq(Seller::getSellerPassword, password);
		Seller seller = sellerMapper.selectOne(queryWrapper);
		if (seller != null) {
			Map<String, Integer> tokenmap = new HashMap<>();
			tokenmap.put("id", seller.getSellerId());
			String token = JwtUtils.getToken(tokenmap);

			JSONObject jsonObject = (JSONObject) JSON.toJSON(seller);
			jsonObject.put("token", token);  //加入token
			jsonObject.remove("customerPassword");
			System.out.println(jsonObject);

			return R.success("登录成功", jsonObject);

		} else {
			return R.error("登录失败");
		}

	}

	@Override
	public R getSellerList() {

		JSONArray jsonArray = new JSONArray();
		for (Seller seller : sellerMapper.selectList(null)) {
			JSONObject jsonObject = (JSONObject) JSON.toJSON(seller);
			jsonObject.remove("sellerPassword");
			jsonArray.add(jsonObject);
		}
		return R.success("查询成功", jsonArray);
	}

	public R getSellerByName(String name) {
		LambdaQueryWrapper<Seller> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper
				.eq(Seller::getSellerName, name);  // 仅返回指定列
		Seller seller = sellerMapper.selectOne(queryWrapper);
		if (seller != null) {
			JSONObject jsonObject = (JSONObject) JSON.toJSON(seller);
			jsonObject.remove("sellerPassword");
			System.out.println(jsonObject);
			return R.success("查询成功", jsonObject);
		} else {
			return R.error("查询失败");
		}

	}

}
