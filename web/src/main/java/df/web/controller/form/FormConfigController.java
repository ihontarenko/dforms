package df.web.controller.form;

import df.application.persistence.entity.form.Form;
import df.application.persistence.entity.form.FormConfig;
import df.application.old_mapping.form.FormConfigMapper;
import df.application.dto.form.FormConfigDTO;
import df.application.persistence.exception.JpaResourceNotFoundException;
import df.application.service.form.FormConfigService;
import df.application.service.form.FormService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static df.application.Messages.*;
import static df.web.common.flash.FlashMessage.*;

@Controller
public class FormConfigController implements FormConfigOperations {

    private final FormConfigService configService;
    private final FormService       formService;
    private final ControllerHelper  helper;

    public FormConfigController(FormConfigService configService, FormService formService, ControllerHelper helper) {
        this.helper = helper;
        this.configService = configService;
        this.formService = formService;

        configService.setRedirectUrl("/form");
        helper.setRedirectUrl("/form");
    }

    @Override
    public ModelAndView index(String primaryId) {
        helper.setViewName(MAVConstants.VIEW_FORM_CONFIG);

        bindAttributes(new FormConfigDTO(){{
            setFormId(primaryId);
        }});

        return helper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(String primaryId, String configId, RedirectAttributes attributes) {
        helper.setViewName(MAVConstants.VIEW_FORM_CONFIG);
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FormConfigMapper().map(configService.requireById(configId)));
            mav = helper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(FormConfigDTO configDTO, BindingResult result,
                                RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName(MAVConstants.VIEW_FORM_CONFIG);
        helper.setRedirectUrl(MAVConstants.REDIRECT_FORM_CONFIG.formatted(configDTO.getFormId()));

        bindAttributes(configDTO);

        if (!result.hasErrors()) {
            FormConfig config = configService
                    .createOrUpdate(formService.requireById(configDTO.getFormId()), configDTO);
            helper.addMessage(success(SUCCESS_CONFIG_SAVED
                    .formatted(config.getConfigName())));
        }

        return helper.resolveWithRedirect();
    }

    @Override
    public ModelAndView remove(String itemId, RedirectAttributes attributes) {
        Optional<FormConfig> config = configService.getById(itemId);
        String               formId = config.map(c -> c.getForm().getId()).orElse(null);

        helper.setRedirectUrl(MAVConstants.REDIRECT_FORM_CONFIG.formatted(formId));
        helper.setRedirectAttributes(attributes);
        helper.addMessage(error(SUCCESS_CONFIG_DELETED.formatted(itemId)));

        configService.deleteIfExists(config.orElse(null));

        return helper.redirect();
    }

    private void bindAttributes(FormConfigDTO itemDTO) {
        Map<String, Object> attributes = new HashMap<>();
        Form                form       = formService.requireById(itemDTO.getFormId());

        attributes.put("itemDTO", itemDTO);
        attributes.put("configurations", configService.getAllByForm(form));
        attributes.put("form", form);

        helper.attributes(attributes);
    }

}
