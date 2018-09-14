package com.unzen.common.core.persist.entity;

import java.util.ArrayList;
import java.util.List;

public class AuthMenuPO {
	
	private Long id;
	
	private String name;
	
	private String url;
	
	private int sort;
	
	private String permission;
	
//	@Column(name="parent_id")
	private Long parentId;
	
//	@Column(name="parent_ids")
	private String parentIds;

	private String icon;

//	@ManyToMany(mappedBy = "authMenus", fetch=FetchType.LAZY)
//	@Fetch(FetchMode.SUBSELECT)
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<RolePO> roles = new ArrayList<RolePO>();
	
//	@ManyToMany(fetch=FetchType.LAZY)
//	@JoinTable(name = "mto_role_menu", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "menu_id") })
	private List<AuthMenuPO> children = new ArrayList<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<RolePO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolePO> roles) {
		this.roles = roles;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public List<AuthMenuPO> getChildren() {
		return children;
	}

	public void setChildren(List<AuthMenuPO> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
