package df.web.controller.form;

import df.base.common.validation.custom.Validation;
import df.base.common.validation.custom.ValidationContext;
import df.base.dto.form.FieldDTO;
import df.base.mapping.form.FieldMapper;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.support.ElementType;
import df.base.persistence.entity.support.FieldStatus;
import df.base.persistence.entity.support.UsageType;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.persistence.repository.form.FieldRepository;
import df.base.service.form.FieldService;
import df.base.validation.groups.Operations;
import df.web.common.ControllerHelper;
import df.web.common.flash.FlashMessage;
import df.web.controller.MAVConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static df.base.Messages.*;
import static df.web.common.flash.FlashMessage.*;

@Controller
public class FieldController implements FieldOperations {

    private final Validation       validation;
    private final ControllerHelper controllerHelper;
    private final FieldService     fieldService;

    // todo: test
    private final FieldRepository repository;

    public FieldController(@Qualifier("fieldValidation") Validation validation, ControllerHelper controllerHelper,
                           FieldService fieldService, FieldRepository repository) {
        this.validation = validation;
        this.fieldService = fieldService;
        this.controllerHelper = controllerHelper;
        this.repository = repository;

        controllerHelper.setRedirectUrl(MAVConstants.REDIRECT_FORM_FIELD);
        fieldService.setRedirectUrl(MAVConstants.REDIRECT_FORM_FIELD);
    }

    @Override
    public ModelAndView index() {
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_FIELD_LIST);

        bindAttributes(new FieldDTO());

        return controllerHelper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView create() {
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_FIELD_FORM);

        bindAttributes(new FieldDTO());

        return controllerHelper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(String itemId, RedirectAttributes attributes) {
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_FIELD_FORM);
        controllerHelper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FieldMapper().map(fieldService.requireById(itemId)));
            mav = controllerHelper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = controllerHelper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(@Validated(Operations.Primary.class) FieldDTO fieldDTO,
                                BindingResult result, RedirectAttributes attributes, ValidationContext context) {
        controllerHelper.setBindingResult(result);
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_FIELD_FORM);

        bindAttributes(fieldDTO);

        validation.validate(fieldDTO, context, result);

        if (!result.hasFieldErrors()) {
            Field field = fieldService.createOrUpdate(fieldDTO);
            controllerHelper.addMessage(success(SUCCESS_FIELD_SAVED.formatted(field.getLabel())));
        }

        return controllerHelper.resolveWithRedirect();
    }

    @Override
    public ModelAndView remove(String itemId, RedirectAttributes attributes) {
        controllerHelper.setRedirectAttributes(attributes);

        Optional<Field> result = fieldService.getById(itemId);

        if (result.isPresent()) {
            fieldService.delete(result.get());
            controllerHelper.addMessage(error(SUCCESS_FIELD_DELETED.formatted(itemId)));
        } else {
            controllerHelper.addMessage(warning(ERROR_FIELD_NOT_FOUND.formatted(itemId)));
        }

        return controllerHelper.redirect();
    }

    @Override
    public ModelAndView status(String itemId, String status, RedirectAttributes attributes) {
        Optional<Field> result = fieldService.getById(itemId);
        controllerHelper.setRedirectAttributes(attributes);

        if (result.isPresent()) {
            FieldStatus formStatus = FieldStatus.valueOf(status.toUpperCase());
            EnumMap<FieldStatus, Function<String, FlashMessage>> messages = new EnumMap<>(FieldStatus.class) {{
                put(FieldStatus.ACTIVE, FlashMessage::success);
                put(FieldStatus.INACTIVE, FlashMessage::dark);
                put(FieldStatus.DELETED, FlashMessage::error);
            }};
            fieldService.changeStatus(result.get(), formStatus);
            controllerHelper.addMessage(messages.get(formStatus).apply(
                    SUCCESS_FIELD_STATUS_CHANGED
                            .formatted(itemId, formStatus)));
        } else {
            controllerHelper.addMessage(error(ERROR_FIELD_NOT_FOUND
                    .formatted(itemId)));
        }

        return controllerHelper.redirect();
    }

    private void bindAttributes(FieldDTO itemDTO) {
        Map<String, Object> attributes = new HashMap<>();
        FieldMapper         mapper     = new FieldMapper();

        attributes.put("itemDTO", itemDTO);
        attributes.put("elementTypes", ElementType.values());
        attributes.put("fields", fieldService.getAll().stream().map(mapper::map).toList());
        attributes.put("fieldStatuses", FieldStatus.values());
        attributes.put("usageTypes", UsageType.values());

        controllerHelper.attributes(attributes);
    }

}
