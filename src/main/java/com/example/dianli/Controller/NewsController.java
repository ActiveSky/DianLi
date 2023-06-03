package com.example.dianli.Controller;

import com.example.dianli.Service.NewsService;
import com.example.dianli.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JiKuiXie
 * @Date 2023/05/30
 */
@RestController
@RequestMapping("/news")
@Api(tags = "新闻接口")

public class NewsController {

	@Autowired
	private NewsService newsService;

	@ApiOperation(value = "获取所有新闻", notes = "获取所有新闻")
	@GetMapping("/getAllNews")
	public R getAllNews() {
		return newsService.getNewsList();
	}

	@ApiOperation(value = "删除新闻", notes = "根据id删除新闻")
	@DeleteMapping("/deleteNewsById")
	public R deleteNewsById(Integer id) {
		return newsService.deleteNewsById(id);
	}

	@PostMapping("/addNews")
	@ApiOperation(value = "添加新闻", notes = "根据news表添加新闻")
	public R addNews(@RequestBody com.example.dianli.Entity.News news) {
		return newsService.addNews(news);
	}
}
