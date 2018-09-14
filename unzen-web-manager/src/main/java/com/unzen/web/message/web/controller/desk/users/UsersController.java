/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.web.message.web.controller.desk.users;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unzen.common.core.data.User;
import com.unzen.common.service.UserService;
import com.unzen.web.message.web.controller.BaseController;
import com.unzen.web.message.web.controller.desk.Views;

/**
 * 访问他人主页
 * @author langhsu
 *
 */
@Controller
public class UsersController extends BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/users/{uid}")
	public String home(@PathVariable Long uid, HttpServletRequest request, ModelMap model) {
		User user = userService.get(uid);
		int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);

		model.put("user", user);
		model.put("pn", pn);
		return view(Views.USERS_VIEW);
	}
}
