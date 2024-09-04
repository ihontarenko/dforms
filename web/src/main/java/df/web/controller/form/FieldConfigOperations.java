package df.web.controller.form;

import df.base.internal.breadcrumb.Breadcrumbs;
import df.base.model.form.FormFieldConfigDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FORM_FIELD_CONFIG)
public interface FieldConfigOperations extends DefaultOperations<FormFieldConfigDTO> {

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Configurations '{ownerId}'")
    })
    ModelAndView index(@PathVariable("ownerId") String formId);


    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Modify '{itemId}'")
    })
    ModelAndView modify(@PathVariable("itemId") String itemId, RedirectAttributes attributes);

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Performing")
    })
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid FormFieldConfigDTO configDTO, BindingResult result,
                         RedirectAttributes attributes);


}
