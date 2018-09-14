/**
 * 
 */
package com.unzen.web.message.web.controller.desk.comment;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.unzen.base.data.Data;
import com.unzen.base.lang.Consts;
import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.AccountProfile;
import com.unzen.common.core.data.Comment;
import com.unzen.common.event.NotifyEvent;
import com.unzen.common.service.CommentService;
import com.unzen.web.message.web.controller.BaseController;


/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 查看博客评论列表
	 * @param toId
	 * @return
	 */
	@RequestMapping("/list/{toId}")
	@ResponseBody
	public DataPageModel<Comment> view( Long toId) {//@PathVariable
//		Pageable pageable = wrapPageable();
		Comment comment = new Comment();
		comment.setToId(toId);
		DataPageModel<Comment> page = commentService.paging(comment);
		return page;
	}
	
	@RequestMapping("/submit")
	public @ResponseBody
	Data post(Long toId, String text, HttpServletRequest request) {
		Data data = Data.failure("操作失败");
		
		long pid = ServletRequestUtils.getLongParameter(request, "pid", 0);
		
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			data = Data.failure("请先登录在进行操作");
			
			return data;
		}
		if (toId > 0 && StringUtils.isNotEmpty(text)) {
			AccountProfile up = getSubject().getProfile();
			
			Comment c = new Comment();
			c.setToId(toId);
			c.setContent(HtmlUtils.htmlEscape(text));
			c.setAuthorId(up.getId());
			
			c.setPid(pid);
			
			commentService.post(c);

            if(toId != up.getId()) {
			    sendNotify(up.getId(), toId, pid);
            }
			
			data = Data.success("发表成功!", Data.NOOP);
		}
		return data;
	}

	@RequestMapping("/delete")
	public @ResponseBody Data delete(Long id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			AccountProfile up = getSubject().getProfile();
			try {
				commentService.delete(id, up.getId());
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}

	/**
	 * 发送通知
	 * @param userId
	 * @param postId
	 */
	private void sendNotify(long userId, long postId, long pid) {
		NotifyEvent event = new NotifyEvent("NotifyEvent");
		event.setFromUserId(userId);

		if (pid > 0) {
			event.setEvent(Consts.NOTIFY_EVENT_COMMENT_REPLY);
		} else {
			event.setEvent(Consts.NOTIFY_EVENT_COMMENT);
		}
		// 此处不知道文章作者, 让通知事件系统补全
		event.setPostId(postId);
		applicationContext.publishEvent(event);
	}
}