package com.example.dianli.Controller;

import com.example.dianli.Entity.*;
import com.example.dianli.Entity.result.ItemAllNOSeller;
import com.example.dianli.Entity.result.Month;
import com.example.dianli.Entity.result.ItemAll;
import com.example.dianli.Mapper.ItemDetailMapper;
import com.example.dianli.Service.ItemDetailService;
import com.example.dianli.Service.ItemService;
import com.example.dianli.common.R;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */
@RestController
@RequestMapping("/item")
@Api(tags = "商品接口")
public class ItemController {
	@Autowired
	private ItemDetailService itemDetailService;
	@Autowired
	private ItemService itemService;

	@ApiOperation(value = "获取商品详情", notes = "根据商品id获取商品详情")
	@GetMapping("/getItemDetail")    //获取商品详情
	public R getItemDetailById( @RequestParam(value = "itemId") Integer itemId) {

		return itemDetailService.getItemDetailById(itemId);
	}

	@ApiOperation(value = "获取所有商品列表", notes = "获取所有商品列表，顾客登录看到的")
	@GetMapping("/getItemList")  //获取商品列表
	public R getItemList() {
		return itemService.getItemList();
	}


	@ApiOperation(value="添加商品", notes="添加商品")
	@PostMapping("/addItem")  //添加商品
	public R addItem(@RequestBody ItemAllNOSeller itemAllNOSeller, @RequestParam(value = "sellerId") Integer sellerId) {
		return itemService.addItem(itemAllNOSeller, sellerId);
	}


	@ApiOperation(value="更新商品", notes="更新商品")
	@PostMapping("/updateItem")  //更新商品
	public R updateItem(@RequestBody ItemAllNOSeller itemAllNOSeller, @RequestParam(value = "itemId") Integer itemId) {
		return itemService.updateItem(itemAllNOSeller, itemId);
	}

	@ApiOperation(value="删除商品", notes="删除商品")
	@DeleteMapping("/deleteItem")  //删除商品
	public R deleteItem(@RequestParam(value = "itemId") Integer itemId) {
		return itemService.deleteItem(itemId);
	}


	@ApiOperation(value="根据商家id获取商品", notes="根据商家id获取商品,商家登录看到的")
	@GetMapping("/getItemBySellerId")  //根据商家id获取商品
	public R getItemBySellerId(@RequestParam(value = "sellerId") Integer sellerId) {
		return itemService.getItemBySellerId(sellerId);
	}

	@ApiOperation(value="根据type筛选套餐", notes="传递int类型参数type，0、1、2分别表示筛选分时价格、市场费率、混合模式")
	@GetMapping("/screenItemByType")
	public R screenItemByType(@RequestParam(value = "type") Integer type) {
		return itemService.screenItemByType(type);
	}

	@ApiOperation(value="根据type排序套餐", notes="传递int类型参数type，0、1、2分别表示根据用户数量、套餐期限、发布时间排序")
	@GetMapping("/sortItemBySth")
	public R sortItemBySth(@RequestParam(value = "type") Integer type) {
		return itemService.sortItemBySth(type);
	}



}
