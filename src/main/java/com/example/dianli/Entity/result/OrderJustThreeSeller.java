package com.example.dianli.Entity.result;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author JiKuiXie
 * @Date 2023/05/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderJustThreeSeller {



	private Integer id; // id

	private LocalDateTime orderTime; //	预定时间(用户自定义)


	private String sellerName; // 商家名

	private String blockchainId;  // 区块链id


}
