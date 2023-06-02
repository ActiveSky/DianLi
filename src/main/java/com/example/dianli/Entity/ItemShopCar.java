package com.example.dianli.Entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName(value = "item_shopCar")
public class ItemShopCar {   //多对多关系
	@TableId(value = "id",type= IdType.AUTO)
	private Integer id; // id
	@TableField(value = "customer_id")
	private Integer customer_id; // 客户id
	@TableField(value = "item_id")
	private Integer item_id; // 套餐id
	@TableField(value = "create_time",fill= FieldFill.INSERT)
	private Date create_time;  // 创建时间

}
