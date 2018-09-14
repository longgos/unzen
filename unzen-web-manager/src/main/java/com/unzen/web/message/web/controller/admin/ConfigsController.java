/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.web.message.web.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unzen.base.data.Data;
import com.unzen.base.lang.Consts;
import com.unzen.common.core.data.Config;
import com.unzen.common.service.ChannelService;
import com.unzen.common.service.ConfigService;
import com.unzen.common.service.PostService;
import com.unzen.web.message.web.controller.BaseController;

/**
 * 系统配置
 *
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/admin/config")
public class ConfigsController extends BaseController {
	@Autowired
	private ConfigService configService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private PostService postService;

	@RequestMapping("/")
	public String list(ModelMap model) {
		model.put("configs", configService.findAll2Map());
		return "/admin/configs/main";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request, ModelMap model) {
		Map<String, String[]> params = request.getParameterMap();

		List<Config> configs = new ArrayList<Config>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (Entry<String, String[]> param : params.entrySet()) {
			Config conf = new Config();
			conf.setKey(param.getKey());
			conf.setValue(param.getValue()[0]);
			configs.add(conf);
		}
		/*params.forEach((k, v) -> {
			Config conf = new Config();
			conf.setKey(k);
			conf.setValue(v[0]);

			configs.add(conf);
		});*/
		configService.update(configs);
		return "redirect:/admin/config/";
	}
	
	@RequestMapping("/flush_conf")
	public @ResponseBody Data flushFiledia() {
		// 刷新系统变量
		List<Config> configs = configService.findAll();

		Map<String, String> configMap = new HashMap<String, String>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < configs.size(); i++) {
			servletContext.setAttribute(configs.get(i).getKey(), configs.get(i).getValue());
			configMap.put(configs.get(i).getKey(), configs.get(i).getValue());
		}
		/*configs.forEach(conf -> {
			servletContext.setAttribute(conf.getKey(), conf.getValue());
			configMap.put(conf.getKey(), conf.getValue());
		});*/

		appContext.setConfig(configMap);

		// 刷新文章Group
		servletContext.setAttribute("channels", channelService.findAll(Consts.STATUS_NORMAL));
		return Data.success("操作成功", Data.NOOP);
	}

	@RequestMapping("/flush_indexs")
	public @ResponseBody Data flushIndexs() {
		postService.resetIndexs();
		return Data.success("操作成功", Data.NOOP);
	}
	
}
