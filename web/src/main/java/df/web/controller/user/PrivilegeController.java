package df.web.controller.user;

import df.base.persistence.entity.user.Privilege;
import df.base.mapping.user.PrivilegeMapper;
import df.base.dto.user.PrivilegeDTO;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.service.user.PrivilegeService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import static df.base.Messages.SUCCESS_PRIVILEGE_SAVED;
import static df.web.common.flash.FlashMessage.success;

@Controller
public class PrivilegeController implements PrivilegeOperations {

    private final PrivilegeService privilegeService;
    private final ControllerHelper controllerHelper;

    public PrivilegeController(ControllerHelper controllerHelper, PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
        this.controllerHelper = controllerHelper;

        controllerHelper.setRedirectUrl(MAVConstants.REDIRECT_PRIVILEGE);
    }

    @Override
    public ModelAndView index() {
        bindAttributes(new PrivilegeDTO());

        controllerHelper.setViewName(MAVConstants.VIEW_PRIVILEGE);

        return controllerHelper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(String privilegeId, RedirectAttributes attributes) {
        controllerHelper.setViewName(MAVConstants.VIEW_PRIVILEGE);
        controllerHelper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new PrivilegeMapper().map(privilegeService.requireById(privilegeId)));
            mav = controllerHelper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = controllerHelper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(PrivilegeDTO privilegeDTO, BindingResult result, RedirectAttributes attributes) {
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.setBindingResult(result);
        controllerHelper.setViewName(MAVConstants.VIEW_PRIVILEGE);

        bindAttributes(privilegeDTO);

        if (!result.hasFieldErrors()) {
            Privilege privilege = privilegeService.createOrUpdate(privilegeDTO);
            controllerHelper.addMessage(success(SUCCESS_PRIVILEGE_SAVED.formatted(privilege.getId())));
        }

        return controllerHelper.resolveWithRedirect();
    }

    private void bindAttributes(PrivilegeDTO privilegeDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("itemDTO", privilegeDTO);
        attributes.put("privileges", privilegeService.getAll());

        controllerHelper.attributes(attributes);
    }

}
