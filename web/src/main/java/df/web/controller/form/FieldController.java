package df.web.controller.form;

import df.base.persistence.entity.form.Field;
import df.base.persistence.support.EntityGraphConstants;
import df.base.persistence.repository.form.FieldRepository;
import df.base.persistence.entity.support.ElementType;
import df.base.persistence.entity.support.FieldStatus;
import df.base.persistence.entity.support.UsageType;
import df.base.mapper.form.FormFieldMapper;
import df.base.dto.form.FormFieldDTO;
import df.base.service.JpaResourceNotFoundException;
import df.base.service.form.FieldService;
import df.web.common.ControllerHelper;
import df.web.common.flash.FlashMessage;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.function.Function;

import static df.base.Messages.*;
import static df.base.common.spring.jpa.entity_graph.JpaEntityGraph.Named.load;
import static df.web.common.flash.FlashMessage.*;
import static df.web.common.flash.FlashMessage.error;

@Controller
public class FieldController implements FieldOperations {

    private final ControllerHelper controllerHelper;
    private final FieldService     fieldService;

    // todo: test
    private final FieldRepository repository;

    public FieldController(ControllerHelper controllerHelper, FieldService fieldService, FieldRepository repository) {
        this.fieldService = fieldService;
        this.controllerHelper = controllerHelper;
        this.repository = repository;
        controllerHelper.setRedirectUrl(MAVConstants.REDIRECT_FORM_FIELD);
        fieldService.setRedirectUrl(MAVConstants.REDIRECT_FORM_FIELD);
    }

    @Override
    public ModelAndView index() {
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_FIELD_LIST);

        bindAttributes(new FormFieldDTO());

        repository.findByStatus(FieldStatus.ACTIVE, load(EntityGraphConstants.FORM_FIELD_WITH_CONFIGS));

        return controllerHelper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView create() {
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_FIELD_FORM);

        bindAttributes(new FormFieldDTO());

        return controllerHelper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(String itemId, RedirectAttributes attributes) {
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_FIELD_FORM);
        controllerHelper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FormFieldMapper().map(fieldService.requireById(itemId)));
            mav = controllerHelper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = controllerHelper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(FormFieldDTO fieldDTO, BindingResult result, RedirectAttributes attributes) {
        controllerHelper.setBindingResult(result);
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_FIELD_FORM);

        bindAttributes(fieldDTO);

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

    private void bindAttributes(FormFieldDTO itemDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("itemDTO", itemDTO);
        attributes.put("elementTypes", ElementType.values());
        attributes.put("fields", fieldService.getAll());
        attributes.put("fieldStatuses", FieldStatus.values());
        attributes.put("usageTypes", UsageType.values());

        controllerHelper.attributes(attributes);
    }

}
