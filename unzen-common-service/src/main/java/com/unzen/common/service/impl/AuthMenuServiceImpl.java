package com.unzen.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.common.core.data.AuthMenu;
import com.unzen.common.core.persist.entity.AuthMenuPO;
import com.unzen.common.core.persist.param.AuthMenuParam;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.dao.AuthMenuDao;
import com.unzen.common.service.AuthMenuService;


/**
 * 系统配置信息
 * @author ljk
 *
 */
@Service
@Transactional
public class AuthMenuServiceImpl implements AuthMenuService {
	
	@Autowired
	private AuthMenuDao authMenuDao;

	@Override
	public List<AuthMenu> findByParentId(long parentId) {
		// TODO Auto-generated method stub
		List<AuthMenu> authMenus = new ArrayList<>();
		List<AuthMenuPO> authMenuPOs = authMenuDao.findAllByParentIdOrderBySortAsc(parentId);
		if(authMenuPOs!=null){
			for(AuthMenuPO po : authMenuPOs){
				AuthMenu authMenu = BeanMapUtils.copy(po);
				authMenus.add(authMenu);
			}
		}
		return authMenus;
	}

	@Override
	public List<AuthMenu> tree(Long id) {

		List<AuthMenu> menus = new ArrayList<>();
		AuthMenuPO authMenuPO = authMenuDao.get(id);
		AuthMenu authMenu = BeanMapUtils.copy(authMenuPO);
		menus.add(authMenu);
		if(authMenu.getChildren()!=null){
//			List<AuthMenu> sortedList = sort(authMenu.getChildren());
			for (AuthMenu po: authMenu.getChildren()){
				menus.addAll(tree(po.getId()));
			}
		}
		return menus;
	}

	@Override
	public List<AuthMenu> listAll() {
		List<AuthMenuPO> list = authMenuDao.findAll();
		List<AuthMenu> rets = new ArrayList<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < list.size(); i++) {
			AuthMenu a = new AuthMenu();
			BeanUtils.copyProperties(list.get(i), a, "parent", "roles", "children");
			rets.add(a);
		}
		/*list.forEach(po -> {
			AuthMenu a = new AuthMenu();
			BeanUtils.copyProperties(po, a, "parent", "roles", "children");
			rets.add(a);
		});*/
		return rets;
	}

	@Override
	public AuthMenu get(Long id) {
		AuthMenu authMenu = BeanMapUtils.copy(authMenuDao.get(id));
		return authMenu;
	}

	@Override
	@Transactional(readOnly=false)
	public void save(AuthMenu authMenu) {
		AuthMenuPO po = new AuthMenuPO();
		BeanUtils.copyProperties(authMenu, po);
		authMenuDao.save(po);
	}

	@Override
	public void delete(Long id) {
		AuthMenuPO authMenuPO = authMenuDao.get(id);
		if(authMenuPO.getChildren()!=null){
			for(AuthMenuPO po : authMenuPO.getChildren()){
				delete(po.getId());
			}
		}
		authMenuDao.delete(authMenuPO);
	}

	@Override
	public List<AuthMenu> findList(AuthMenuParam param) {
		return authMenuDao.findList(param);
	}

	/**
	 * 根据权限id查询权限菜单
	 */
	@Override
	public List<AuthMenuPO> findAuthMenuByRole(int roleId) {
		return authMenuDao.findAuthMenuByRole(roleId);
	}

//	private List<AuthMenu> sort(List<AuthMenu> list) {
//		for(int i=0;i<list.size();i++){
//			for(int j=list.size()-1;j>i;j--){
//				if(list.get(i).getSort()>list.get(j).getSort()){
//					AuthMenu temp = list.get(i);
//					list.set(i,list.get(j));
//					list.set(j,temp);
//				}
//			}
//		}
//		return list;
//	}

}
