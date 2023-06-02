package com.example.dianli.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dianli.Entity.Seller;
import com.example.dianli.common.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author JiKuiXie
 * @Date 2023/05/26
 */

public interface SellerService extends IService<Seller> {

	public R updateSellerMsgById(Seller seller);
	public R updateSellerLogoById(MultipartFile logoFile);

	R getSellerMsg(Integer id);

	R login(String email, String password);


	R getSellerList();

	R getSellerByName (String name);


}
