package com.example.dianli.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dianli.Entity.*;
import com.example.dianli.Entity.result.ItemAll;
import com.example.dianli.Entity.result.ItemAllNOSeller;
import com.example.dianli.Entity.result.Month;
import com.example.dianli.Mapper.ItemDetailMapper;
import com.example.dianli.Service.HourDDetailService;
import com.example.dianli.Service.ItemDetailService;
import com.example.dianli.Service.ItemService;
import com.example.dianli.Service.MonthDDetailService;
import com.example.dianli.common.R;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */
@Service
public class ItemDetailServiceImpl extends ServiceImpl<ItemDetailMapper, ItemDetail> implements ItemDetailService {


	@Autowired
	private ItemDetailMapper itemDetailMapper;
	@Autowired
	private ItemService itemService;
	@Autowired
	private MonthDDetailService mon;
	@Autowired
	private HourDDetailService hour;


	public R getItemDetailById(Integer itemId) {
		MPJLambdaWrapper<ItemDetail> lambdaWrapper = new MPJLambdaWrapper<>();
		lambdaWrapper
				.selectAll(Item.class)
				.selectCollection(MonthDurationDetail.class, ItemAllNOSeller::getMonthList, map -> map
						.result(MonthDurationDetail::getStartMonth)
						.result(MonthDurationDetail::getEndMonth)
						.collection(HourDurationDetail.class, Month::getHourList, c -> c
								.result(HourDurationDetail::getStartHour)
								.result(HourDurationDetail::getEndHour)
								.result(HourDurationDetail::getPrice)
								.result(HourDurationDetail::getStatus))
				)
				.innerJoin(MonthDurationDetail.class, MonthDurationDetail::getId, ItemDetail::getMonthDurationId)
				.innerJoin(Item.class, Item::getId, ItemDetail::getItemId)
				.innerJoin(HourDurationDetail.class, HourDurationDetail::getId, ItemDetail::getHourDurationId)
				.eq(Item::getId, itemId);

		ItemAllNOSeller itemAllNOSeller = itemDetailMapper.selectJoinOne(ItemAllNOSeller.class, lambdaWrapper);

		Seller seller = itemService.getSellerById(itemId);//  通过itemId获取seller

		ItemAll itemAll = new ItemAll();

		BeanUtils.copyProperties(itemAllNOSeller, itemAll);
		itemAll.setSeller(seller);
		return R.success("查询成功", itemAll);
	}


	public R addItemDetail(ItemDetail itemDetail){
		if(itemDetailMapper.insert(itemDetail)>0){
			return R.success("添加成功");
		}else
			return R.error("添加失败");
	}




}
