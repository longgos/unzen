/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.core.persist.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.unzen.common.core.data.AccountProfile;
import com.unzen.common.core.data.AuthMenu;
import com.unzen.common.core.data.Channel;
import com.unzen.common.core.data.Comment;
import com.unzen.common.core.data.Favor;
import com.unzen.common.core.data.Feeds;
import com.unzen.common.core.data.Notify;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.data.Role;
import com.unzen.common.core.data.User;
import com.unzen.common.core.persist.entity.AuthMenuPO;
import com.unzen.common.core.persist.entity.ChannelPO;
import com.unzen.common.core.persist.entity.CommentPO;
import com.unzen.common.core.persist.entity.FavorPO;
import com.unzen.common.core.persist.entity.FeedsPO;
import com.unzen.common.core.persist.entity.NotifyPO;
import com.unzen.common.core.persist.entity.PostPO;
import com.unzen.common.core.persist.entity.RolePO;
import com.unzen.common.core.persist.entity.UserPO;

/**
 * @author langhsu
 *
 */
public class BeanMapUtils {
	public static String[] USER_IGNORE = new String[]{"password", "extend", "roles"};

	public static String[] POST_IGNORE_LIST = new String[]{"markdown", "content"};

	public static User copy(UserPO po, int level) {
		if (po == null) {
			return null;
		}
		
		User ret = new User();
		List<AuthMenuPO> list = po.getRoleAuths();
		List<AuthMenu> amList = new ArrayList<>();
		for (AuthMenuPO authMenuPO : list) {
			AuthMenu am = new AuthMenu();
			BeanDataUtils.copyProperties(authMenuPO, am);
			amList.add(am);
		}
		BeanUtils.copyProperties(po, ret, USER_IGNORE);
		ret.setRoleAuths(amList);
		//ret.setRoleMenus(po.getRoleAuths());
		//暂注释 等待解决 日期：18-09-10
		/*if (level > 0) {
			List<AuthMenu> authMenus = po.getRoleAuths();
			List<Role> roles = new ArrayList<Role>();
			for(AuthMenu authmenu :authMenus){
				//Role role = copy(authmenu);
				Role role = new Role();
				role.setAuthMenus(authmenu);
				roles.add(role);
			}
			ret.setRoles(roles);
		}*/
		return ret;
	}

	public static AccountProfile copyPassport(UserPO po) {
		AccountProfile passport = new AccountProfile(po.getId(), po.getUsername());
		passport.setName(po.getName());
		passport.setEmail(po.getEmail());
		passport.setAvatar(po.getAvatar());
		passport.setLastLogin(po.getLastLogin());
		passport.setStatus(po.getStatus());
		passport.setActiveEmail(po.getActiveEmail());

		List<AuthMenuPO> menus = po.getRoleAuths();
		// 18-09-10 注释
		/*List<AuthMenu> menus = new ArrayList<AuthMenu>();
		List<RolePO> rolePOs = po.getRoles();
		List<Role> roles = new ArrayList<Role>();
		for(RolePO rolePo :rolePOs){
			Role role = copy(rolePo);
			roles.add(role);
		}
		for(Role role : roles){
			List<AuthMenu> authMenus = role.getAuthMenus();
			menus.addAll(authMenus);
		}*/
		User user = copy(po,0);
		passport.setAuthMenus(user.getRoleAuths());
		return passport;
	}

	public static Comment copy(CommentPO po) {
		Comment ret = new Comment();
		BeanUtils.copyProperties(po, ret);
		return ret;
	}
	public static Post copy(PostPO po, int level) {
		Post d = new Post();
		if (level > 0) {
			BeanUtils.copyProperties(po, d);
		} else {
			BeanUtils.copyProperties(po, d, POST_IGNORE_LIST);
		}
		return d;
	}

	public static Channel copy(ChannelPO po) {
		Channel r = new Channel();
		BeanUtils.copyProperties(po, r);
		return r;
	}

	public static AuthMenu copy(AuthMenuPO po) {
		AuthMenu am = new AuthMenu();
		BeanUtils.copyProperties(po, am, "children");
		return am;
	}

	public static Role copy(RolePO po) {
		Role r = new Role();
		BeanUtils.copyProperties(po, r, "users", "authMenus");
		List<AuthMenu> authMenus = new ArrayList<>();
		authMenus = po.getAuthMenus();
		//18-09-10注释
		/*for (AuthMenuPO authMenuPO : po.getAuthMenus()) {
			AuthMenu authMenu = new AuthMenu();
			BeanUtils.copyProperties(authMenuPO, authMenu, "roles", "children");
			authMenus.add(authMenu);
		}*/
		r.setAuthMenus(authMenus);
		return r;
	}

	public static Feeds copy(FeedsPO po) {
		Feeds ret = new Feeds();
		BeanUtils.copyProperties(po, ret);
		return ret;
	}

	public static Notify copy(NotifyPO po) {
		Notify ret = new Notify();
		BeanUtils.copyProperties(po, ret);
		return ret;
	}

	public static Favor copy(FavorPO po) {
		Favor ret = new Favor();
		BeanUtils.copyProperties(po, ret);
		return ret;
	}

}
