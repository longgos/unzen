/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.web.message.web.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unzen.base.data.Data;
import com.unzen.base.lang.Consts;
import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.service.ChannelService;
import com.unzen.common.service.PostService;
import com.unzen.web.message.web.controller.BaseController;


/**
 * @author langhsu
 *
 */
@Controller("mng_post_ctl")
@RequestMapping("/admin/posts")
public class PostsController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 表单页面
	 * @param title
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String list(String title, ModelMap model, HttpServletRequest request) {
		long id = ServletRequestUtils.getLongParameter(request, "id", Consts.ZERO);
		int group = ServletRequestUtils.getIntParameter(request, "group", Consts.ZERO);
		PostParam param = new PostParam();
		if(id > Consts.ZERO) param.setId(id);
		if(group > Consts.ZERO) param.setChannelId(group);
		param.setTitle(title);
		PageModel<Post> page = postService.paging(param);
		model.put("page", page);
		model.put("param", param);
		model.put("title", title);
		model.put("id", id);
		model.put("group", group);
		return "/admin/posts/list";
	}
	
	/**
	 * 跳转到文章编辑方法
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String toUpdate(Long id, ModelMap model) {
		Post ret = postService.get(id);
		model.put("view", ret);
		model.put("groups", channelService.findAll(Consts.STATUS_NORMAL));
		return "/admin/posts/update";
	}
	
	/**
	 * 更新文章方法
	 * @author LBB
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String subUpdate(Post p, HttpServletRequest request) {
		if (p != null) {
			String content = request.getParameter("content");
			p.setContent(content);
			postService.update(p);
		}
		return "redirect:/admin/posts/list";
	}

	/**
	 * 设置/取消 置顶
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/featured")
	@ResponseBody
	public Data featured(Long id, HttpServletRequest request) {
		Data data = Data.failure("操作失败");
		int featured = ServletRequestUtils.getIntParameter(request, "featured", Consts.FEATURED_ACTIVE);
		if (id != null) {
			try {
				postService.updateFeatured(id, featured);
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}

	/**
	 * 设置/取消 推荐
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/weight")
	@ResponseBody
	public Data weight(Long id, HttpServletRequest request) {
		Data data = Data.failure("操作失败");
		//SpringMVC控制器ServletRequestUtils获取值不存在 使用默认值
		int weight = ServletRequestUtils.getIntParameter(request, "weight", Consts.FEATURED_ACTIVE);
		if (id != null) {
			try {
				postService.updateWeight(id, weight);
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Data delete(@RequestParam("id") List<Long> id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			try {
				postService.delete(id);
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
}
