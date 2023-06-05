package com.example.dianli.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dianli.Entity.Order;
import com.example.dianli.common.R;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */

public interface OrderService extends IService<Order>{

	public R addOrder(Order order) throws IOException;
	public R getOrderListByCustomerId(Integer customerId);
	public R deleteOrderById(Integer orderId);

	R getOrderListBySellerId(Integer sellerId);
	R getOrderByBlockId(String blockId) throws IOException;
}
