package com.unzen.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.AuthMenu;
import com.unzen.common.core.data.Role;
import com.unzen.common.core.persist.entity.AuthMenuPO;
import com.unzen.common.core.persist.entity.RolePO;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.dao.AuthMenuDao;
import com.unzen.common.dao.RoleDao;
import com.unzen.common.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private AuthMenuDao authMenuDao;

	@Override
	@Transactional(readOnly = true)
	public DataPageModel<Role> paging() {
		DataPageModel<RolePO> page = roleDao.findAllByOrderByIdDesc();
		List<Role> roles = new ArrayList<>();
		/** jdk7不兼容8版本新循环格式。暂时挂起选用旧循环方式 */
		List<RolePO> data = page.getDatas();
		for (int i = 0; i < data.size(); i++) {
			roles.add(BeanMapUtils.copy(data.get(i)));
		}
		/*page.getDatas().forEach(po -> {
			roles.add(BeanMapUtils.copy(po));
		});*/
		DataPageModel<Role> role = new DataPageModel<Role>();
		role.setDatas(roles);
		return role;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Role get(Long id) {
		RolePO po = roleDao.selectOne(id);
		Role role = BeanMapUtils.copy(po);
		return role;
	}

	@Override
	@Transactional
	public void save(Role role){
		RolePO rolePO = new RolePO();
		List<AuthMenu> authMenus = role.getAuthMenus();
		List<AuthMenuPO> authMenuPOs = new ArrayList<>();
		for(AuthMenu authMenu : authMenus){
			AuthMenuPO authMenuPO = authMenuDao.get(authMenu.getId());
			authMenuPOs.add(authMenuPO);
		}
		BeanUtils.copyProperties(role, rolePO);
		rolePO.setAuthMenus(authMenus);
		//18-09-10注掉
		//rolePO.setAuthMenus(authMenuPOs);
		roleDao.save(rolePO);
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long id) {
		roleDao.delete(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Role> getAll() {
		List<RolePO> list = roleDao.findAll();
		List<Role> roles = new ArrayList<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < list.size(); i++) {
			roles.add(BeanMapUtils.copy(list.get(i)));
		}
		/*list.forEach(po -> {
			roles.add(BeanMapUtils.copy(po));
		});*/
		return roles;
	}
	

}
