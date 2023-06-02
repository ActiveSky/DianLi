package com.example.dianli.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.dianli.Entity.HourDurationDetail;
import com.example.dianli.Entity.MonthDurationDetail;
import com.example.dianli.Mapper.MonthDDetailMapper;
import com.example.dianli.Service.MonthDDetailService;
import com.example.dianli.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */
@Service
public class MonthDDetailServiceImpl extends ServiceImpl<MonthDDetailMapper, MonthDurationDetail> implements MonthDDetailService {
	@Autowired
	private MonthDDetailMapper mon;

	@Override
	public R addMonthDDetail(MonthDurationDetail monthDurationDetail) {
//		LambdaQueryWrapper<MonthDurationDetail> lambdaQuery = new LambdaQueryWrapper<>();
//		lambdaQuery
//				.eq(MonthDurationDetail::getStart_month, monthDurationDetail.getStart_month())
//				.eq(MonthDurationDetail::getEnd_month, monthDurationDetail.getEnd_month())
//				.select(MonthDurationDetail::getId);
//
//		MonthDurationDetail monthDurationDetail1 = mon.selectOne(lambdaQuery);
////		System.out.println("输出object第一次");
////		System.out.println(hourDurationDetail1.getId());  //获得id
//		if (monthDurationDetail == null) {
			if (mon.insert(monthDurationDetail) > 0) {
				System.out.println(monthDurationDetail.getId());
				return R.success("添加成功", monthDurationDetail.getId());
			} else {
				return R.error("添加失败");
			}
//		} else {
//			return R.success("已存在", monthDurationDetail1.getId());
//		}

	}
}


