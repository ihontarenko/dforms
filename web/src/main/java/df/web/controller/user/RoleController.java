package df.web.controller.user;

import df.application.persistence.entity.user.Role;
import df.application.mapping.user.RoleMapper;
import df.application.dto.user.RoleDTO;
import df.application.persistence.exception.JpaResourceNotFoundException;
import df.application.service.user.PrivilegeService;
import df.application.service.user.RoleService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import static df.application.Messages.SUCCESS_ROLE_SAVED;
import static df.web.common.flash.FlashMessage.success;

@Controller
public class RoleController implements RoleOperations {

    private final RoleService      roleService;
    private final PrivilegeService privilegeService;
    private final ControllerHelper controllerHelper;

    public RoleController(ControllerHelper controllerHelper, RoleService roleService, PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
        this.controllerHelper = controllerHelper;
        this.roleService = roleService;

        controllerHelper.setRedirectUrl(MAVConstants.REDIRECT_ROLE);
    }

    @Override
    public ModelAndView index() {
        bindAttributes(new RoleDTO());

        controllerHelper.setViewName(MAVConstants.VIEW_ROLE);

        return controllerHelper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(String roleId, RedirectAttributes attributes) {
        controllerHelper.setViewName(MAVConstants.VIEW_ROLE);
        controllerHelper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new RoleMapper().map(roleService.requiredById(roleId)));
            mav = controllerHelper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = controllerHelper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(RoleDTO roleDTO, BindingResult result, RedirectAttributes attributes) {
        controllerHelper.setViewName(MAVConstants.VIEW_ROLE);
        controllerHelper.setBindingResult(result);
        controllerHelper.setRedirectAttributes(attributes);

        bindAttributes(roleDTO);

        if (!result.hasFieldErrors()) {
            Role role = roleService.createOrUpdate(roleDTO);
            controllerHelper.addMessage(success(SUCCESS_ROLE_SAVED.formatted(role.getId())));
        }

        return controllerHelper.resolveWithRedirect();
    }

    private void bindAttributes(RoleDTO roleDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("itemDTO", roleDTO);
        attributes.put("roles", roleService.getAll());
        attributes.put("privileges", privilegeService.getAll());

        controllerHelper.attributes(attributes);
    }

}
