package com.example.dianli.Controller;


import com.example.dianli.Entity.Customer;
import com.example.dianli.Entity.Seller;
import com.example.dianli.Service.CustomerService;
import com.example.dianli.Service.SellerService;
import com.example.dianli.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Consumer;

/**
 * @Author JiKuiXie
 * @Date 2023/05/30
 */
@RestController
@RequestMapping("/customer")
@Api(tags = "客户接口")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SellerService sellerService;

	/**
	 * 上传图片返回图床url
	 *
	 * @param logoFile
	 *
	 * @return R
	 */

	@ApiOperation(value = "上传图片返回图床url", notes = "上传图片返回图床url")
	@PostMapping("/updateCustomerLogo")
	public R updateCustomerMsg(@RequestPart("file") @ApiParam(value = "图片文件") MultipartFile logoFile) {
		return customerService.updateCustomerLogo(logoFile);
	}

	/**
	 * 更改客户信息，根据id
	 *
	 * @param customer
	 */
	@ApiOperation(value = "更改客户信息", notes = "将整个customer(包含logoPath)发过来,")
	@PostMapping("/updateCustomerMsg")
	public R updateCustomerMsg(@RequestBody @ApiParam(value = "customer类") Customer customer) {
		return customerService.updateCustomerMsg(customer);
	}

	@ApiOperation(value = "根据id获取客户信息", notes = "进入'个人信息页'时调用")
	@GetMapping("/getCustomerMsg")
	public R getCustomerMsg(@RequestParam("id") @ApiParam(value = "客户id") Integer id) {
		return customerService.getCustomerById(id);
	}

	@ApiOperation(value = "客户登录", notes = "客户登录,根据email和password,注意目前没有加token")
	@PostMapping("/login")
	public R login(@RequestParam("email") @ApiParam(value = "客户email") String email,
	               @RequestParam("password") @ApiParam(value = "客户密码") String password
	) {
		return customerService.login(email, password);
	}

	@ApiOperation(value = "根据商家名称查询", notes = "传递string类型的参数name")
	@GetMapping("/getSellerByName")
	public R getSellerByName(@RequestParam("name") String name) {
		return sellerService.getSellerByName(name);
	}

}
