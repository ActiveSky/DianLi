package com.example.dianli.Service;

import com.example.dianli.Entity.Customer;
import com.example.dianli.common.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */

public interface CustomerService {



	public R updateCustomerMsg(Customer customer);

	public R updateCustomerLogo(MultipartFile logoFile);

	public R getCustomerById(Integer id);

//	public R getCustomerList(Customer customer);

	R login(String email, String password);



}
