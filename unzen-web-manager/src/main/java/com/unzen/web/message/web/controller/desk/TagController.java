/**
 *
 */
package com.unzen.web.message.web.controller.desk;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Post;
import com.unzen.common.service.PostService;
import com.unzen.web.message.web.controller.BaseController;


/**
 * 标签
 * @author langhsu
 *
 */
@Controller
public class TagController extends BaseController {
    @Autowired
    private PostService postService;

    @RequestMapping("/tag/{kw}")
    public String tag(@PathVariable String kw, ModelMap model) {
//        Pageable pageable = wrapPageable();
        try {
            if (StringUtils.isNotEmpty(kw)) {
            	Post post = new Post();
				post.getChannel().setKey(kw);
                DataPageModel<Post> page = postService.searchByTag(post);
                model.put("page", page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.put("kw", kw);
        return view(Views.TAGS_TAG);
    }

}
