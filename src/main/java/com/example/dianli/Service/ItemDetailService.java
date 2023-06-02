package com.example.dianli.Service;

import com.example.dianli.Entity.ItemDetail;
import com.example.dianli.common.R;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */

public interface ItemDetailService {

	public R getItemDetailById(Integer itemId);

	R addItemDetail(ItemDetail itemDetail);
}
