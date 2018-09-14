package com.unzen.web.message.web.controller.admin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unzen.common.core.data.AuthMenu;
import com.unzen.common.core.data.Role;
import com.unzen.common.service.AuthMenuService;
import com.unzen.common.service.RoleService;
import com.unzen.web.message.web.controller.BaseController;


@Controller
@RequestMapping("/admin/authMenus")
public class AuthMenuController extends BaseController{

    @Autowired
    private AuthMenuService authMenuService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("authMenu")
    public AuthMenu get(@RequestParam(required=false) String id) {
        if (id!=null&&!id.equals("0")){
            return authMenuService.get(Long.valueOf(id));
        }else{
            return new AuthMenu();
        }
    }

    @RequestMapping("/list")
    public String list(ModelMap model) {
        List<AuthMenu> list = authMenuService.listAll();
        model.put("list", list);
        return "/admin/authMenus/list";
    }

    /**
     * 添加下级菜单
     * @param parentId
     * @param authMenu
     * @param model
     * @return
     */
    @RequestMapping(value = "view")
    public String view(@RequestParam(required = false) Long parentId, AuthMenu authMenu, Model model) {
        if(parentId==null){
            parentId = 1L;
        }
        AuthMenu parent = authMenuService.get(parentId);
        model.addAttribute("parentId",parentId);
        model.addAttribute("parent",parent);
        model.addAttribute("authMenu", authMenu);
        return "/admin/authMenus/view";
    }

    /*public String view1(AuthMenu authMenu, Model model){
    	if(parentId==null){
    		parentId = 1L;
    	}
    	 AuthMenu parent = authMenuService.findList(param);
    }*/
    
    @RequestMapping("/save")
    public String save(AuthMenu authMenu,Model model){
        authMenuService.save(authMenu);
        return "redirect:/admin/authMenus/list";
    }

    @RequestMapping("/delete")
    public String delete(Long id,Model model){
        authMenuService.delete(id);
        return "redirect:/admin/authMenus/list";
    }

    @RequestMapping("treeView")
    public String treeView(String parentId,Model model){
        model.addAttribute("parentId",parentId);
        return "/admin/authMenus/treeView";
    }

    @RequestMapping("tree")
    @ResponseBody
    public List<AuthMenu.Node> tree(@RequestParam(required = false) Long roleId){
        HashMap<Long, AuthMenu> map = new LinkedHashMap<>();

        if (roleId != null && roleId != 0) {
            Role role = roleService.get(roleId);
            List<AuthMenu> authedMenus = role.getAuthMenus();

            if (authedMenus != null)
            	/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
            	for (int i = 0; i < authedMenus.size(); i++) {
            		map.put(authedMenus.get(i).getId(), authedMenus.get(i));
				}
//                authedMenus.forEach(n -> map.put(n.getId(), n));
        }
        List<AuthMenu> list = authMenuService.listAll();

        List<AuthMenu.Node> results = new LinkedList<>();

        for(AuthMenu a: list){
            AuthMenu.Node m = a.toNode();

            if (!map.isEmpty() && map.get(m.getId()) != null)
                m.setChecked(true);

            results.add(m);
        }

        return results;
    }
}