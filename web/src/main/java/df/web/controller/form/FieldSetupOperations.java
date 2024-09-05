package df.web.controller.form;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.dto.form.FieldConfigDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FORM_FIELD_SETUP)
public interface FieldSetupOperations extends DefaultOperations<FieldConfigDTO> {

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Field Setup '{ownerId}'")
    })
    ModelAndView index(@PathVariable("ownerId") String formId);


    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Fields", url = "/form/field"),
            @Breadcrumbs.Item(label = "Modify '{itemId}'")
    })
    ModelAndView modify(@PathVariable("itemId") String itemId, RedirectAttributes attributes);


}
