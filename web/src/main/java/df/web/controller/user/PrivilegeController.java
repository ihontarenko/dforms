package df.web.controller.user;

import df.base.jpa.Privilege;
import df.base.mapper.user.PrivilegeMapper;
import df.base.model.user.PrivilegeDTO;
import df.base.service.ResourceNotFoundException;
import df.base.service.user.PrivilegeService;
import df.web.common.ControllerHelper;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import static df.base.Messages.SUCCESS_PRIVILEGE_SAVED;
import static df.web.common.flash.FlashMessage.success;

@Controller
@RequestMapping("/user/privilege")
@PreAuthorize("hasRole('ADMIN')")
public class PrivilegeController {

    private final PrivilegeService service;
    private final ControllerHelper helper;

    public PrivilegeController(ControllerHelper helper, PrivilegeService service) {
        this.service = service;
        this.helper = helper;

        helper.setRedirectUrl("/user/privilege");
    }

    @GetMapping
    public ModelAndView index() {
        bindAttributes(new PrivilegeDTO());

        helper.setViewName("user/privilege");

        return helper.resolveWithoutRedirect();
    }

    @GetMapping("/{privilegeId}/modify")
    public ModelAndView modify(@PathVariable("privilegeId") String privilegeId, RedirectAttributes attributes) {
        helper.setViewName("user/privilege");
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new PrivilegeMapper().map(service.requireById(privilegeId)));
            mav = helper.resolveWithoutRedirect();
        } catch (ResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @PostMapping("/perform")
    public ModelAndView perform(@Valid PrivilegeDTO privilegeDTO, BindingResult result, RedirectAttributes attributes) {
        helper.setRedirectAttributes(attributes);
        helper.setBindingResult(result);
        helper.setViewName("user/privilege");

        bindAttributes(privilegeDTO);

        if (!result.hasFieldErrors()) {
            Privilege privilege = service.createOrUpdate(privilegeDTO);
            helper.addMessage(success(SUCCESS_PRIVILEGE_SAVED.formatted(privilege.getId())));
        }

        return helper.resolveWithRedirect();
    }

    private void bindAttributes(PrivilegeDTO privilegeDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("privilegeDTO", privilegeDTO);
        attributes.put("privileges", service.getAll());

        helper.attributes(attributes);
    }

}
