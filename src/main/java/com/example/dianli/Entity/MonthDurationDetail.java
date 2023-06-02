package com.example.dianli.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author JiKuiXie
 * @Date 2023/05/28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "month_duration_detail")
public class MonthDurationDetail {
	@TableId(type= IdType.AUTO)

	private Integer id; // id
//	private Integer item_id; // 套餐id
	private Integer startMonth;  //开始月份
	private Integer endMonth;  //结束月份
}
