package df.web.controller.form;

import org.jmouse.common.support.context.ArgumentsContext;
import org.jmouse.common.support.context.ResultContext;
import df.application.exception.ApplicationException;
import df.common.pipeline.PipelineContextFactoty;
import df.common.pipeline.context.DefaultPipelineContext;
import df.common.pipeline.context.PipelineContext;
import df.application.dto.form.FormDTO;
import df.application.mapping.form.FormMapper;
import df.application.persistence.entity.form.Form;
import df.application.persistence.entity.support.FormStatus;
import df.application.persistence.exception.JpaResourceNotFoundException;
import df.application.security.UserInfo;
import df.application.service.form.FormManagement;
import df.application.service.form.FormService;
import df.web.common.ControllerHelper;
import df.web.common.flash.FlashMessage;
import df.web.controller.MAVConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.function.Function;

import static df.application.Messages.*;
import static df.web.common.flash.FlashMessage.*;

@Controller
public class FormController implements FormOperations {

    private final FormService        formService;
    private final ControllerHelper   controllerHelper;

    private final FormManagement managementService;

    public FormController(FormService formService, ControllerHelper controllerHelper, FormManagement management) {
        this.formService = formService;
        this.controllerHelper = controllerHelper;
        this.managementService = management;

        formService.setRedirectUrl(MAVConstants.REDIRECT_FORM);
        controllerHelper.setRedirectUrl(MAVConstants.REDIRECT_FORM);
    }

    @Override
    public ModelAndView index() {
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_INDEX);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        bindAttributes(new FormDTO() {{
            setOwnerId(((UserInfo) authentication.getPrincipal()).getUser().getId());
        }});

        return controllerHelper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView demo(String primaryId, MultiValueMap<String, String> postData, RedirectAttributes attributes) {
        PipelineContext performContext = PipelineContextFactoty.createByDefault();

        managementService.performDynamicForm((DefaultPipelineContext) performContext, primaryId, postData);

        BindingResult bindingResult = performContext.getProperty(BindingResult.class);

        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_DEMO);
        controllerHelper.setBindingResult(bindingResult);

        if (bindingResult.hasErrors()) {
            PipelineContext  renderContext = PipelineContextFactoty.createByDefault();
            ArgumentsContext arguments     = renderContext.getArgumentsContext();
            Form             entity        = formService.loadFormWithFields(primaryId);

            //  prepare context and pass necessary data to arguments
            arguments.setArgument(Form.class, entity);
            arguments.copyArgument("REQUEST_DATA", performContext.getArgumentsContext());
            arguments.setArgument(BindingResult.class, bindingResult);

            // render dynamic form
            managementService.renderDynamicForm((DefaultPipelineContext) renderContext, primaryId);

            controllerHelper.attribute("form", entity);
            controllerHelper.attribute("errors", bindingResult);
            controllerHelper.attribute("html", renderContext.getResultContext().getReturnValue());
        }

        return controllerHelper.resolveWithRedirect();
    }

    @Override
    public ModelAndView demo(String primaryId, RedirectAttributes attributes) {
        Form             entity    = formService.loadFormWithFields(primaryId);
        PipelineContext  context   = PipelineContextFactoty.createByDefault();
        ResultContext    result    = context.getResultContext();
        ArgumentsContext arguments = context.getArgumentsContext();

        arguments.setArgument(Form.class, entity);
        arguments.setArgument("REQUEST_DATA", Collections.emptyMap());
        arguments.setArgument("ERROR_DATA", Collections.emptyMap());

        // proceed html nodes and render
        managementService.renderDynamicForm((DefaultPipelineContext) context, primaryId);

        if (result.hasErrors()) {
            throw new ApplicationException(result.getError("EXCEPTION").message(), MAVConstants.REDIRECT_FORM);
        }

        controllerHelper.setViewName(MAVConstants.VIEW_FORM_DEMO);
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.attribute("form", entity);
        controllerHelper.attribute("html", result.getReturnValue());

        return controllerHelper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(String itemId, RedirectAttributes attributes) {
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_INDEX);
        controllerHelper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FormMapper().map(formService.requireById(itemId)));
            mav = controllerHelper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = controllerHelper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(@Validated FormDTO itemDTO, BindingResult result, RedirectAttributes attributes) {
        controllerHelper.setBindingResult(result);
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_INDEX);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        bindAttributes(itemDTO);

        if (!result.hasErrors()) {
            Form form = formService.createOrUpdate(itemDTO, ((UserInfo) authentication.getPrincipal()).getUser());
            controllerHelper.addMessage(success(SUCCESS_FORM_SAVED.formatted(form.getDescription())));
        }

        return controllerHelper.resolveWithRedirect();
    }

    @Override
    public ModelAndView remove(String itemId, RedirectAttributes attributes) {
        controllerHelper.setRedirectAttributes(attributes);

        Optional<Form> result = formService.getById(itemId);

        if (result.isPresent()) {
            formService.delete(result.get());
            controllerHelper.addMessage(error(SUCCESS_FORM_DELETED.formatted(itemId)));
        } else {
            controllerHelper.addMessage(warning(ERROR_FORM_NOT_FOUND.formatted(itemId)));
        }

        return controllerHelper.redirect();
    }

    @Override
    public ModelAndView status(@PathVariable("itemId") String itemId, @PathVariable("status") String status, RedirectAttributes attributes) {
        Optional<Form> result = formService.getById(itemId);
        controllerHelper.setRedirectAttributes(attributes);

        if (result.isPresent()) {
            FormStatus formStatus = FormStatus.valueOf(status.toUpperCase());
            EnumMap<FormStatus, Function<String, FlashMessage>> messages = new EnumMap<>(FormStatus.class) {{
                put(FormStatus.ACTIVE, FlashMessage::success);
                put(FormStatus.INACTIVE, FlashMessage::dark);
                put(FormStatus.DELETED, FlashMessage::error);
            }};
            formService.changeStatus(result.get(), formStatus);
            controllerHelper.addMessage(
                    messages.get(formStatus).apply(SUCCESS_FORM_STATUS_CHANGED.formatted(itemId, formStatus)));
        } else {
            controllerHelper.addMessage(error(ERROR_FORM_NOT_FOUND.formatted(itemId)));
        }

        return controllerHelper.redirect();
    }

    private void bindAttributes(FormDTO itemDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("itemDTO", itemDTO);
        attributes.put("forms", formService.getAll());
        attributes.put("statuses", new HashMap<>() {{
            put(FormStatus.ACTIVE, "bg-success");
            put(FormStatus.INACTIVE, "bg-dark");
            put(FormStatus.DELETED, "bg-danger");
        }});

        controllerHelper.attributes(attributes);
    }

}
