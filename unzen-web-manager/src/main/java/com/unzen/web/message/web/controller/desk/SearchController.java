/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.web.message.web.controller.desk;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Post;
import com.unzen.common.service.PostService;
import com.unzen.web.message.web.controller.BaseController;


/**
 * 文章搜索
 * @author langhsu
 *
 */
@Controller
public class SearchController extends BaseController {
	@Autowired
	private PostService postService;

	@RequestMapping("/search")
	public String search(String kw, ModelMap model) {
//		Pageable pageable = wrapPageable();
		try {
			if (StringUtils.isNotEmpty(kw)) {
				Post post = new Post();
				post.getChannel().setKey(kw);
				DataPageModel<Post> page = postService.search(post);
				model.put("page", page);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("kw", kw);
		return view(Views.BROWSE_SEARCH);
	}
	
}
