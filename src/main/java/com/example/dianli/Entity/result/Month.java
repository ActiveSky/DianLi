package com.example.dianli.Entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class Month {
	private Integer startMonth;  //开始月份
	private Integer endMonth;  //结束月份

	private List<Hour> hourList;  //小时段

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
