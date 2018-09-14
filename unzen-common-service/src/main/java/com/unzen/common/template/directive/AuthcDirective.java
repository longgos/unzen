package com.unzen.common.template.directive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unzen.common.core.data.AuthMenu;
import com.unzen.common.service.AuthMenuService;
import com.unzen.common.template.DirectiveHandler;
import com.unzen.common.template.TemplateDirective;

/**
 * Created by langhsu on 2017/11/21.
 */
@Component
public class AuthcDirective extends TemplateDirective {
    @Autowired
    private AuthMenuService authMenuService;

    @Override
    public String getName() {
        return "authc";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        long pid = handler.getInteger("pid", 0);

        List<AuthMenu> list = authMenuService.findByParentId(pid);
        handler.put(RESULTS, list).render();
    }

}
