package df.web.controller.form;

import df.base.internal.breadcrumb.Breadcrumbs;
import df.base.model.form.FormDTO;
import df.base.security.UserInfo;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FORM)
public interface FormOperations extends DefaultOperations<FormDTO> {

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms")
    })
    ModelAndView index();

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Create Form")
    })
    ModelAndView create();

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms", url = "/form"),
            @Breadcrumbs.Item(label = "Modify '{itemId}'")
    })
    ModelAndView modify(@PathVariable("itemId") String itemId,
                        RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Performing")
    })
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid FormDTO itemDTO, BindingResult result,
                         RedirectAttributes attributes);

    ModelAndView remove(@PathVariable("itemId") String itemId,
                        RedirectAttributes attributes);

    ModelAndView status(@PathVariable("itemId") String itemId, @PathVariable("status") String status,
                        RedirectAttributes attributes);

}
