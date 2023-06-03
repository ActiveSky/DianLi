package com.example.dianli.Controller;

import com.example.dianli.Entity.Order;
import com.example.dianli.Service.OrderService;
import com.example.dianli.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JiKuiXie
 * @Date 2023/06/01
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单接口")

public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping("/addOrder")
	@ApiOperation(value="添加订单", notes="根据order表添加订单")
	public R addOrder(@RequestBody  Order order) {
		return orderService.addOrder(order);
	}

	@GetMapping("/getOrderListByCustomerId")
	@ApiOperation(value="获取订单列表", notes="根据顾客id获取订单列表")
	public R getOrderListById(@RequestParam(value = "customerId") Integer customerId) {
		return orderService.getOrderListByCustomerId(customerId);
	}

	@DeleteMapping("/deleteOrderById")
	@ApiOperation(value="删除订单", notes="根据订单id删除订单")
	public R deleteOrderById(@RequestParam(value = "orderId") Integer orderId) {
		return orderService.deleteOrderById(orderId);
	}

	@GetMapping("/getOrderListBySellerId")
	@ApiOperation(value="获取订单列表", notes="根据商家id获取订单列表")
	public R getOrderListBySellerId(@RequestParam(value = "sellerId") Integer sellerId) {
		return orderService.getOrderListBySellerId(sellerId);
	}

	@GetMapping("/getOrderByBlockId")
	@ApiOperation(value="获取订单列表", notes="根据区块链id获取订单列表")
	public R getOrderByBlockId(@RequestParam(value = "blockId") String blockId) {
		return orderService.getOrderByBlockId(blockId);
	}



}
