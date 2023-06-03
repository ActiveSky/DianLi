package com.example.dianli.Entity.result;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.dianli.Entity.Order;
import com.example.dianli.Entity.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author JiKuiXie
 * @Date 2023/06/03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderAll {

	private Integer id; // id

	private LocalDateTime createTime;  // 创建时间

	private Integer customerId; // 客户id

	private Integer itemId; // 套餐id

	private String name;  // 套餐名称

	private String duration;  // 套餐期限

	private Integer sellerId; // 售电公司id

	private String sellerName;  // 商家名称

	private Date orderTime; //	预定时间(用户自定义)

	private String sellerEmail; // 商家邮箱

	private String sellerPhone;   // 电话

	private String sellerAddress; // 商家地址



}
