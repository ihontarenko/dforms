package df.web.controller.form;

import df.common.breadcrumb.Breadcrumbs;
import df.application.dto.KeyValuePair;
import df.application.dto.form.FieldAttributeDTO;
import df.application.dto.form.FieldConfigDTO;
import df.application.dto.form.FieldDTO;
import df.application.dto.form.FieldOptionDTO;
import df.application.validation.groups.Operations;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.FORM_FIELD_FIELD_ID_CUSTOMIZATION)
public interface FieldCustomizationOperations extends DefaultOperations<KeyValuePair> {


    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Configuring field-{section}")
    })
    @GetMapping("/{section:[config|attribute|option|embedded]+}")
    ModelAndView index(
            @PathVariable("section") String section,
            @PathVariable("primaryId") String ownerId,
            RedirectAttributes attributes);

    @PostMapping("/embedded/attach")
    ModelAndView attach(
            @PathVariable("primaryId") String primaryId,
            @ModelAttribute("itemDTO") @Validated(Operations.PrimaryId.class) FieldDTO itemDTO,
            BindingResult result, RedirectAttributes attributes,
            org.jmouse.validator.old.ValidationContext context);

    @GetMapping("/embedded/{embeddedId}/detach")
    ModelAndView detach(
            @PathVariable("primaryId") String primary,
            @PathVariable("embeddedId") String embeddedId,
            RedirectAttributes attributes);

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
    @PostMapping("/config/perform")
    ModelAndView perform(@PathVariable("primaryId") String primaryId,
                         @ModelAttribute("itemDTO") @Validated(Operations.Secondary.class) FieldConfigDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Performing...")
    })
    @PostMapping("/attribute/perform")
    ModelAndView perform(@PathVariable("primaryId") String primaryId,
                         @ModelAttribute("itemDTO") @Validated(Operations.Secondary.class) FieldAttributeDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Performing...")
    })
    @PostMapping("/option/perform")
    ModelAndView perform(@PathVariable("primaryId") String primaryIdm,
                         @ModelAttribute("itemDTO") @Validated(Operations.Secondary.class) FieldOptionDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);

    @GetMapping("/{section}/{itemId}/remove")
    ModelAndView remove(
            @PathVariable("section") String section,
            @PathVariable("primaryId") String primaryId,
            @PathVariable("itemId") String itemId,
            RedirectAttributes attributes);

}
