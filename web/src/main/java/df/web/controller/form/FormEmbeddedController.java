package df.web.controller.form;

import df.base.common.validation.custom.ValidationContext;
import df.base.dto.form.FieldDTO;
import df.base.persistence.entity.form.Form;
import df.base.service.form.FieldService;
import df.base.service.form.FormService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static df.web.controller.MAVConstants.REDIRECT_FIELD_CUSTOMIZATION;

@Controller
public class FormEmbeddedController implements FormEmbeddedOperations {

    private final FieldService     fieldService;
    private final FormService      formService;
    private final ControllerHelper controllerHelper;

    public FormEmbeddedController(FormService formService, FieldService fieldService, ControllerHelper controllerHelper) {
        this.fieldService = fieldService;
        this.formService = formService;
        this.controllerHelper = controllerHelper;
    }

    @Override
    public ModelAndView index(String primaryId, RedirectAttributes attributes) {
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.setRedirectUrl(REDIRECT_FIELD_CUSTOMIZATION.formatted(primaryId, "config"));
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_EMBEDDED);

        ModelAndView mav;

        try {
            bindAttributes(primaryId);
            mav = controllerHelper.resolveWithoutRedirect();
        } catch (Exception exception) {
            mav = controllerHelper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView attach(String primaryId, FieldDTO itemDTO,
                               BindingResult result, RedirectAttributes attributes, ValidationContext context) {
        return null;
    }

    @Override
    public ModelAndView detach(String primary, String embeddedId, RedirectAttributes attributes) {
        return null;
    }

    private void bindAttributes(String primaryId) {
        Form formEntity = formService.requireById(primaryId);

        controllerHelper.attribute("embeddable", fieldService.getEligibleFields());
        controllerHelper.attribute("form", formEntity);
    }

}
