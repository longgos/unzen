/**
 *
 */
package com.unzen.common.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.service.PostService;
import com.unzen.common.template.DirectiveHandler;
import com.unzen.common.template.TemplateDirective;


/**
 * 根据作者取文章列表
 *
 * @author langhsu
 *
 */
@Component
public class AuthorContentsDirective extends TemplateDirective {
    @Autowired
	private PostService postService;

	@Override
	public String getName() {
		return "author_contents";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        int pn = handler.getInteger("pn", 1);
        long uid = handler.getInteger("uid", 0);

//        Pageable pageable = new PageRequest(pn - 1, 10);
        PostParam param = new PostParam();
        param.getAuthor().setId(uid);
        PageModel<Post> result = postService.pagingByAuthorId(param);

        handler.put(RESULTS, result).render();
    }

}
