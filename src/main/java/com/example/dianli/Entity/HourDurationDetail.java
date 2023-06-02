package com.example.dianli.Entity;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Time;

/**
 * @Author JiKuiXie
 * @Date 2023/05/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "hour_duration_detail")
public class HourDurationDetail {
	@TableId(type= IdType.AUTO)
	private Integer id; // id
//	private Integer item_id; // 套餐id
//	private Integer month_duration_id;  //月段id
	private Time startHour;  //开始小时
	private Time endHour;  //结束小时
	private Float price;  //价格
	private String status ;  //状态(平，谷，峰)
}
