package df.web.controller.form;

import df.common.breadcrumb.Breadcrumbs;
import df.application.dto.form.FieldDTO;
import df.application.dto.form.FormDTO;
import df.application.validation.groups.Operations;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import org.jmouse.validator.old.ValidationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FORM_EMBEDDED)
public interface FormEmbeddedOperations extends DefaultOperations<FormDTO> {

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms", url = "/form"),
            @Breadcrumbs.Item(label = "Embedded Fields '{primaryId}'")
    })
    @GetMapping
    ModelAndView index(@PathVariable("primaryId") String primaryId, RedirectAttributes attributes);

    @PostMapping("/attach")
    ModelAndView attach(
            @PathVariable("primaryId") String primaryId,
            @ModelAttribute("itemDTO") @Validated(Operations.PrimaryId.class) FieldDTO itemDTO,
            BindingResult result, RedirectAttributes attributes,
            ValidationContext context);

    @GetMapping("/{embeddedId}/detach")
    ModelAndView detach(
            @PathVariable("primaryId") String primary,
            @PathVariable("embeddedId") String embeddedId,
            RedirectAttributes attributes);

    @GetMapping("/{embeddedId}/sequence")
    ModelAndView order(
            @PathVariable("primaryId") String primary,
            @PathVariable("embeddedId") String embeddedId,
            @RequestParam("newOrder") Integer newOrder,
            RedirectAttributes attributes);

}
