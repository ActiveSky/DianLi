package com.example.dianli.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
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
@TableName(value = "customer")
@ApiModel(value = "客户实体类")
public class Customer {
	@TableId(type = IdType.AUTO,value = "customer_id")
	private Integer customerId; // 客户id

	@TableField(value = "customer_email")
	private String customerEmail; // 客户邮箱
	@TableField(select = false,value = "customer_password")
	private String customerPassword; // 客户密码

	@TableField(value = "customer_name")
	private String customerName;  // 客户名称
	//	private Date entry_time;  // 入驻时间  删掉
	@TableField(value = "customer_phone")
	private String customerPhone;   // 电话
	//	private String registered_capital; // 注册资本  删掉
	@TableField(value = "customer_address")
	private String customerAddress; // 客户地址
	@TableField(value = "customer_introduction")
	private String customerIntroduction; // 客户简介
	@TableField(value = "customer_logo")
	private String customerLogo; // 客户图片
	@TableField(value = "main_business")
	private String mainBusiness; // 主营业务
}
