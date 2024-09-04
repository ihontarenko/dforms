package df.web.controller.user;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.dto.user.RoleDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_ROLE)
@PreAuthorize("hasRole('ADMIN')")
public interface RoleOperations extends DefaultOperations<RoleDTO> {

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Roles")
    })
    ModelAndView index();

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Roles", url = "/role"),
            @Breadcrumbs.Item(label = "Modify '{itemId}'"),
    })
    ModelAndView modify(@PathVariable("itemId") String itemId, RedirectAttributes attributes);

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Roles", url = "/role"),
            @Breadcrumbs.Item(label = "Performing"),
    })
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid RoleDTO roleDTO, BindingResult result, RedirectAttributes attributes);

}
