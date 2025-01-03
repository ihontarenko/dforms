package df.web.controller.form;

import df.common.validation.custom.ValidationContext;
import df.application.dto.form.FieldDTO;
import df.application.service.form.FieldService;
import df.application.service.form.FormService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static df.application.Messages.*;
import static df.web.common.flash.FlashMessage.primary;
import static df.web.common.flash.FlashMessage.success;
import static df.web.controller.MAVConstants.REDIRECT_FIELD_CUSTOMIZATION;
import static df.web.controller.MAVConstants.REDIRECT_FORM_EMBEDDED;

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
        controllerHelper.setRedirectUrl(REDIRECT_FORM_EMBEDDED.formatted(primaryId));
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.addMessage(success(SUCCESS_FORM_FIELD_ATTACHED.formatted(primaryId, itemDTO.id())));

        formService.attach(primaryId, itemDTO.id());

        return controllerHelper.redirect();
    }

    @Override
    public ModelAndView detach(String primary, String embeddedId, RedirectAttributes attributes) {
        controllerHelper.setRedirectUrl(REDIRECT_FORM_EMBEDDED.formatted(primary));
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.addMessage(primary(SUCCESS_FORM_FIELD_DETACHED.formatted(primary, embeddedId)));

        formService.detach(primary, embeddedId);

        return controllerHelper.redirect();
    }

    @Override
    public ModelAndView order(String primary, String embeddedId, Integer newOrder,
                              RedirectAttributes attributes) {
        controllerHelper.setRedirectUrl(REDIRECT_FORM_EMBEDDED.formatted(primary));
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.addMessage(primary(SUCCESS_FORM_FIELD_ORDER_CHANGED.formatted("field", embeddedId)));

        formService.reorder(primary, embeddedId, newOrder);

        return controllerHelper.redirect();
    }

    private void bindAttributes(String primaryId) {
        controllerHelper.attribute("embeddable", fieldService.getEligibleFields());
        controllerHelper.attribute("form", formService.requireById(primaryId));
    }

}
