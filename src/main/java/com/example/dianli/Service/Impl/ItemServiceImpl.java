package com.example.dianli.Service.Impl;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dianli.Entity.*;
import com.example.dianli.Entity.result.*;
import com.example.dianli.Mapper.ItemDetailMapper;
import com.example.dianli.Mapper.ItemMapper;
import com.example.dianli.Service.HourDDetailService;
import com.example.dianli.Service.ItemService;
import com.example.dianli.Service.MonthDDetailService;
import com.example.dianli.common.R;
import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDetailMapper itemDetailMapper;

	@Autowired
	private MonthDDetailService mon;
	@Autowired
	private HourDDetailService hourS;

	@Override
	public R getItemList() {
		LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(Item::getId, Item::getName, Item::getType, Item::getDuration, Item::getReleaseTime, Item::getUserCount);
		List<Item> itemList = itemMapper.selectList(queryWrapper);

		// 将查询结果转换成新的实体类
		List<ItemNoDetail> newList = new ArrayList<>();
		for (Item entity : itemList) {
			ItemNoDetail newEntity = new ItemNoDetail();
			BeanUtils.copyProperties(entity, newEntity);
			newList.add(newEntity);
		}
		JSONArray jsonArray = new JSONArray(newList);
		return R.success("获取成功", jsonArray);

	}

	@Override
	public Seller getSellerById(Integer itemId) {
		MPJLambdaWrapper<Item> lambdaWrapper = new MPJLambdaWrapper<>();
		lambdaWrapper
				.selectAll(Seller.class)
				.innerJoin(Seller.class, Seller::getSellerId, Item::getSellerId)
				.eq(Item::getId, itemId);

		return itemMapper.selectJoinOne(Seller.class, lambdaWrapper);
	}


	@Override
	public R addItem(ItemAllNOSeller itemAllNOSeller, Integer sellerId) {
		itemAllNOSeller.setStatus("上架") //默认上架
				.setUserCount(0); //默认用户数为0

		Item item = new Item();
		BeanUtils.copyProperties(itemAllNOSeller, item);
		System.out.println("这是Id测试0");
		System.out.println(item.getId());
		item.setSellerId(sellerId);  //添加商家id
		if (itemMapper.insert(item) > 0) {
			System.out.println("添加成功");  //先添加Item,没有月份和日信息
		} else {
			System.out.println("添加失败");
		}
		System.out.println("这是Id测试");
		System.out.println(item.getId());

		//添加月份信息
		List<Month> monthList = itemAllNOSeller.getMonthList();

		List<Integer> monthIdList = new ArrayList<>();
		List<Integer> hourIdList = new ArrayList<>();

//		Map<Integer, Integer> month_hour = new HashMap<>();

		for (Month month : monthList) {
			MonthDurationDetail monthDurationDetail = new MonthDurationDetail();
			monthDurationDetail
					.setStartMonth(month.getStartMonth())
					.setEndMonth(month.getEndMonth());

			R r_month = mon.addMonthDDetail(monthDurationDetail);
			if (!StringUtils.equals(r_month.getMessage(), "添加失败")) {
				monthIdList.add((Integer) r_month.getData());  //添加月份id
				System.out.println("添加月份成功");
			} else {
				System.out.println("添加月份失败");
			}

			List<Hour> hourList = month.getHourList();
			for (Hour hour : hourList) {
				HourDurationDetail hourDurationDetail = new HourDurationDetail();
				hourDurationDetail
						.setStartHour(hour.getStartHour())
						.setEndHour(hour.getEndHour())
						.setPrice(hour.getPrice())
						.setStatus(hour.getStatus());
				R r_hour = hourS.addHourDDetail(hourDurationDetail);
				if (!StringUtils.equals(r_hour.getMessage(), "添加失败")) {
//					month_hour.put((Integer) r_month.getData(), (Integer) r_hour.getData());  //添加小时id
					hourIdList.add((Integer) r_hour.getData());
					System.out.println("添加小时成功");
				} else {
					System.out.println("添加小时失败");
				}
			}


		}

//		LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<Item>();
//		queryWrapper
//				.select(Item::getId)
//				.eq(Item::getName, item.getName())
//				.eq(Item::getSellerId, item.getSellerId());
//		Item item1 = itemMapper.selectOne(queryWrapper);
		Integer itemId = item.getId();  //item的id

		int i = 0;

		int hourNUMS = 24;
		int n = 0;

		for (Integer monthId : monthIdList) {
			for (int j = 0; j < hourNUMS; j++) {
				ItemDetail itemDetail = new ItemDetail();
				itemDetail
						.setItemId(itemId)
						.setMonthDurationId(monthId)
						.setHourDurationId(hourIdList.get(i * hourNUMS + j));
				itemDetailMapper.insert(itemDetail); //添加itemDetail
				System.out.println("这是n:" + (n++));
			}
			i++;
			System.out.println(i);
		}


		return R.success("添加成功");
	}


	@Override
	public R updateItem(ItemAllNOSeller itemAllNOSeller, Integer ItemId) {
		Item item = new Item();
		BeanUtils.copyProperties(itemAllNOSeller, item);
		item.setId(ItemId);
		if (itemMapper.updateById(item) > 0) {
			System.out.println("修改成功");  //先添加Item,没有月份和日信息
		} else {
			System.out.println("修改失败");
		}
		System.out.println("这是Id测试");
		System.out.println(item.getId());

		//添加月份信息
		List<Month> monthList = itemAllNOSeller.getMonthList();

		List<Integer> monthIdList = new ArrayList<>();
		List<Integer> hourIdList = new ArrayList<>();

//		Map<Integer, Integer> month_hour = new HashMap<>();

		for (Month month : monthList) {
			MonthDurationDetail monthDurationDetail = new MonthDurationDetail();
			monthDurationDetail
					.setStartMonth(month.getStartMonth())
					.setEndMonth(month.getEndMonth());

			R r_month = mon.addMonthDDetail(monthDurationDetail);
			if (!StringUtils.equals(r_month.getMessage(), "添加失败")) {
				monthIdList.add((Integer) r_month.getData());  //添加月份id
				System.out.println("添加月份成功");
			} else {
				System.out.println("添加月份失败");
			}

			List<Hour> hourList = month.getHourList();
			for (Hour hour : hourList) {
				HourDurationDetail hourDurationDetail = new HourDurationDetail();
				hourDurationDetail
						.setStartHour(hour.getStartHour())
						.setEndHour(hour.getEndHour())
						.setPrice(hour.getPrice())
						.setStatus(hour.getStatus());
				R r_hour = hourS.addHourDDetail(hourDurationDetail);
				if (!StringUtils.equals(r_hour.getMessage(), "添加失败")) {
//					month_hour.put((Integer) r_month.getData(), (Integer) r_hour.getData());  //添加小时id
					hourIdList.add((Integer) r_hour.getData());
					System.out.println("添加小时成功");
				} else {
					System.out.println("添加小时失败");
				}
			}


		}

//		LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<Item>();
//		queryWrapper
//				.select(Item::getId)
//				.eq(Item::getName, item.getName())
//				.eq(Item::getSellerId, item.getSellerId());
//		Item item1 = itemMapper.selectOne(queryWrapper);
		Integer itemId = item.getId();  //item的id

		int i = 0;

		int hourNUMS = 24;
		int n = 0;

		for (Integer monthId : monthIdList) {
			for (int j = 0; j < hourNUMS; j++) {
				ItemDetail itemDetail = new ItemDetail();
				itemDetail
						.setItemId(itemId)
						.setMonthDurationId(monthId)
						.setHourDurationId(hourIdList.get(i * hourNUMS + j));
				itemDetailMapper.insert(itemDetail); //添加itemDetail
				System.out.println("这是n:" + (n++));
			}
			i++;
			System.out.println(i);
		}


		return R.success("修改成功");

	}


	public R deleteItem(Integer itemId) {
		if (itemMapper.deleteById(itemId) > 0) {
			System.out.println("删除成功");
			return R.success("删除成功");

		} else {
			System.out.println("删除失败");
			return R.error("删除失败");
		}
	}

	public R getItemBySellerId(Integer sellerId) {
		MPJLambdaWrapper<Item> queryWrapper = new MPJLambdaWrapper<>();
		queryWrapper.select(Item::getId, Item::getName, Item::getType, Item::getDuration, Item::getReleaseTime, Item::getUserCount)
				.eq(Item::getSellerId, sellerId);
		List<ItemNoDetail> itemList = itemMapper.selectJoinList(ItemNoDetail.class, queryWrapper);

		// 将查询结果转换成新的实体类
//		List<ItemNoDetail> newList = new ArrayList<>();
//		for (Item entity : itemList) {
//			ItemNoDetail newEntity = new ItemNoDetail();
//			BeanUtils.copyProperties(entity, newEntity);
//			newList.add(newEntity);
//		}
		return R.success("获取成功", itemList);
	}

	/**
	 * 根据类型筛选
	 * @param type
	 * @return
	 */

	public R screenItemByType(int type){
		switch(type){
			case 0 :
				//语句
				LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.select(Item::getId, Item::getName, Item::getType, Item::getDuration, Item::getReleaseTime, Item::getUserCount)
						.eq(Item::getType, "分时价格");
				List<Item> itemList = itemMapper.selectList(queryWrapper);
//				JSONArray jsonArray = new JSONArray(itemList);

				// 将查询结果转换成新的实体类
				List<ItemNoDetail> newList = new ArrayList<>();
				for (Item entity : itemList) {
					ItemNoDetail newEntity = new ItemNoDetail();
					BeanUtils.copyProperties(entity, newEntity);
					newList.add(newEntity);
				}
				JSONArray jsonArray = new JSONArray(newList);

				return R.success("筛选成功",jsonArray);
			case 1 :
				//语句
				LambdaQueryWrapper<Item> queryWrapper2 = new LambdaQueryWrapper<>();
				queryWrapper2.select(Item::getId, Item::getName, Item::getType, Item::getDuration, Item::getReleaseTime, Item::getUserCount)
						.eq(Item::getType, "市场费率");
				List<Item> itemList2 = itemMapper.selectList(queryWrapper2);
				// 将查询结果转换成新的实体类
				List<ItemNoDetail> newList2 = new ArrayList<>();
				for (Item entity : itemList2) {
					ItemNoDetail newEntity = new ItemNoDetail();
					BeanUtils.copyProperties(entity, newEntity);
					newList2.add(newEntity);
				}
				JSONArray jsonArray2 = new JSONArray(newList2);

				return R.success("筛选成功",newList2);
			//你可以有任意数量的case语句
			case 2 :
				LambdaQueryWrapper<Item> queryWrapper3 = new LambdaQueryWrapper<>();
				queryWrapper3.select(Item::getId, Item::getName, Item::getType, Item::getDuration, Item::getReleaseTime, Item::getUserCount)
						.eq(Item::getType, "混合模式");
				List<Item> itemList3 = itemMapper.selectList(queryWrapper3);

				// 将查询结果转换成新的实体类
				List<ItemNoDetail> newList3 = new ArrayList<>();
				for (Item entity : itemList3) {
					ItemNoDetail newEntity = new ItemNoDetail();
					BeanUtils.copyProperties(entity, newEntity);
					newList3.add(newEntity);
				}
				JSONArray jsonArray3 = new JSONArray(newList3);
				return R.success("筛选成功",newList3);
			//语句
			default : //可选
				LambdaQueryWrapper<Item> queryWrapper4 = new LambdaQueryWrapper<>();
				queryWrapper4.select(Item::getId, Item::getName, Item::getType, Item::getDuration, Item::getReleaseTime, Item::getUserCount);
				List<Item> itemList4 = itemMapper.selectList(queryWrapper4);
				return R.success("筛选成功",itemList4);
			//语句
		}

	}

	public R sortItemBySth(int type){
		switch(type){
			case 0 ://用户数量
				//语句
				LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.orderByDesc(Item::getUserCount);
				List<Item> itemList = itemMapper.selectList(queryWrapper);

				// 将查询结果转换成新的实体类
				List<ItemNoDetail> newList = new ArrayList<>();
				for (Item entity : itemList) {
					ItemNoDetail newEntity = new ItemNoDetail();
					BeanUtils.copyProperties(entity, newEntity);
					newList.add(newEntity);
				}
				JSONArray jsonArray = new JSONArray(newList);

				return R.success("根据用户数量排序成功",newList);
			case 1 ://套餐期限
				//语句
				LambdaQueryWrapper<Item> queryWrapper2 = new LambdaQueryWrapper<>();
				queryWrapper2.orderByDesc(Item::getDuration);
				List<Item> itemList2 = itemMapper.selectList(queryWrapper2);
				// 将查询结果转换成新的实体类
				List<ItemNoDetail> newList2 = new ArrayList<>();
				for (Item entity : itemList2) {
					ItemNoDetail newEntity = new ItemNoDetail();
					BeanUtils.copyProperties(entity, newEntity);
					newList2.add(newEntity);
				}
				JSONArray jsonArray2 = new JSONArray(newList2);
				return R.success("根据套餐期限排序成功",newList2);
			//你可以有任意数量的case语句
			case 2 ://发布时间
				LambdaQueryWrapper<Item> queryWrapper3 = new LambdaQueryWrapper<>();
				queryWrapper3.orderByDesc(Item::getReleaseTime);
				List<Item> itemList3 = itemMapper.selectList(queryWrapper3);
				// 将查询结果转换成新的实体类
				List<ItemNoDetail> newList3 = new ArrayList<>();
				for (Item entity : itemList3) {
					ItemNoDetail newEntity = new ItemNoDetail();
					BeanUtils.copyProperties(entity, newEntity);
					newList3.add(newEntity);
				}
				JSONArray jsonArray3 = new JSONArray(newList3);
				return R.success("根据发布时间排序成功",newList3);
			//语句
			default : //可选
				LambdaQueryWrapper<Item> queryWrapper4 = new LambdaQueryWrapper<>();
				queryWrapper4.select(Item::getId, Item::getName, Item::getType, Item::getDuration, Item::getReleaseTime, Item::getUserCount);
				List<Item> itemList4 = itemMapper.selectList(queryWrapper4);
				return R.success("默认顺序",itemList4);
			//语句
		}

	}


}
