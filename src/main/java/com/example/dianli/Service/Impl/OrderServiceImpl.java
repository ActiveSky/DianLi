package com.example.dianli.Service.Impl;

import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dianli.Entity.Item;
import com.example.dianli.Entity.ItemDetail;
import com.example.dianli.Entity.Order;
import com.example.dianli.Entity.Seller;
import com.example.dianli.Entity.result.OrderAll;
import com.example.dianli.Entity.result.OrderJustTwo;
import com.example.dianli.Mapper.ItemMapper;
import com.example.dianli.Mapper.OrderMapper;
import com.example.dianli.Service.OrderService;
import com.example.dianli.Utils.HashUtils;
import com.example.dianli.common.R;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.gson.JsonObject;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */
@Service

public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public R addOrder(Order order) {
//		order.setBlockchainId("11111");
		order.setBlockchainId(HashUtils.hash(order.toString()));
		System.out.println("order的值为：");
		System.out.println(order);

		LambdaQueryWrapper<Item> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper
				.eq(Item::getId, order.getItemId())
				.select(Item::getUserCount);

		Item item = itemMapper.selectOne(lambdaQueryWrapper);
		Integer currentCount=item.getUserCount();

		LambdaUpdateWrapper<Item> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
		lambdaUpdateWrapper
				.eq(Item::getId, order.getItemId())
				.set(Item::getUserCount, currentCount + 1);


		if (orderMapper.insert(order) > 0) {
			itemMapper.update(null, lambdaUpdateWrapper);
			return R.success("添加成功");
		} else {
			return R.error("添加失败");
		}
	}

	/**
	 * @param customerId
	 */
	public R getOrderListByCustomerId(Integer customerId) {
//		LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
		LambdaQueryWrapper<Order> lambdaWrapper = new LambdaQueryWrapper<>();
		lambdaWrapper.select(Order::getId, Order::getOrderTime, Order::getBlockchainId).eq(Order::getCustomerId, customerId);

		List<Order> orderList = orderMapper.selectList(lambdaWrapper);

		List<OrderJustTwo> newList = new ArrayList<>();

		for (Order entity : orderList) {
			OrderJustTwo newEntity = new OrderJustTwo();
			BeanUtils.copyProperties(entity, newEntity);
			newList.add(newEntity);
		}

		JSONArray jsonArray = new JSONArray(newList);

		return R.success("获取成功", jsonArray);


	}

	public R deleteOrderById(Integer orderId) {
		if (orderMapper.deleteById(orderId) > 0) {
			return R.success("删除成功");
		} else {
			return R.error("删除失败");
		}
	}

	public R getOrderListBySellerId(Integer sellerId) {
		LambdaQueryWrapper<Order> lambdaWrapper = new LambdaQueryWrapper<>();
		lambdaWrapper.select(Order::getSellerId, Order::getOrderTime, Order::getBlockchainId).eq(Order::getSellerId, sellerId);

		List<Order> orderList = orderMapper.selectList(lambdaWrapper);

		List<OrderJustTwo> newList = new ArrayList<>();

		for (Order entity : orderList) {
			OrderJustTwo newEntity = new OrderJustTwo();
			BeanUtils.copyProperties(entity, newEntity);
			newList.add(newEntity);
		}

		JSONArray jsonArray = new JSONArray(newList);

		return R.success("获取成功", jsonArray);
	}


	public R getOrderByBlockId(String blockId){

		MPJLambdaWrapper<Order> lambdaWrapper = new MPJLambdaWrapper<>();
		lambdaWrapper
				.select(Order.class,i->!i.getColumn().equals("blockchain_id"))
				.select(Order::getId)
				.select(Item::getName,Item::getDuration)
				.select(Seller::getSellerEmail,Seller::getSellerName,Seller::getSellerPhone,Seller::getSellerAddress)
				.eq(Order::getBlockchainId, blockId)
				.innerJoin(Seller.class, Seller::getSellerId, Order::getSellerId)
				.innerJoin(Item.class, Item::getId, Order::getItemId);


		OrderAll orderAll = orderMapper.selectJoinOne(OrderAll.class,lambdaWrapper);



		JSONObject jsonObject = (JSONObject) JSON.toJSON(orderAll);


		return R.success("获取成功", jsonObject);
	}





}
