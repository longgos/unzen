/**
 * 
 */
package com.unzen.web.message.web.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unzen.base.lang.Consts;
import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.service.PostService;
import com.unzen.web.message.web.controller.BaseController;


/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/api")
public class PostJsonController extends BaseController {
	@Autowired
	private PostService postService;
	
	@RequestMapping("/posts")
	@ResponseBody
	public PageModel<Post> posts(HttpServletRequest request) {
		String order = ServletRequestUtils.getStringParameter(request, "ord", Consts.order.NEWEST);
		int gid = ServletRequestUtils.getIntParameter(request, "gid", 0);
//		Pageable pageable = wrapPageable();
		PostParam param = new PostParam();
		param.setChannelId(gid);
		param.setOrd(order);
		PageModel<Post> page = postService.paging(param);
		return page;
	}
}
