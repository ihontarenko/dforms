package df.web.controller.form;

import df.base.dto.form.FieldDTO;
import df.base.mapping.form.FieldMapper;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.service.form.FieldService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FieldSetupController implements FieldSetupOperations {

    private final ControllerHelper helper;
    private final FieldService     service;
    private final FieldMapper      mapper;

    public FieldSetupController(ControllerHelper helper, FieldService service, FieldMapper mapper) {
        this.helper = helper;
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ModelAndView index(String fieldId) {
        FieldDTO     fieldDTO = mapper.map(service.requireById(fieldId));
        ModelAndView mav;

        helper.setViewName(MAVConstants.VIEW_FIELD_CONFIG);

        try {
            bindAttributes(fieldDTO);
            mav = helper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

//    @PostMapping("/perform")
    // todo: do something with dtos
    // todo: validation groups
    public ModelAndView perform(@ModelAttribute("itemDTO") @Validated FieldDTO dto,
                                BindingResult result, RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName(MAVConstants.VIEW_FIELD_CONFIG);

        if (!result.hasErrors()) {

        } else {
            bindAttributes(dto);
        }

        return helper.resolveWithoutRedirect();
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
