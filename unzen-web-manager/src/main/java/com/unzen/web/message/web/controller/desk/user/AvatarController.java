/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.web.message.web.controller.desk.user;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unzen.base.context.AppContext;
import com.unzen.base.data.Data;
import com.unzen.base.utils.FilePathUtils;
import com.unzen.base.utils.ImageUtils;
import com.unzen.common.core.data.AccountProfile;
import com.unzen.common.core.persist.utils.Base64Util;
import com.unzen.common.service.UserService;
import com.unzen.web.message.web.controller.BaseController;
import com.unzen.web.message.web.controller.desk.Views;

/**
 * @author langhsu
 *
 */
@Controller
@RequestMapping("/user")
public class AvatarController extends BaseController {
	@Autowired
	private AppContext appContext;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public String view() {
		return view(Views.USER_AVATAR);
	}
	
	@RequestMapping(value = "/avatar", method = RequestMethod.POST)
	public String post(String path, Float x, Float y, Float width, Float height, ModelMap model) {
		AccountProfile profile = getSubject().getProfile();
		
		if (StringUtils.isEmpty(path)) {
			model.put("data", Data.failure("请选择图片"));
			return view(Views.USER_AVATAR);
		}
		
		if (width != null && height != null) {
			String root = fileRepoFactory.select().getRoot();
			File temp = new File(root + path);
			File scale = null;
			
			// 目标目录
			String ava100 = appContext.getAvaDir() + getAvaPath(profile.getId(), 100);
			String dest = root + ava100;
			try {
				// 判断父目录是否存在
				File f = new File(dest);
		        if(!f.getParentFile().exists()){
		            f.getParentFile().mkdirs();
		        }
		        // 在目标目录下生成截图
		        String scalePath = f.getParent() + "/" + profile.getId() + ".jpg";
				ImageUtils.truncateImage(temp.getAbsolutePath(), scalePath, x.intValue(), y.intValue(), width.intValue());
		        
				// 对结果图片进行压缩
				ImageUtils.scaleImage(scalePath, dest, 100);

				AccountProfile user = userService.updateAvatar(profile.getId(), ava100);
				putProfile(user);
				
				scale = new File(scalePath);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				temp.delete();
				if (scale != null) {
					scale.delete();
				}
			}
		}
		return "redirect:/user/profile";
	}
	
	public String getAvaPath(long uid, int size) {
		String base = FilePathUtils.getAvatar(uid);
		return String.format("/%s_%d.jpg", base, size);
	}
	
	
	@RequestMapping(value="/userAvatar", method = RequestMethod.POST)
	@ResponseBody
	public boolean avatar(String imgStr, ModelMap model){
		try {
			String newJson = imgStr.replaceAll("&quot;","\"");
			newJson = newJson.substring(14);
			String path = Base64Util.GenerateImage(newJson);
			if(path == null){
				return false;
			}
			AccountProfile profile = getSubject().getProfile();
			Long id = profile.getId();
			userService.updateAvatar(id, path);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
}
