package df.web.controller.user;

import df.base.jpa.Role;
import df.base.mapper.user.RoleMapper;
import df.base.model.user.RoleDTO;
import df.base.service.ResourceNotFoundException;
import df.base.service.user.PrivilegeService;
import df.base.service.user.RoleService;
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

import static df.base.Messages.SUCCESS_ROLE_SAVED;
import static df.web.common.flash.FlashMessage.success;

@Controller
@RequestMapping("/user/role")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private final RoleService      roleService;
    private final PrivilegeService privilegeService;
    private final ControllerHelper helper;

    public RoleController(ControllerHelper helper, RoleService roleService, PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
        this.helper = helper;
        this.roleService = roleService;

        helper.setRedirectUrl("/user/role");
    }

    @GetMapping
    public ModelAndView index() {
        bindAttributes(new RoleDTO());

        helper.setViewName("user/role");

        return helper.resolveWithoutRedirect();
    }

    @GetMapping("/{roleId}/modify")
    public ModelAndView modify(@PathVariable("roleId") String roleId, RedirectAttributes attributes) {
        helper.setViewName("user/role");
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new RoleMapper().map(roleService.requiredById(roleId)));
            mav = helper.resolveWithoutRedirect();
        } catch (ResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @PostMapping("/perform")
    public ModelAndView perform(@Valid RoleDTO roleDTO, BindingResult result, RedirectAttributes attributes) {
        helper.setViewName("user/role");
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);

        bindAttributes(roleDTO);

        if (!result.hasFieldErrors()) {
            Role role = roleService.createOrUpdate(roleDTO);
            helper.addMessage(success(SUCCESS_ROLE_SAVED.formatted(role.getId())));
        }

        return helper.resolveWithRedirect();
    }

    private void bindAttributes(RoleDTO roleDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("roleDTO", roleDTO);
        attributes.put("roles", roleService.getAll());
        attributes.put("privileges", privilegeService.getAll());

        helper.attributes(attributes);
    }

}
