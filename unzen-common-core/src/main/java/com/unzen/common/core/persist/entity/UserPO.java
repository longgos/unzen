/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.core.persist.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.unzen.common.core.data.AuthMenu;

/**
 * 用户信息
 *
 * @author langhsu
 *
 */
public class UserPO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username; // 用户名

	private String password; // 密码

	private String avatar;  // 头像

	private String name;  // 昵称

	private Date updated;//修改时间
	
	private int gender;   // 性别
	
	private String openId;//微信openId

	private String email;  // 邮箱

	private String mobile;  // 手机号

	private int posts; // 文章数

	private int comments; // 发布评论数

	private int follows; // 关注人数

	private int fans; // 粉丝数

	private int favors; // 收到的喜欢数

	private Date created;  // 注册时间

	private int source; // 注册来源：主要用于区别第三方登录

	private Date lastLogin;//最后登录时间

	private String signature; // 个性签名

	/** 权限菜单 */
	private List<AuthMenuPO> roleAuths = new ArrayList<>();

	private int activeEmail; // 邮箱激活状态 0：启动；1：禁用
	private int status; // 用户状态 0：启动；1：禁用
	
	private int roleId ;//权限id

	public UserPO() {

	}

	public UserPO(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	/*public List<RolePO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolePO> roles) {
		this.roles = roles;
	}*/

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getActiveEmail() {
		return activeEmail;
	}

	public void setActiveEmail(int activeEmail) {
		this.activeEmail = activeEmail;
	}

	public int getPosts() {
		return posts;
	}

	public void setPosts(int posts) {
		this.posts = posts;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public int getFollows() {
		return follows;
	}

	public void setFollows(int follows) {
		this.follows = follows;
	}

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

	public int getFavors() {
		return favors;
	}

	public void setFavors(int favors) {
		this.favors = favors;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public List<AuthMenuPO> getRoleAuths() {
		return roleAuths;
	}

	public void setRoleAuths(List<AuthMenuPO> roleAuths) {
		this.roleAuths = roleAuths;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	
	
}
