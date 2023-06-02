package com.example.dianli.Entity.result;

import com.example.dianli.Entity.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author JiKuiXie
 * @Date 2023/05/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ItemAll {
	private Integer id; // 套餐id

	private String name;  // 套餐名称

	private String duration;  // 套餐期限

	private String type;  // 套餐类型

	private Date releaseTime;  // 发布时间

	private Integer userCount;  // 用户数量

	private String cancelPolicy;  // 退订政策

	private String applicableObjects;// 适用对象

	private String status;  // 状态（下架、上架）

	private List<Month> monthList;  // 月段列表

	private Seller seller;  // 所属公司
}
