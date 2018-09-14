/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.web.message.web.controller.desk.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.AccountProfile;
import com.unzen.common.core.data.BadgesCount;
import com.unzen.common.core.data.Comment;
import com.unzen.common.core.data.Favor;
import com.unzen.common.core.data.Feeds;
import com.unzen.common.core.data.Notify;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.data.User;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.service.CommentService;
import com.unzen.common.service.FavorService;
import com.unzen.common.service.FeedsService;
import com.unzen.common.service.FollowService;
import com.unzen.common.service.NotifyService;
import com.unzen.common.service.PostService;
import com.unzen.common.service.UserService;
import com.unzen.web.message.shiro.authc.AccountSubject;
import com.unzen.web.message.web.controller.BaseController;
import com.unzen.web.message.web.controller.desk.Views;


/**
 * 用户主页
 * @author langhsu
 *
 */
@Controller
public class UserController extends BaseController {
	@Autowired
	private PostService postService;
	@Autowired
	private FeedsService feedsService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private FollowService followService;
	@Autowired
	private FavorService favorService;
	@Autowired
	private NotifyService notifyService;

	/**
	 * 用户主页
	 * @param model
	 * @return
	 */
	@GetMapping("/user")
	public String home(ModelMap model) {
		AccountSubject subject = getSubject();
		Feeds f = new Feeds();
		f.setOwnId(subject.getProfile().getId());
		PageModel<Feeds> page = feedsService.findPage(f);
		model.put("page", page);
		initUser(model);

		return view(Views.USER_FEEDS);
	}

	/**
	 * 我发布的文章
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user", params = "method=posts")
	public String posts(ModelMap model) {
//		Pageable pageable = wrapPageable();
		AccountProfile up = getSubject().getProfile();
		PostParam post = new PostParam();
		post.getAuthor().setId(up.getId());
		PageModel<Post> page = postService.pagingByAuthorId(post);

		model.put("page", page);
		initUser(model);

		return view(Views.USER_POSTS);
	}

	/**
	 * 我发表的评论
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user", params = "method=comments")
	public String comments(ModelMap model) {
//		Pageable pageable = wrapPageable();
		AccountSubject subject = getSubject();
		PostParam post = new PostParam();
		post.setAuthorId(subject.getProfile().getId());
		DataPageModel<Comment> page = commentService.findPageHome(post);

		model.put("page", page);
		initUser(model);

		return view(Views.USER_COMMENTS);
	}

	/**
	 * 我喜欢过的文章
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user", params = "method=favors")
	public String favors(ModelMap model) {
//		Pageable pageable = wrapPageable();
		AccountProfile profile = getSubject().getProfile();
		Favor favor = new Favor();
		favor.setOwnId(profile.getId());
		DataPageModel<Favor> page = favorService.findPage(favor);
		model.put("page", page);
		initUser(model);

		return view(Views.USER_FAVORS);
	}

	/**
	 * 我的关注
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user", params = "method=follows")
	public String follows(ModelMap model) {
//		Pageable pageable = wrapPageable();
		AccountProfile profile = getSubject().getProfile();
		User user = new User();
		user.setId(profile.getId());
		DataPageModel<User> page = followService.findMyFollow(user);

		model.put("page", page);
		initUser(model);

		return view(Views.USER_FOLLOWS);
	}

	/**
	 * 我的粉丝
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user", params = "method=fans")
	public String fans(ModelMap model) {
//		Pageable pageable = wrapPageable();
		AccountProfile profile = getSubject().getProfile();
		User user = new User();
		user.setId(profile.getId());
		DataPageModel<User> page = followService.findFollowMe(user);

		model.put("page", page);
		initUser(model);

		return view(Views.USER_FANS);
	}

	/**
	 * 我的通知
	 * @param model
	 * @return
	 */
	@GetMapping(value="/user", params = "method=notifies")
	public String notifies(ModelMap model) {
//		Pageable pageable = wrapPageable();
		AccountProfile profile = getSubject().getProfile();
		Notify nof = new Notify();
		nof.setOwnId(profile.getId());
		DataPageModel<Notify> page = notifyService.findByOwnId(nof);
		// 标记已读
		notifyService.readed4Me(profile.getId());

		model.put("page", page);
		initUser(model);

		return view(Views.USER_NOTIFIES);
	}

	private void initUser(ModelMap model) {
		AccountProfile up = getSubject().getProfile();
		User user = userService.get(up.getId());

		model.put("user", user);

		pushBadgesCount();
	}

	private void pushBadgesCount() {
		AccountProfile profile = (AccountProfile) session.getAttribute("profile");
		if (profile != null && profile.getBadgesCount() != null) {
			BadgesCount count = new BadgesCount();
			count.setNotifies(notifyService.unread4Me(profile.getId()));
			profile.setBadgesCount(count);
			session.setAttribute("profile", profile);
		}
	}

	public static void main(String[] args) {
		char []a = {'a','b','c','d','e','f','g'};
		String s = new String(a,1,3);
		Arrays.copyOf(a, a.length);
		System.out.println(a);
	}
}
