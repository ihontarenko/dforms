package df.web.controller.form;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.common.breadcrumb.Breadcrumbs.Item;
import df.base.dto.form.FormFieldDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FORM_FIELD)
public interface FieldOperations extends DefaultOperations<FormFieldDTO> {

    @Override
    @Breadcrumbs({
            @Item(label = "Home", url = "/"),
            @Item(label = "Fields")
    })
    ModelAndView index();

    @Override
    @Breadcrumbs({
            @Item(label = "Home", url = "/"),
            @Item(label = "Fields", url = "/form/field"),
            @Item(label = "Create Field")
    })
    ModelAndView create();

    @Override
    @Breadcrumbs({
            @Item(label = "Home", url = "/"),
            @Item(label = "Fields", url = "/form/field"),
            @Item(label = "Modify '{itemId}'")
    })
    ModelAndView modify(@PathVariable("itemId") String itemId, RedirectAttributes attributes);

    @Override
    @Breadcrumbs({
            @Item(label = "Home", url = "/"),
            @Item(label = "Fields", url = "/form/field"),
            @Item(label = "Performing...")
    })
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid FormFieldDTO itemDTO,
                         BindingResult result, RedirectAttributes attributes);


}
