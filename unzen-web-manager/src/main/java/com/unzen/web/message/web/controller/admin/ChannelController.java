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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unzen.base.data.Data;
import com.unzen.base.lang.Consts;
import com.unzen.common.core.data.Channel;
import com.unzen.common.service.ChannelService;
import com.unzen.web.message.web.controller.BaseController;

/**
 * @author langhsu
 *
 */
@Controller("mng_channel_ctl")
@RequestMapping("/admin/channel")
public class ChannelController extends BaseController {
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping("/list")
	public String list(ModelMap model) {
		model.put("list", channelService.findAll(Consts.IGNORE));
		return "/admin/channel/list";
	}
	
	@RequestMapping("/view")
	public String view(Integer id, ModelMap model) {
		if (id != null) {
			Channel view = channelService.get(id);
			model.put("view", view);
		}
		return "/admin/channel/view";
	}
	
	@RequestMapping("/update")
	public String update(Channel view) {
		if (view != null) {
			channelService.update(view);
		}
		return "redirect:/admin/channel/list";
	}
	
	@RequestMapping("/delete")
	public @ResponseBody Data delete(Integer id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			try {
				channelService.delete(id);
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
	
}
