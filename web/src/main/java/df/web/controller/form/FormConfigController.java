package df.web.controller.form;

import df.base.security.UserInfo;
import df.base.service.form.FormService;
import df.web.common.ControllerHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/form/config")
public class FormConfigController {

    private final FormService      service;
    private final ControllerHelper helper;

    public FormConfigController(FormService service, ControllerHelper helper) {
        this.service = service;
        this.helper = helper;
        service.setRedirectUrl("/form/config");
        helper.setRedirectUrl("/form/config");
    }

    @GetMapping("/{formId}")
    public ModelAndView index(@AuthenticationPrincipal UserInfo principal) {
        helper.setViewName("form/config");

        return helper.resolveWithoutRedirect();
    }

}
