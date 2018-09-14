/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.base.context.AppContext;
import com.unzen.common.core.data.Config;
import com.unzen.common.core.persist.entity.ConfigPO;
import com.unzen.common.dao.ConfigDao;
import com.unzen.common.service.ConfigService;


/**
 * @author langhsu
 *
 */
@Service
public class ConfigServiceImpl implements ConfigService {
	
	@Autowired
	private ConfigDao configDao;

	@Autowired
	private AppContext appContext;

	@Override
	@Transactional(readOnly = true)
	/**
	 * 获取mto_config所有数据
	 */
	public List<Config> findAll() {
		List<ConfigPO> list = configDao.findAll();
		List<Config> rets = new ArrayList<>();
		
		for (ConfigPO po : list) {
			Config r = new Config();
			BeanUtils.copyProperties(po, r);
			rets.add(r);
		}
		return rets;
	}

	@Override
	@Transactional
	public void update(List<Config> settings) {
		if (settings == null) {
			return;
		}
		
		for (Config st :  settings) {
			ConfigPO entity = configDao.findByKey(st.getKey());

			// 修改
			if (entity != null) {
				entity.setValue(st.getValue());
			}
			// 添加
			else {
				entity = new ConfigPO();
				BeanUtils.copyProperties(st, entity);
			}
			configDao.save(entity);
			appContext.getConfig().put(entity.getKey(), entity.getValue()); //更新全局变量
			appContext.getServletContext().setAttribute(entity.getKey(),entity.getValue()); //更新容器全局变量
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Config> findAll2Map() {
		List<Config> list = findAll();
		Map<String, Config> ret = new LinkedHashMap<String, Config>();
		
		for (Config c : list) {
			ret.put(c.getKey(), c);
		}
		return ret;
	}

	public String findConfigValueByName(String key) {
		ConfigPO entity = configDao.findByKey(key);
		if (entity != null) {
			return entity.getValue();
		}
		return null;
	}
	
}
