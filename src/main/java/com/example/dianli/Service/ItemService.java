package com.example.dianli.Service;

import com.example.dianli.Entity.Item;
import com.example.dianli.Entity.Seller;
import com.example.dianli.Entity.result.ItemAllNOSeller;
import com.example.dianli.common.R;

/**
 * 主要接口，业务层
 * @Author JiKuiXie
 * @Date 2023/05/29
 */

public interface ItemService {

	public R getItemList();
	public Seller getSellerById(Integer itemId);
	R addItem(ItemAllNOSeller itemAllNOSeller, Integer sellerId);

	R updateItem(ItemAllNOSeller itemAllNOSeller, Integer ItemId);

	R deleteItem(Integer itemId);

	R getItemBySellerId(Integer sellerId);

	public R screenItemByType(int type);

	public R sortItemBySth(int type);





}
