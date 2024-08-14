package df.web.controller.user;

import df.base.mapper.user.UserMapper;
import df.base.model.user.UserDTO;
import df.base.security.UserInfo;
import df.base.service.user.RoleService;
import df.base.service.user.UserService;
import df.web.common.flash.FlashMessageService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final FlashMessageService flash;
    private final UserService userService;
    private final RoleService roleService;

    public UserController(FlashMessageService flash, UserService userService, RoleService roleService) {
        this.flash = flash;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("user/index");

        bindVariables(mav, new UserDTO());

        return mav;
    }

    @GetMapping("/{userId}/modify")
    public ModelAndView modify(@PathVariable("userId") String userId) {
        ModelAndView mav = new ModelAndView("user/index");

        bindVariables(mav, new UserMapper().map(userService.requiredById(userId)));

        return mav;
    }

    @PostMapping("/perform")
    public ModelAndView perform(@Valid UserDTO userDTO, BindingResult result, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("user/index");

        bindVariables(mav, userDTO);

        return mav;
    }

    private void bindVariables(ModelAndView mav, UserDTO userDTO) {
        mav.addObject("userDTO", userDTO);
        mav.addObject("users", userService.getAll());
        mav.addObject("roles", roleService.getAll());
    }

}
