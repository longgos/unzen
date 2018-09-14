/**
 *
 */
package com.unzen.common.template.directive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unzen.common.core.data.Post;
import com.unzen.common.service.PostService;
import com.unzen.common.template.DirectiveHandler;
import com.unzen.common.template.TemplateDirective;

/**
 * 推荐内容查询
 *
 * @author langhsu
 *
 */
@Component
public class BannerDirective extends TemplateDirective {
	@Autowired
    private PostService postService;

    @Override
    public String getName() {
        return "banner";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        List<Post> result = postService.findAllFeatured();
        handler.put(RESULTS, result).render();
    }
}
