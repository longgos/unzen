package com.unzen.web.message.web.controller.applets;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.util.StringUtil;
import com.unzen.base.context.UnzenConsts;
import com.unzen.common.core.data.User;
import com.unzen.common.service.UserService;

/**
 * 小程序注册
 * @author ljk
 * 
 */
@Controller
@RequestMapping(value="${appPath}/login")
public class MiniLoginController {

	@Autowired
	private UserService userService;
	
	/**
	 * 小程序初次授权注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="register")
	@ResponseBody
	public boolean register(@RequestBody User user){
		String nickname ="DearApril"; //user.getUsername();
		if(!StringUtil.isEmpty(nickname)){
			nickname = nickname.replaceAll("[\ue000-\uefff]","");
			user.setName(nickname);
			user.setUsername(nickname);
			user.setPassword(UnzenConsts.DEFAULT_PWD);
			user.setStatus(UnzenConsts.STATUS_0);
			user.setSource(UnzenConsts.SOURCE_3);
			user.setComments(UnzenConsts.DEFAULT_NUM_0);
			user.setFans(UnzenConsts.DEFAULT_NUM_0);
			user.setFavors(UnzenConsts.DEFAULT_NUM_0);
			user.setFollows(UnzenConsts.DEFAULT_NUM_0);
			user.setPosts(UnzenConsts.DEFAULT_NUM_0);
			user.setLastLogin(new Date());
			user.setRoleId(UnzenConsts.ROLE_2);
			userService.register(user);
			return true;
		}else{
			return false;
		}
	}
}
