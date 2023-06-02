package com.example.dianli.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dianli.Entity.News;
import com.example.dianli.Mapper.NewsMapper;
import com.example.dianli.common.R;

/**
 * @Author JiKuiXie
 * @Date 2023/05/30
 */

public interface NewsService  {
	public R getNewsList();
	R deleteNewsById(Integer newsId);
	R addNews(News news);
}
