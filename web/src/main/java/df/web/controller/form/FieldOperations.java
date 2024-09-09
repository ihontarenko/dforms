package df.web.controller.form;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.common.breadcrumb.Breadcrumbs.Item;
import df.base.common.validation.custom.ValidationContext;
import df.base.dto.form.FieldDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FORM_FIELD)
public interface FieldOperations extends DefaultOperations<FieldDTO> {

    @Override
    @Breadcrumbs({
            @Item(label = "Home", url = "/"),
            @Item(label = "Fields")
    })
    @GetMapping
    ModelAndView index();

    @Override
    @Breadcrumbs({
            @Item(label = "Home", url = "/"),
            @Item(label = "Fields", url = "/form/field"),
            @Item(label = "Create Field")
    })
    @GetMapping("/create")
    ModelAndView create();

    @Override
    @Breadcrumbs({
            @Item(label = "Home", url = "/"),
            @Item(label = "Fields", url = "/form/field"),
            @Item(label = "Modify '{itemId}'")
    })
    @GetMapping("/{itemId}/modify")
    ModelAndView modify(@PathVariable("itemId") String itemId, RedirectAttributes attributes);

    @Breadcrumbs({
            @Item(label = "Home", url = "/"),
            @Item(label = "Fields", url = "/form/field"),
            @Item(label = "Performing...")
    })
    @PostMapping("/perform")
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid FieldDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes, ValidationContext context);

    @Override
    @GetMapping("/{itemId}/remove")
    ModelAndView remove(@PathVariable("itemId") String itemId, RedirectAttributes attributes);

    @GetMapping("/{itemId}/status/{status}")
    default ModelAndView status(@PathVariable("itemId") String itemId,
                                @PathVariable("status") String status,
                                RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }


}
