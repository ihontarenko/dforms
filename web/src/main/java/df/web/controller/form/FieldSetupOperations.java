package df.web.controller.form;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldNestedDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FIELD_SETUP)
public interface FieldSetupOperations extends DefaultOperations<FieldDTO> {

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Field Setup '{ownerId}'")
    })
    ModelAndView index(@PathVariable("ownerId") String formId);

    ModelAndView perform(@ModelAttribute("itemDTO") @Valid FieldDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);


}
