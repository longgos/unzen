/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.service;

import java.util.Date;

/**
 * @author langhsu
 *
 */
public interface LogService {
	void add(int logType, long userId, long targetId, String ip);
}
