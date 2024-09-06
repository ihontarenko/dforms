package df.web.controller.user;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.dto.user.PrivilegeDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_PRIVILEGE)
@PreAuthorize("hasRole('ADMIN')")
public interface PrivilegeOperations extends DefaultOperations<PrivilegeDTO> {

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Privileges")
    })
    ModelAndView index();

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Privileges", url = "/privilege"),
            @Breadcrumbs.Item(label = "Modify '{itemId}'"),
    })
    @GetMapping("/{itemId}/modify")
    ModelAndView modify(@PathVariable("itemId") String itemId, RedirectAttributes attributes);

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Roles", url = "/role"),
            @Breadcrumbs.Item(label = "Performing"),
    })
    @PostMapping("/perform")
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid PrivilegeDTO privilegeDTO, BindingResult result,
                         RedirectAttributes attributes);

}
