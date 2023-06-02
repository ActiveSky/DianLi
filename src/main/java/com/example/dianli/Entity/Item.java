package com.example.dianli.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author JiKuiXie
 * @Date 2023/05/28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "item")
public class Item {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id; // 套餐id

	@TableField(value = "name")
	private String name ;  // 套餐名称

	@TableField(value = "duration")
	private String duration;  // 套餐期限

	@TableField(value = "type")
	private String type;  // 套餐类型

	@TableField(value = "release_time")
	private Date releaseTime;  // 发布时间

	@TableField(value = "user_count")
	private Integer userCount;  // 用户数量

	@TableField(value = "cancel_policy")
	private String cancelPolicy;  // 退订政策

	@TableField(value = "applicable_objects")
	private String applicableObjects;// 适用对象

	@TableField(value = "status")
	private String status;  // 状态（下架、上架）

	@TableField(value = "seller_id")
	private Integer sellerId;  // 所属公司id

}
