package com.example.dianli.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ItemDetail {
	@TableId(type= IdType.AUTO)

	private Integer id; // id
	private Integer itemId; // 套餐id
	private Integer monthDurationId;  //月段id
	private Integer hourDurationId;  //小时段id


}
