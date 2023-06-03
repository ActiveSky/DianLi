package com.example.dianli.Controller;

import com.example.dianli.Entity.Seller;
import com.example.dianli.Service.SellerService;
import com.example.dianli.common.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author JiKuiXie
 * @Date 2023/05/26
 */

@RestController
@RequestMapping("/seller")
@Api(tags = "商家接口")

public class SellerController {

	@Autowired
	private SellerService sellerService;



	@ApiOperation(value = "更改商家信息", notes = "更改商家信息，将整个seller发过来")
	@PostMapping("/updateSellerMsg")
	public R updateSeller(@RequestBody Seller seller) {
		return sellerService.updateSellerMsgById(seller);
	}

	@ApiOperation(value = "上传图片返回图床url", notes = "上传图片返回图床url")
	@PostMapping("/updateSellerLogo")
	public R updateSellerLogo(@RequestPart("file")MultipartFile logoFile) {
		return sellerService.updateSellerLogoById(logoFile);
	}

	@ApiOperation(value = "根据id获取商家信息", notes = "根据id获取商家信息")
	@GetMapping("/getSellerMsg")
	public R getSellerMsg(@RequestParam("id") Integer id) {
		return sellerService.getSellerMsg(id);
	}

	@ApiOperation(value = "商家登录", notes = "商家登录,根据email和password,注意目前没有加token")
	@PostMapping("/login")
	public R login(@RequestParam("email") String email, @RequestParam("password") String password) {
		return sellerService.login(email, password);
	}

	@GetMapping("/getAllSeller")
	@ApiOperation(value = "获取所有商家", notes = "获取所有商家")
	public R getAllSeller() {
		return sellerService.getSellerList();
	}

}
