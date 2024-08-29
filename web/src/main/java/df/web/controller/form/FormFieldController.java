package df.web.controller.form;

import df.base.jpa.form.ElementType;
import df.base.jpa.form.FieldStatus;
import df.base.jpa.form.FormField;
import df.base.mapper.form.FormFieldMapper;
import df.base.model.form.FormFieldDTO;
import df.base.service.ResourceNotFoundException;
import df.base.service.form.FormFieldService;
import df.web.common.ControllerHelper;
import df.web.common.flash.FlashMessage;
import df.web.controller.MAVConstants;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.function.Function;

import static df.base.Messages.*;
import static df.web.common.flash.FlashMessage.*;
import static df.web.common.flash.FlashMessage.error;

@Controller
@RequestMapping(MAVConstants.REQUEST_MAPPING_FORM_FIELD)
public class FormFieldController implements FormFieldOperations {

    private final ControllerHelper    helper;
    private final FormFieldService    service;

    public FormFieldController(ControllerHelper helper, FormFieldService service) {
        this.service = service;
        this.helper = helper;
        helper.setRedirectUrl(MAVConstants.REDIRECT_FORM_FIELD);
        service.setRedirectUrl(MAVConstants.REDIRECT_FORM_FIELD);
    }

    @Override
    public ModelAndView index() {
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_LIST);

        bindAttributes(new FormFieldDTO());

        return helper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView create() {
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_FORM);

        bindAttributes(new FormFieldDTO());

        return helper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(@PathVariable("fieldId") String fieldId, RedirectAttributes attributes) {
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_FORM);
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FormFieldMapper().map(service.requireById(fieldId)));
            mav = helper.resolveWithoutRedirect();
        } catch (ResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(@ModelAttribute("fieldDTO") @Valid FormFieldDTO fieldDTO, BindingResult result, RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_FORM);

        bindAttributes(fieldDTO);

        if (!result.hasFieldErrors()) {
            FormField field = service.createOrUpdate(fieldDTO);
            helper.addMessage(success(SUCCESS_FIELD_SAVED.formatted(field.getLabel())));
        }

        return helper.resolveWithRedirect();
    }

    @Override
    public ModelAndView remove(@PathVariable("fieldId") String fieldId, RedirectAttributes attributes) {
        helper.setRedirectAttributes(attributes);

        Optional<FormField> result = service.getById(fieldId);

        if (result.isPresent()) {
            service.delete(result.get());
            helper.addMessage(error(SUCCESS_FIELD_DELETED.formatted(fieldId)));
        } else {
            helper.addMessage(warning(ERROR_FIELD_NOT_FOUND.formatted(fieldId)));
        }

        return helper.redirect();
    }

    @Override
    public ModelAndView status(@PathVariable("fieldId") String fieldId,
                               @PathVariable("status") String status,
                               RedirectAttributes attributes) {
        Optional<FormField> result = service.getById(fieldId);
        helper.setRedirectAttributes(attributes);

        if (result.isPresent()) {
            FieldStatus formStatus = FieldStatus.valueOf(status.toUpperCase());
            EnumMap<FieldStatus, Function<String, FlashMessage>> messages = new EnumMap<>(FieldStatus.class) {{
                put(FieldStatus.ACTIVE, FlashMessage::success);
                put(FieldStatus.INACTIVE, FlashMessage::dark);
                put(FieldStatus.DELETED, FlashMessage::error);
            }};
            service.changeStatus(result.get(), formStatus);
            helper.addMessage(messages.get(formStatus).apply(
                    SUCCESS_FIELD_STATUS_CHANGED
                            .formatted(fieldId, formStatus)));
        } else {
            helper.addMessage(error(ERROR_FIELD_NOT_FOUND
                    .formatted(fieldId)));
        }

        return helper.redirect();
    }

    private void bindAttributes(FormFieldDTO fieldDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("fieldDTO", fieldDTO);
        attributes.put("elementTypes", ElementType.values());
        attributes.put("fields", service.getAll());
        attributes.put("fieldStatuses", FieldStatus.values());

        helper.attributes(attributes);
    }

}
