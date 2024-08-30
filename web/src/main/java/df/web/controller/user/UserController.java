package df.web.controller.user;

import df.base.jpa.User;
import df.base.mapper.user.UserMapper;
import df.base.model.user.UserDTO;
import df.base.service.JpaResourceNotFoundException;
import df.base.service.user.RoleService;
import df.base.service.user.UserService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import static df.base.Messages.SUCCESS_USER_SAVED;
import static df.web.common.flash.FlashMessage.success;

@SuppressWarnings({"unused"})
@Controller
public class UserController implements UserOperations {

    private final UserService      userService;
    private final RoleService      roleService;
    private final ControllerHelper controllerHelper;

    public UserController(ControllerHelper controllerHelper, UserService userService, RoleService roleService) {
        this.controllerHelper = controllerHelper;
        this.userService = userService;
        this.roleService = roleService;

        controllerHelper.setRedirectUrl(MAVConstants.REDIRECT_USER);
    }

    @Override
    public ModelAndView index() {
        bindAttributes(new UserDTO());

        controllerHelper.setViewName(MAVConstants.VIEW_USER);

        return controllerHelper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(String userId, RedirectAttributes attributes) {
        controllerHelper.setViewName(MAVConstants.VIEW_USER);
        controllerHelper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new UserMapper().map(userService.requireById(userId)));
            mav = controllerHelper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = controllerHelper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(UserDTO userDTO, BindingResult result, RedirectAttributes attributes) {
        controllerHelper.setBindingResult(result);
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.setViewName(MAVConstants.VIEW_USER);

        bindAttributes(userDTO);

        if (!result.hasFieldErrors()) {
            User user = userService.createOrUpdate(userDTO);
            controllerHelper.addMessage(success(SUCCESS_USER_SAVED.formatted(user.getId())));
        }

        return controllerHelper.resolveWithRedirect();
    }

    private void bindAttributes(UserDTO userDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("itemDTO", userDTO);
        attributes.put("users", userService.getAll());
        attributes.put("roles", roleService.getAll());

        controllerHelper.attributes(attributes);
    }

}
