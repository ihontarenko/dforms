package df.web.controller.user;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.dto.user.UserDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_USER)
@PreAuthorize("hasRole('ADMIN')")
public interface UserOperations extends DefaultOperations<UserDTO> {

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Users")
    })
    @GetMapping
    ModelAndView index();

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Users", url = "/user"),
            @Breadcrumbs.Item(label = "Modify '{userId}'"),
    })
    @GetMapping("/{itemId}/modify")
    ModelAndView modify(@PathVariable("itemId") String itemId,
                        RedirectAttributes attributes);

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Users", url = "/user"),
            @Breadcrumbs.Item(label = "Performing"),
    })
    @PostMapping("/perform")
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid UserDTO userDTO, BindingResult result,
                         RedirectAttributes attributes);

}
