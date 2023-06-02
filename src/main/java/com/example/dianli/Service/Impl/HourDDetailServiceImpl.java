package com.example.dianli.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dianli.Entity.HourDurationDetail;
import com.example.dianli.Mapper.HourDDetailMapper;
import com.example.dianli.Service.HourDDetailService;
import com.example.dianli.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */
@Service
public class HourDDetailServiceImpl extends ServiceImpl<HourDDetailMapper, HourDurationDetail> implements HourDDetailService {

	@Autowired
	private HourDDetailMapper hourDDetailMapper;

	public R addHourDDetail(HourDurationDetail hourDurationDetail) {

		if (hourDDetailMapper.insert(hourDurationDetail) > 0) {
			return R.success("添加成功", hourDurationDetail.getId());
		} else {
			return R.error("添加失败");
		}
	}

	@Override
	public R updateHourDDetail(HourDurationDetail hourDurationDetail) {

		if (hourDDetailMapper.updateById(hourDurationDetail) > 0) {
			return R.success("修改成功", hourDurationDetail.getId());
		} else {
			return R.error("修改失败");
		}
	}
}

