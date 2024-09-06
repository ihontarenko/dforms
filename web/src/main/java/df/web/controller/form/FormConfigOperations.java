package df.web.controller.form;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.dto.form.FormConfigDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FORM_CONFIG)
public interface FormConfigOperations extends DefaultOperations<FormConfigDTO> {

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms", url = "/form"),
            @Breadcrumbs.Item(label = "Configurations '{ownerId}'")
    })
    @GetMapping
    ModelAndView index(@PathVariable("primaryId") String primaryId);


    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms", url = "/form"),
            @Breadcrumbs.Item(label = "Modify '{itemId}'")
    })
    @GetMapping("/{itemId}/modify")
    ModelAndView modify(@PathVariable("primaryId") String primaryId, @PathVariable("itemId") String itemId,
                        RedirectAttributes attributes);

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms", url = "/form"),
            @Breadcrumbs.Item(label = "Performing")
    })
    @PostMapping("/perform")
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid FormConfigDTO configDTO, BindingResult result,
                         RedirectAttributes attributes);

    @Override
    @GetMapping("/{itemId}/remove")
    ModelAndView remove(String itemId, RedirectAttributes attributes);


}
