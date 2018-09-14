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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.common.core.persist.entity.LogPO;
import com.unzen.common.dao.LogDao;
import com.unzen.common.service.LogService;


/**
 * @author langhsu
 *
 */
@Service
public class LogServiceImpl implements LogService {
	@Autowired
	private LogDao logDao;
	
	@Override
	@Transactional
	public void add(int logType, long userId, long targetId, String ip) {
		LogPO po = new LogPO();
		po.setType(logType);
		po.setTargetId(targetId);
		po.setUserId(userId);
		po.setIp(ip);
		po.setCreated(new Date());
		logDao.save(po);
	}
	
}
