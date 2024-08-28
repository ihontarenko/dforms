package df.web.controller.form;

import df.base.jpa.form.Form;
import df.base.jpa.form.FormConfig;
import df.base.mapper.form.FormConfigMapper;
import df.base.model.form.FormConfigDTO;
import df.base.service.ResourceNotFoundException;
import df.base.service.form.FormConfigService;
import df.base.service.form.FormService;
import df.web.common.ControllerHelper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import static df.base.Messages.SUCCESS_FORM_CONFIG_SAVED;
import static df.web.common.flash.FlashMessage.success;

@Controller
@RequestMapping("/form/config")
public class FormConfigController {

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

    @GetMapping("/{formId}")
    public ModelAndView index(@PathVariable("formId") String formId) {
        helper.setViewName("form/config");

        bindAttributes(new FormConfigDTO(){{
            setFormId(formId);
        }});

        return helper.resolveWithoutRedirect();
    }

    @GetMapping("/{configId}/modify")
    public ModelAndView modify(@PathVariable("configId") String configId, RedirectAttributes attributes) {
        helper.setViewName("form/config");
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FormConfigMapper().map(configService.requireById(configId)));
            mav = helper.resolveWithoutRedirect();
        } catch (ResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @PostMapping("/{formId}/perform")
    public ModelAndView perform(@ModelAttribute("configDTO") @Valid FormConfigDTO configDTO, BindingResult result,
                                RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName("form/config");
        helper.setRedirectUrl("/form/config/%s".formatted(configDTO.getFormId()));

        bindAttributes(configDTO);

        if (!result.hasErrors()) {
            FormConfig config = configService
                    .createOrUpdate(formService.requireById(configDTO.getFormId()), configDTO);
            helper.addMessage(success(SUCCESS_FORM_CONFIG_SAVED
                    .formatted(config.getConfigName())));
        }

        return helper.resolveWithRedirect();
    }

    private void bindAttributes(FormConfigDTO configDTO) {
        Map<String, Object> attributes = new HashMap<>();
        Form                form       = formService.requireById(configDTO.getFormId());

        attributes.put("configDTO", configDTO);
        attributes.put("configurations", configService.getAllByForm(form));
        attributes.put("form", form);

        helper.attributes(attributes);
    }

}
