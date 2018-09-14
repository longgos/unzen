/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.web.message.web.controller.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unzen.base.data.Data;
import com.unzen.common.core.data.AccountProfile;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.data.User;
import com.unzen.common.service.PostService;
import com.unzen.common.service.UserService;
import com.unzen.web.message.web.controller.BaseController;

/**
 * 侧边栏数据加载
 * 
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/api")
public class SidebarController extends BaseController {
	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	Data login(String username, String password, ModelMap model) {
		Data data = Data.failure("操作失败");

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return data;
		}

		AuthenticationToken token = createToken(username, password);
		if (token == null) {
			data.setMessage("用户名或密码错误");
			return data;
		}

		try {
			SecurityUtils.getSubject().login(token);
			data = Data.success("登录成功", getSubject().getProfile());

		} catch (Exception e) {
			if (e instanceof UnknownAccountException) {
				data.setMessage("用户不存在");
			} else if (e instanceof LockedAccountException) {
				data.setMessage("用户被禁用");
			} else {
				data.setMessage("登录认证失败");
			}
		}
		return data;
	}

	/**
	 * 最近
	 * @return
	 */
	@RequestMapping("/latests")
	@ResponseBody
	public List<Post> latests() {
		AccountProfile up = getSubject().getProfile();
		long ignoreUserId = 0;
		if (up != null) {
			ignoreUserId = up.getId();
		}
		List<Post> rets = postService.findLatests(6, ignoreUserId);
		return rets;
	}
	
	@RequestMapping("/hots")
	public @ResponseBody List<Post> hots() {
		AccountProfile up = getSubject().getProfile();
		long ignoreUserId = 0;
		if (up != null) {
			ignoreUserId = up.getId();
		}
		List<Post> rets = postService.findHots(6, ignoreUserId);
		return rets;
	}
	
	/**
	 * 热门用户
	 * @param pn
	 * @return
	 */
	@RequestMapping(value="/hotusers")
	public @ResponseBody List<User> hotusers(Integer pn) {
		List<User> rets = userService.findHotUserByfans();
         return rets;
	}
}
