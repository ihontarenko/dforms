package df.web.controller.form;

import df.base.common.elements.NodeContext;
import df.base.common.elements.TagName;
import df.base.common.elements.builder.NodeBuilderContext;
import df.base.common.elements.node.HTMLElementNode;
import df.base.common.elements.node.InputElementNode;
import df.base.common.exception.ApplicationException;
import df.base.common.pipeline.PipelineContext;
import df.base.common.pipeline.PipelineManager;
import df.base.common.pipeline.SpringBeanProvider;
import df.base.common.pipeline.context.PipelineArguments;
import df.base.common.pipeline.context.PipelineResult;
import df.base.dto.form.FormDTO;
import df.base.html.builder.bootstrap.BootstrapBuilderStrategy;
import df.base.mapping.form.DeepFormMapper;
import df.base.mapping.form.FormMapper;
import df.base.persistence.entity.form.Form;
import df.base.persistence.entity.support.FormStatus;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.security.UserInfo;
import df.base.service.form.FormService;
import df.web.common.ControllerHelper;
import df.web.common.flash.FlashMessage;
import df.web.controller.MAVConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
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

    private final FormService        formService;
    private final ControllerHelper   controllerHelper;
    private final NodeBuilderContext builderContext;
    private final PipelineManager    pipelineManager;

    public FormController(FormService formService, ControllerHelper controllerHelper, PipelineManager pipelineManager) {
        this.formService = formService;
        this.controllerHelper = controllerHelper;
        this.pipelineManager = pipelineManager;

        this.builderContext = new NodeBuilderContext();
        this.builderContext.setStrategy(new BootstrapBuilderStrategy());

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
    public ModelAndView demo(String primaryId, MultiValueMap<String, String> postData,
                             HttpServletRequest request,  RedirectAttributes attributes) {
        controllerHelper.setRedirectAttributes(attributes);
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_INDEX);

        System.out.println(request.getParameterMap());

        return controllerHelper.redirect();
    }

    @Override
    public ModelAndView demo(String primaryId, RedirectAttributes attributes) {
        controllerHelper.setViewName(MAVConstants.VIEW_FORM_DEMO);
        controllerHelper.setRedirectAttributes(attributes);

        Form              entity          = formService.loadFormWithFields(primaryId);
        PipelineContext   pipelineContext = pipelineManager.getContext();
        PipelineArguments arguments       = pipelineContext.getPipelineArguments();
        PipelineResult    result          = pipelineContext.getPipelineResult();

        pipelineContext.setBeanProvider(new SpringBeanProvider());

        arguments.setArgument("PRIMARY_ID", primaryId);
        arguments.setArgument("ENV_NAME", "DEMO");

        try {
            pipelineManager.runPipeline("process-form-html");
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), MAVConstants.REDIRECT_FORM);
        }

        if (result.hasErrors()) {
            throw new ApplicationException(result.getError("EXCEPTION").message(), MAVConstants.REDIRECT_FORM);
        }

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
