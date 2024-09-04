package df.web.controller.form;

import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.form.FieldConfig;
import df.base.mapping.form.FormFieldConfigMapper;
import df.base.dto.form.FieldConfigDTO;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.service.form.FieldConfigService;
import df.base.service.form.FieldService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static df.base.Messages.SUCCESS_CONFIG_DELETED;
import static df.base.Messages.SUCCESS_CONFIG_SAVED;
import static df.web.common.flash.FlashMessage.error;
import static df.web.common.flash.FlashMessage.success;

@Controller
public class FieldConfigController implements FieldConfigOperations {

    private final ControllerHelper   helper;
    private final FieldConfigService configService;
    private final FieldService       fieldService;

    public FieldConfigController(ControllerHelper helper, FieldConfigService configService,
                                 FieldService fieldService) {
        this.helper = helper;
        this.configService = configService;
        this.fieldService = fieldService;
        helper.setRedirectUrl(MAVConstants.REDIRECT_FORM_FIELD_CONFIG);
    }

    @Override
    public ModelAndView index(String fieldId) {
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_CONFIG);

        bindAttributes(new FieldConfigDTO(){{
            setFormFieldId(fieldId);
        }});

        return helper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(String configId, RedirectAttributes attributes) {
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_CONFIG);
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FormFieldConfigMapper().map(configService.requireById(configId)));
            mav = helper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(FieldConfigDTO configDTO, BindingResult result,
                                RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_CONFIG);
        helper.setRedirectUrl(MAVConstants.REDIRECT_FORM_CONFIG.formatted(configDTO.getFormFieldId()));

        if (!result.hasErrors()) {
            FieldConfig config = configService
                    .createOrUpdate(fieldService.requireById(configDTO.getFormFieldId()), configDTO);
            helper.addMessage(success(SUCCESS_CONFIG_SAVED
                    .formatted(config.getConfigName())));
        } else {
            bindAttributes(configDTO);
        }

        return helper.resolveWithRedirect();
    }

    @Override
    public ModelAndView remove(String itemId, RedirectAttributes attributes) {
        Optional<FieldConfig> config  = configService.getById(itemId);
        String                fieldId = config.map(c -> c.getField().getId()).orElse(null);

        helper.setRedirectUrl(MAVConstants.REDIRECT_FORM_CONFIG.formatted(fieldId));
        helper.setRedirectAttributes(attributes);
        helper.addMessage(error(SUCCESS_CONFIG_DELETED.formatted(itemId)));

        configService.deleteIfExists(config.orElse(null));

        return helper.redirect();
    }

    private void bindAttributes(FieldConfigDTO itemDTO) {
        Map<String, Object> attributes = new HashMap<>();
        Field               field      = fieldService.requireById(itemDTO.getFormFieldId());

        attributes.put("itemDTO", itemDTO);
        attributes.put("configurations", configService.getAllByField(field));
        attributes.put("field", field);

        helper.attributes(attributes);
    }

}
