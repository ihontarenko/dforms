package df.web.controller.form;

import df.base.dto.form.FieldAttributeDTO;
import df.base.dto.form.FieldConfigDTO;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldOptionDTO;
import df.base.mapping.form.FieldMapper;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.service.form.FieldService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FieldCustomizationController implements FieldCustomizationOperations {

    private final ControllerHelper helper;
    private final FieldService     service;
    private final FieldMapper      mapper;

    public FieldCustomizationController(ControllerHelper helper, FieldService service, FieldMapper mapper) {
        this.helper = helper;
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ModelAndView index(String section, String ownerId) {
        helper.setViewName(MAVConstants.VIEW_FIELD_CONFIG);

        FieldDTO     fieldDTO = mapper.map(service.requireById(ownerId));
        ModelAndView mav;

        try {
            bindAttributes(fieldDTO);
            mav = helper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView modify(String itemId, RedirectAttributes attributes) {
        return null;
    }

    @Override
    public ModelAndView perform(FieldConfigDTO itemDTO, BindingResult result, RedirectAttributes attributes) {
        return null;
    }

    @Override
    public ModelAndView perform(FieldAttributeDTO itemDTO, BindingResult result, RedirectAttributes attributes) {
        return null;
    }

    @Override
    public ModelAndView perform(FieldOptionDTO itemDTO, BindingResult result, RedirectAttributes attributes) {
        return null;
    }

    private void bindAttributes(FieldDTO itemDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("itemDTO", itemDTO);

        helper.attributes(attributes);
    }

}
