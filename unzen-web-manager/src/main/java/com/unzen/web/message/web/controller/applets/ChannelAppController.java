package com.unzen.web.message.web.controller.applets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unzen.common.core.data.Post;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.service.PostService;
import com.unzen.web.message.web.controller.BaseController;

@Controller
@RequestMapping(value="${appPath}/unzen")
public class ChannelAppController extends BaseController{
	
	@Autowired
	private PostService postService;

	@RequestMapping(value="")
	@ResponseBody
	public PageModel<Post> channel(Model model){
		PostParam param = new PostParam();
        param.getPager().setPageSize(15);
        PageModel<Post> result = postService.paging(param);
        List<Post> postList = result.getDatas();
        for (Post post : postList) {
        	if(post.getPicturesPath()!= null){
        		String [] paths = post.getPicturesPath().split(",");
        		if(paths.length>0)post.setPicturesPathArray(paths);;
        	}
		}
        return result;
       
	}
}
