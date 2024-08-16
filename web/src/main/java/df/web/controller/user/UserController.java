package df.web.controller.user;

import df.base.jpa.User;
import df.base.mapper.user.UserMapper;
import df.base.model.user.UserDTO;
import df.base.service.ResourceNotFoundException;
import df.base.service.user.RoleService;
import df.base.service.user.UserService;
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

import static df.base.Messages.SUCCESS_USER_SAVED;
import static df.web.common.flash.FlashMessage.success;

@SuppressWarnings({"unused"})
@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService      userService;
    private final RoleService      roleService;
    private final ControllerHelper helper;

    public UserController(ControllerHelper helper, UserService userService, RoleService roleService) {
        this.helper = helper;
        this.userService = userService;
        this.roleService = roleService;

        helper.setRedirectUrl("/user");
    }

    @GetMapping
    public ModelAndView index() {
        bindAttributes(new UserDTO());

        helper.setViewName("user/index");

        return helper.resolveWithoutRedirect();
    }

    @GetMapping("/{userId}/modify")
    public ModelAndView modify(@PathVariable("userId") String userId, RedirectAttributes attributes) {
        helper.setViewName("user/index");
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new UserMapper().map(userService.requireById(userId)));
            mav = helper.resolveWithoutRedirect();
        } catch (ResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @PostMapping("/perform")
    public ModelAndView perform(@Valid UserDTO userDTO, BindingResult result, RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName("user/index");

        bindAttributes(userDTO);

        if (!result.hasFieldErrors()) {
            User user = userService.createOrUpdate(userDTO);
            helper.addMessage(success(SUCCESS_USER_SAVED.formatted(user.getId())));
        }

        return helper.resolveWithRedirect();
    }

    private void bindAttributes(UserDTO userDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("userDTO", userDTO);
        attributes.put("users", userService.getAll());
        attributes.put("roles", roleService.getAll());

        helper.attributes(attributes);
    }

}
