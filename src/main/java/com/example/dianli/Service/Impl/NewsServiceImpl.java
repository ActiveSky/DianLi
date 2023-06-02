package com.example.dianli.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dianli.Entity.News;
import com.example.dianli.Mapper.NewsMapper;
import com.example.dianli.Service.NewsService;
import com.example.dianli.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JiKuiXie
 * @Date 2023/05/30
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
	@Autowired
	private NewsMapper newsMapper;

	@Override
	public R getNewsList() {
			return R.success("查询成功",newsMapper.selectList(null));
	}

	@Override
	public R deleteNewsById(Integer newsId) {
		if (newsMapper.deleteById(newsId) > 0) {
			return R.success("删除成功");
		} else {
			return R.error("删除失败");
		}
	}

	@Override
	public R addNews(News news) {
		if (newsMapper.insert(news) > 0) {
			return R.success("添加成功");
		} else {
			return R.error("添加失败");
		}
	}
}
