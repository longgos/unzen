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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.base.lang.Consts;
import com.unzen.common.core.data.Channel;
import com.unzen.common.core.persist.entity.ChannelPO;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.dao.ChannelDao;
import com.unzen.common.service.ChannelService;


/**
 * @author langhsu
 *
 */
@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService {
	@Autowired
	private ChannelDao channelDao;

	@Override
	public List<Channel> findAll(int status) {
		List<ChannelPO> list;
		if (status > Consts.IGNORE) {
			
			list = channelDao.findAllByStatus(String.valueOf(status));
		} else {
			list = channelDao.findAll();
		}
		List<Channel> rets = new ArrayList<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < list.size(); i++) {
			rets.add(BeanMapUtils.copy(list.get(i)));
		}
//		list.forEach(po -> rets.add(BeanMapUtils.copy(po)));
		return rets;
	}

	@Override
	public Map<Integer, Channel> findMapByIds(Collection<Integer> ids) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("ids", ids);
		List<ChannelPO> list = channelDao.findAllByIdIn(map);
		Map<Integer, Channel> rets = new HashMap<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < list.size(); i++) {
			rets.put(list.get(i).getId(), BeanMapUtils.copy(list.get(i)));
		}
//		list.forEach(po -> rets.put(po.getId(), BeanMapUtils.copy(po)));
		return rets;
	}

	@Override
	@Cacheable(value = "groupsCaches", key = "'g_' + #id")
	public Channel get(int id) {
		return BeanMapUtils.copy(channelDao.get(id));
	}

	@Override
	@Cacheable(value = "groupsCaches", key = "'g_' + #key")
	public Channel getByKey(String key) {
		return BeanMapUtils.copy(channelDao.findByKey(key));
	}

	@Override
	@Transactional
	public void update(Channel channel) {
		ChannelPO po = channelDao.get(channel.getId());
		if (po != null) {
			BeanUtils.copyProperties(channel, po);
		} else {
			po = new ChannelPO();
			BeanUtils.copyProperties(channel, po);
		}
		channelDao.save(po);
	}

	@Override
	@Transactional
	public void delete(int id) {
		channelDao.delete(id);
	}


}
