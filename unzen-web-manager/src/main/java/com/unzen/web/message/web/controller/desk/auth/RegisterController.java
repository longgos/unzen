/**
 * 
 */
package com.unzen.web.message.web.controller.desk.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.unzen.base.data.Data;
import com.unzen.base.lang.Consts;
import com.unzen.base.utils.MailHelper;
import com.unzen.common.core.data.AccountProfile;
import com.unzen.common.core.data.User;
import com.unzen.common.service.UserService;
import com.unzen.common.service.VerifyService;
import com.unzen.web.message.web.controller.BaseController;
import com.unzen.web.message.web.controller.desk.Views;

/**
 * @author langhsu
 *
 */
@Controller
public class RegisterController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private MailHelper mailHelper;
	
	@GetMapping("/register")
	public String view() {
		AccountProfile profile = getSubject().getProfile();
		if (profile != null) {
			return "redirect:/home";
		}
		return view(Views.REGISTER);
	}
	
	@PostMapping("/register")
	public String register(User post, ModelMap model) {
		Data data;
		String ret = view(Views.REGISTER);

		try {
			post.setAvatar(Consts.AVATAR);
			User user = userService.register(post);

			sendEmail(user);

			data = Data.success("恭喜您! 注册成功, 已经给您的邮箱发了验证码, 赶紧去完成邮箱绑定吧。", Data.NOOP);
			data.addLink("login", "先去登陆尝尝鲜");

			ret = view(Views.REGISTER_RESULT);
			
		} catch (Exception e) {
            model.addAttribute("post", post);
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		return ret;
	}

	private void sendEmail(User user) {
		String code = verifyService.generateCode(user.getId(), Consts.VERIFY_BIND, user.getEmail());
		Map<String, Object> data = new HashMap<>();
		data.put("userId", user.getId());
		data.put("code", code);
		data.put("type", Consts.VERIFY_BIND);

		mailHelper.sendEmail(Consts.EMAIL_TEMPLATE_BIND, user.getEmail(), "邮箱绑定验证", data);
	}

}