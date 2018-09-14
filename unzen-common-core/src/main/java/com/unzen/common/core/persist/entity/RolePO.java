package com.unzen.common.core.persist.entity;

import java.util.ArrayList;
import java.util.List;

import com.unzen.common.core.data.AuthMenu;

public class RolePO {

	private long id;

	private String name;

	private List<AuthMenu> authMenus = new ArrayList<AuthMenu>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<AuthMenu> getAuthMenus() {
		return authMenus;
	}

	public void setAuthMenus(List<AuthMenu> authMenus) {
		this.authMenus = authMenus;
	}



}
