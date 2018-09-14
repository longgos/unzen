/**
 *
 */
package com.unzen.common.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unzen.base.lang.Consts;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.service.PostService;
import com.unzen.common.template.DirectiveHandler;
import com.unzen.common.template.TemplateDirective;

/**
 * 主页文章内容查询执行器
 *
 * 示例：
 * 	请求：http://mtons.com/index?ord=newest&pn=2
 *  使用：@contents group=x pn=pn ord=ord
 *
 * @author langhsu
 *
 */
@Component
public class ContentsDirective extends TemplateDirective {
	@Autowired
    private PostService postService;

    @Override
    public String getName() {
        return "contents";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer pn = handler.getInteger("pn", 1);
        Integer channelId = handler.getInteger("channelId", 0);
        String order = handler.getString("order", Consts.order.NEWEST);

//        Pageable pageable = new PageRequest(pn - 1, 15);
        PostParam param = new PostParam();
        param.getPager().setPageSize(15);
        param.setChannelId(channelId);
		param.setOrd(order);
        PageModel<Post> result = postService.paging(param);

        handler.put(RESULTS, result).render();
    }
}
