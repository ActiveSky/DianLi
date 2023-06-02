package com.example.dianli.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * @Author JiKuiXie
 * @Date 2023/05/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "news")
@ApiModel(value = "新闻实体类")
public class News {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id; // id
	@TableField(value = "title")
	private String title; // 标题题
	@TableField(value = "content")
	private String content; // 内容
	@TableField(value = "create_time")
	private Date time; // 时间
//	private String news_img; // 图片
//	private String news_type; // 类型

	public String toString(){
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id",id)
				.append("title",title)
				.append("content",content)
				.append("time",time)
				.toString();
	}
}
