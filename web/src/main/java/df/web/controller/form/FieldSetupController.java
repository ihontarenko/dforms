package df.web.controller.form;

import df.base.dto.form.FieldDTO;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.service.form.FieldService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FieldSetupController implements FieldSetupOperations {

    private final ControllerHelper      helper;
    private final FieldService          fieldService;

    public FieldSetupController(ControllerHelper helper, FieldService fieldService) {
        this.helper = helper;
        this.fieldService = fieldService;
        helper.setRedirectUrl(MAVConstants.REDIRECT_FORM_FIELD_SETUP);
    }

    @Override
    public ModelAndView index(String fieldId) {
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_CONFIG);

        bindAttributes(new FieldDTO());

        return helper.resolveWithoutRedirect();
    }

    @Override
    public ModelAndView modify(String configId, RedirectAttributes attributes) {
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_CONFIG);
        helper.setRedirectAttributes(attributes);

        ModelAndView mav;

        try {
            bindAttributes(new FieldDTO());
            mav = helper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @GetMapping("/perform")
    public ModelAndView perform(Map<String, Object> dto, BindingResult result,
                                RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName(MAVConstants.VIEW_FORM_FIELD_CONFIG);
//        helper.setRedirectUrl(MAVConstants.REDIRECT_FORM_CONFIG.formatted(configDTO.getFieldId()));

        if (!result.hasErrors()) {
//            FieldConfig config = configService
//                    .createOrUpdate(fieldService.requireById(configDTO.getFieldId()), configDTO);
//            helper.addMessage(success(SUCCESS_CONFIG_SAVED
//                    .formatted(config.getConfigName())));
        } else {
            bindAttributes(new FieldDTO());
        }

        return helper.resolveWithRedirect();
    }

    private void bindAttributes(FieldDTO itemDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("itemDTO", itemDTO);
        attributes.put("configs", null);
        attributes.put("attributes", null);
        attributes.put("options", null);
//        attributes.put("field", field);

        helper.attributes(attributes);
    }

}
