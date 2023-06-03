package com.example.dianli.Entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author JiKuiXie
 * @Date 2023/05/28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "item_order")
public class Order {  //多对多
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id; // id
	@TableField(value = "customer_id")
	private Integer customerId; // 客户id
	@TableField(value = "item_id")
	private Integer itemId; // 套餐id
	@TableField(value = "seller_id")
	private Integer sellerId; // 售电公司id
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private LocalDateTime createTime;  // 创建时间
	@TableField(value = "order_time")
	private Date orderTime; //	预定时间(用户自定义)
	@TableField(value = "blockchain_id")
	private String blockchainId;  // 区块链id


	//预定时间
	//售电公司名
	//套餐名
	//套餐期限
	//预定时间
	//
	//区块链号
}
