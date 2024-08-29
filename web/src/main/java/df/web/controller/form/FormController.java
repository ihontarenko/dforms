package df.web.controller.form;

import df.base.jpa.form.Form;
import df.base.jpa.form.FormStatus;
import df.base.mapper.form.FormMapper;
import df.base.model.form.FormDTO;
import df.base.security.UserInfo;
import df.base.service.ResourceNotFoundException;
import df.base.service.form.FormService;
import df.web.common.ControllerHelper;
import df.web.common.flash.FlashMessage;
import df.web.controller.MAVConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
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
public class FormController implements FormOperations {

    private final FormService      service;
    private final ControllerHelper helper;

    public FormController(FormService service, ControllerHelper helper) {
        this.service = service;
        this.helper = helper;

        service.setRedirectUrl(MAVConstants.REDIRECT_FORM);
        helper.setRedirectUrl(MAVConstants.REDIRECT_FORM);
    }

    @Override
    public ModelAndView index() {
        helper.setViewName(MAVConstants.VIEW_FORM_INDEX);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        bindAttributes(new FormDTO() {{
            setOwnerId(((UserInfo) authentication.getPrincipal()).getUser().getId());
        }});

        return helper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView create() {
        return null;
    }

    @Override
    public ModelAndView modify(@PathVariable("itemId") String itemId, RedirectAttributes attributes) {
        helper.setViewName(MAVConstants.VIEW_FORM_INDEX);
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FormMapper().map(service.requireById(itemId)));
            mav = helper.resolveWithoutRedirect();
        } catch (ResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(@Validated FormDTO itemDTO, BindingResult result, RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName(MAVConstants.VIEW_FORM_INDEX);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        bindAttributes(itemDTO);

        if (!result.hasErrors()) {
            Form form = service.createOrUpdate(itemDTO, ((UserInfo) authentication.getPrincipal()).getUser());
            helper.addMessage(success(SUCCESS_FORM_SAVED.formatted(form.getDescription())));
        }

        return helper.resolveWithRedirect();
    }

    @Override
    public ModelAndView remove(@PathVariable("itemId") String itemId, RedirectAttributes attributes) {
        helper.setRedirectAttributes(attributes);

        Optional<Form> result = service.getById(itemId);

        if (result.isPresent()) {
            service.delete(result.get());
            helper.addMessage(error(SUCCESS_FORM_DELETED
                    .formatted(itemId)));
        } else {
            helper.addMessage(warning(ERROR_FORM_NOT_FOUND
                    .formatted(itemId)));
        }

        return helper.redirect();
    }

    @Override
    public ModelAndView status(@PathVariable("itemId") String itemId,
                               @PathVariable("status") String status,
                               RedirectAttributes attributes) {
        Optional<Form> result = service.getById(itemId);
        helper.setRedirectAttributes(attributes);

        if (result.isPresent()) {
            FormStatus formStatus = FormStatus.valueOf(status.toUpperCase());
            EnumMap<FormStatus, Function<String, FlashMessage>> messages = new EnumMap<>(FormStatus.class) {{
                put(FormStatus.ACTIVE, FlashMessage::success);
                put(FormStatus.INACTIVE, FlashMessage::dark);
                put(FormStatus.DELETED, FlashMessage::error);
            }};
            service.changeStatus(result.get(), formStatus);
            helper.addMessage(messages.get(formStatus).apply(
                    SUCCESS_FORM_STATUS_CHANGED
                            .formatted(itemId, formStatus)));
        } else {
            helper.addMessage(error(ERROR_FORM_NOT_FOUND
                    .formatted(itemId)));
        }

        return helper.redirect();
    }

    private void bindAttributes(FormDTO itemDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("formDTO", itemDTO);
        attributes.put("forms", service.getAll());
        attributes.put("statuses", new HashMap<>() {{
            put(FormStatus.ACTIVE, "bg-success");
            put(FormStatus.INACTIVE, "bg-dark");
            put(FormStatus.DELETED, "bg-danger");
        }});

        helper.attributes(attributes);
    }

}
