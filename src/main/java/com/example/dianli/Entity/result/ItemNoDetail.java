package com.example.dianli.Entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author JiKuiXie
 * @Date 2023/05/30
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ItemNoDetail {

	private Integer id; // 套餐id


	private String name;  // 套餐名称


	private String duration;  // 套餐期限

	private String type;  // 套餐类型


	private Date releaseTime;  // 发布时间


	private Integer userCount;  // 用户数量


}
