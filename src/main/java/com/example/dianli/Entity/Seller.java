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
 * @Date 2023/05/26
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "seller")
public class Seller {
	@TableId(type = IdType.AUTO)
	private Integer sellerId;     // 商家id
	private String sellerEmail; // 商家邮箱

	@TableField(select = false)
	private String sellerPassword; // 商家密码

	private String sellerName;  // 商家名称
	private Date entryTime;  // 入驻时间
	private String sellerPhone;   // 电话
	private String registeredCapital; // 注册资本
	private String sellerAddress; // 商家地址
	private String sellerIntroduction; // 商家简介
	private String sellerLogo; // 商家图片

	private String mainBusiness; // 主营业务


//	加个密码
}
