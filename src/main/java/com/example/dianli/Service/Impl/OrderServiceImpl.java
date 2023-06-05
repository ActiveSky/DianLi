package com.example.dianli.Service.Impl;

import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dianli.Entity.Customer;
import com.example.dianli.Entity.Item;
import com.example.dianli.Entity.Order;
import com.example.dianli.Entity.Seller;
import com.example.dianli.Entity.result.OrderAll;
import com.example.dianli.Entity.result.OrderJustThreeCustomer;
import com.example.dianli.Entity.result.OrderJustThreeSeller;
import com.example.dianli.Mapper.ItemMapper;
import com.example.dianli.Mapper.OrderMapper;
import com.example.dianli.Mapper.SellerMapper;
import com.example.dianli.Service.OrderService;
import com.example.dianli.Utils.HashUtils;
import com.example.dianli.Utils.OkHttpUtil;
import com.example.dianli.common.R;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Autowired
	private SellerMapper sellerMapper;

	@Override
	public R addOrder(Order order) throws IOException {
//		order.setBlockchainId("11111");
		String hash = HashUtils.hash(order.toString());
		order.setBlockchainId(hash);

		System.out.println("order的值为：");
		System.out.println(order);

		LambdaQueryWrapper<Item> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper
				.eq(Item::getId, order.getItemId())
				.select(Item::getUserCount);

		Item item = itemMapper.selectOne(lambdaQueryWrapper);
		Integer currentCount = item.getUserCount();

		LambdaUpdateWrapper<Item> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
		lambdaUpdateWrapper
				.eq(Item::getId, order.getItemId())
				.set(Item::getUserCount, currentCount + 1);


		if (orderMapper.insert(order) > 0) {
			itemMapper.update(null, lambdaUpdateWrapper);
			Map<String, Object> map = new HashMap<>();
			map.put("key", hash);
			Object obj = JSON.toJSON(order);
//			JSONObject jsonObject = new JSONObject();
			map.put("value", obj);   //订单信息上链

			JSONObject jsonObject = new JSONObject(map);

			OkHttpUtil.put(jsonObject.toString());

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
		MPJLambdaWrapper<Order> lambdaWrapper = new MPJLambdaWrapper<>();
		lambdaWrapper
				.select(Order::getId, Order::getOrderTime, Order::getBlockchainId)
				.select(Seller::getSellerName)
				.innerJoin(Seller.class, Seller::getSellerId, Order::getSellerId)
				.eq(Order::getCustomerId, customerId);

		List<OrderJustThreeSeller> orderList = orderMapper.selectJoinList(OrderJustThreeSeller.class, lambdaWrapper);

//		List<OrderJustThreeSeller> newList = new ArrayList<>();
//
//		for (Order entity : orderList) {
//			OrderJustThreeSeller newEntity = new OrderJustThreeSeller();
//			BeanUtils.copyProperties(entity, newEntity);
//			newList.add(newEntity);
//		}

		JSONArray jsonArray = new JSONArray(orderList);

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
		MPJLambdaWrapper<Order> lambdaWrapper = new MPJLambdaWrapper<>();
		lambdaWrapper
				.select(Order::getId, Order::getOrderTime, Order::getBlockchainId)
				.select(Customer::getCustomerName)
				.innerJoin(Customer.class, Customer::getCustomerId, Order::getCustomerId)
				.eq(Order::getSellerId, sellerId);

		List<OrderJustThreeCustomer> orderList = orderMapper.selectJoinList(OrderJustThreeCustomer.class, lambdaWrapper);

//		List<OrderJustThreeSeller> newList = new ArrayList<>();
//
//		for (Order entity : orderList) {
//			OrderJustThreeSeller newEntity = new OrderJustThreeSeller();
//			BeanUtils.copyProperties(entity, newEntity);
//			newList.add(newEntity);
//		}

		JSONArray jsonArray = new JSONArray(orderList);

		return R.success("获取成功", jsonArray);
	}


	public R getOrderByBlockId(String blockId) throws IOException {

		MPJLambdaWrapper<Order> lambdaWrapper = new MPJLambdaWrapper<>();

		lambdaWrapper
				.select(Order.class, i -> !i.getColumn().equals("blockchain_id"))
				.select(Order::getId)
				.select(Item::getName, Item::getDuration) // item信息
				.select(Seller::getSellerEmail, Seller::getSellerName, Seller::getSellerPhone, Seller::getSellerAddress)//seller信息
				.eq(Order::getBlockchainId, blockId)
				.innerJoin(Seller.class, Seller::getSellerId, Order::getSellerId)
				.innerJoin(Item.class, Item::getId, Order::getItemId);


		OrderAll orderAll = orderMapper.selectJoinOne(OrderAll.class, lambdaWrapper);
		JSONObject jsonObject = (JSONObject) JSON.toJSON(orderAll);  //查到返回

//		MPJLambdaWrapper<Order> lambdaWrapper2 = new MPJLambdaWrapper<>();


		if (orderAll != null) {
			return R.success("获取成功", jsonObject);
		} else {
			//查询区块链,转为json
			JSONObject jsonObject1 = JSON.parseObject(OkHttpUtil.query(blockId));
			Object obj_itemId = jsonObject1.get("itemId");
			LambdaQueryWrapper<Item> itemWrapper = new LambdaQueryWrapper<>();
			itemWrapper
					.select(Item::getName, Item::getDuration)
					.eq(Item::getId, obj_itemId);
			Item item = itemMapper.selectOne(itemWrapper);

			Object obj_sellerId = jsonObject1.get("sellerId");
			LambdaQueryWrapper<Seller> sellerWrapper = new LambdaQueryWrapper<>();
			sellerWrapper
					.select(Seller::getSellerEmail, Seller::getSellerName, Seller::getSellerPhone, Seller::getSellerAddress)
					.eq(Seller::getSellerId, obj_sellerId);
			Seller seller = sellerMapper.selectOne(sellerWrapper);

			jsonObject1.remove("blockchainId");

			OrderAll orderAll1 = new OrderAll();
			BeanUtils.copyProperties(item, orderAll1);
			BeanUtils.copyProperties(seller, orderAll1);
			System.out.println("区块链查询");
			System.out.println(jsonObject1);
			orderAll1.setOrderTime(jsonObject1.getDate("orderTime"));
			orderAll1.setId(jsonObject1.getInteger("id"));
			orderAll1.setCustomerId(jsonObject1.getInteger("customerId"));
			orderAll1.setSellerId(jsonObject1.getInteger("sellerId"));
			orderAll1.setItemId(jsonObject1.getInteger("itemId"));

			// 获取LocalDateTime类型数据
			String timestampStr = jsonObject1.getString("createTime");
			long timestamp = Long.parseLong(timestampStr);

			// 将时间戳转换为Instant对象
			Instant instant = Instant.ofEpochMilli(timestamp);

			// 将Instant对象转换为ZonedDateTime对象，需要指定时区
			ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

			// 将ZonedDateTime对象转换为LocalDateTime对象
			LocalDateTime dateTime = zonedDateTime.toLocalDateTime();
			System.out.println(dateTime);
			orderAll1.setCreateTime(dateTime);
			System.out.println(orderAll1);

//			JSONObject jsonObject2 = (JSONObject) JSON.toJSON(orderAll1);  //区块链返回
			System.out.println("区块链返回");
//			System.out.println(jsonObject2);
			return R.success("获取成功", orderAll1);
		}


	}


}
