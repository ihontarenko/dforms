package df.web.controller.form;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.dto.KeyValueDTO;
import df.base.dto.form.FieldAttributeDTO;
import df.base.dto.form.FieldConfigDTO;
import df.base.dto.form.FieldOptionDTO;
import df.base.validation.groups.Operations;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FIELD_EXTRA)
public interface FieldCustomizationOperations extends DefaultOperations<KeyValueDTO> {


    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Field Configuring '{ownerId}'")
    })
    @GetMapping("/{section}")
    ModelAndView index(@PathVariable("section") String section, @PathVariable("fieldId") String ownerId);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Modify Item {itemId}")
    })
    @Override
    ModelAndView modify(@PathVariable("itemId") String itemId, RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Field Config Performing'")
    })
    @PostMapping("/perform/configs")
    ModelAndView perform(@ModelAttribute("itemDTO") @Validated(Operations.Advanced.class) FieldConfigDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Attribute Performing'")
    })
    @PostMapping("/perform/attributes")
    ModelAndView perform(@ModelAttribute("itemDTO") @Validated(Operations.Advanced.class) FieldAttributeDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Element Option Performing'")
    })
    @PostMapping("/perform/options")
    ModelAndView perform(@ModelAttribute("itemDTO") @Validated(Operations.Advanced.class) FieldOptionDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);

}
