package df.web.controller.form;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.dto.SecondaryDTO;
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

@RequestMapping(MAVConstants.FORM_FIELD_FIELD_ID_CUSTOMIZATION)
public interface FieldCustomizationOperations extends DefaultOperations<SecondaryDTO> {


    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Configuring field-{section}")
    })
    @GetMapping("/{section}")
    ModelAndView index(@PathVariable("section") String section, @PathVariable("primaryId") String ownerId);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Modify Item {itemId}")
    })
    @GetMapping("/{section}/{itemId}/modify")
    ModelAndView modify(
            @PathVariable("section") String section,
            @PathVariable("primaryId") String primaryId,
            @PathVariable("itemId") String itemId,
            RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Performing...")
    })
    @PostMapping("/perform/config")
    ModelAndView perform(@ModelAttribute("itemDTO") @Validated(Operations.Secondary.class) FieldConfigDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Performing...'")
    })
    @PostMapping("/perform/attribute")
    ModelAndView perform(@ModelAttribute("itemDTO") @Validated(Operations.Secondary.class) FieldAttributeDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Performing...'")
    })
    @PostMapping("/perform/option")
    ModelAndView perform(@ModelAttribute("itemDTO") @Validated(Operations.Secondary.class) FieldOptionDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);

}
