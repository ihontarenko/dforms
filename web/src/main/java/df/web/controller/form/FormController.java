package df.web.controller.form;

import df.base.common.jbm.bean.context.JbmContext;
import df.base.jpa.form.Form;
import df.base.jpa.form.FormStatus;
import df.base.mapper.form.FormMapper;
import df.base.model.form.FormDTO;
import df.base.security.UserInfo;
import df.base.service.ResourceNotFoundException;
import df.base.service.form.FormService;
import df.web.common.ControllerHelper;
import df.web.common.flash.FlashMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/form")
public class FormController {

    private final FormService      service;
    private final ControllerHelper helper;
    private final JbmContext       jbmContext;

    public FormController(FormService service, ControllerHelper helper, JbmContext jbmContext) {
        this.service = service;
        this.helper = helper;
        this.jbmContext = jbmContext;
        service.setRedirectUrl("/form?error=exception");
        helper.setRedirectUrl("/form");
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal UserInfo principal) {
        helper.setViewName("form/index");

        bindAttributes(new FormDTO() {{
            setOwnerId(principal.getUser().getId());
        }});

        return helper.resolveWithoutRedirect();
    }

    @GetMapping("/{formId}/modify")
    public ModelAndView modify(@PathVariable("formId") String formId, RedirectAttributes attributes) {
        helper.setViewName("form/index");
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FormMapper().map(service.requireById(formId)));
            mav = helper.resolveWithoutRedirect();
        } catch (ResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @PostMapping("/perform")
    public ModelAndView perform(@Validated FormDTO formDTO, BindingResult result, RedirectAttributes attributes,
                                @AuthenticationPrincipal UserInfo principal) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName("form/index");

        bindAttributes(formDTO);

        if (!result.hasErrors()) {
            Form form = service.createOrUpdate(formDTO, principal.getUser());
            helper.addMessage(success(SUCCESS_FORM_SAVED.formatted(form.getDescription())));
        }

        return helper.resolveWithRedirect();
    }

    @PreAuthorize("hasRole('SUPER_USER')")
    @GetMapping("/{formId}/delete")
    public ModelAndView remove(@PathVariable("formId") String formId, RedirectAttributes attributes) {
        helper.setRedirectAttributes(attributes);

        Optional<Form> result = service.getById(formId);

        if (result.isPresent()) {
            service.delete(result.get());
            helper.addMessage(error(SUCCESS_FORM_DELETED
                    .formatted(formId)));
        } else {
            helper.addMessage(warning(ERROR_FORM_NOT_FOUND
                    .formatted(formId)));
        }

        return helper.redirect();
    }

    @GetMapping("/{formId}/status/{status}")
    public ModelAndView status(@PathVariable("formId") String formId,
                               @PathVariable("status") String status,
                               RedirectAttributes attributes) {
        Optional<Form> result = service.getById(formId);
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
                            .formatted(formId, formStatus)));
        } else {
            helper.addMessage(error(ERROR_FORM_NOT_FOUND
                    .formatted(formId)));
        }

        return helper.redirect();
    }

    private void bindAttributes(FormDTO formDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("formDTO", formDTO);
        attributes.put("forms", service.getAll());
        attributes.put("statuses", new HashMap<>() {{
            put(FormStatus.ACTIVE, "bg-success");
            put(FormStatus.INACTIVE, "bg-dark");
            put(FormStatus.DELETED, "bg-danger");
        }});

        helper.attributes(attributes);
    }

}
