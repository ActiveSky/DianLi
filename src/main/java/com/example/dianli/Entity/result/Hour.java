package com.example.dianli.Entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.sql.Time;
import java.util.Date;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Hour {
	private Time startHour;  //开始小时
	private Time endHour;  //结束小时
	private Float price;  //价格
	private String status ;  //状态(平，谷，峰)
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("start_hour", startHour)
				.append("end_hour", endHour)
				.append("price", price)
				.append("status", status)
				.toString();
	}
}
