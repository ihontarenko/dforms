package df.web.controller.form;

import df.base.jpa.form.ElementType;
import df.base.model.form.FormFieldDTO;
import df.web.common.ControllerHelper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/form/field")
public class FormFieldController {

    private final ControllerHelper helper;

    public FormFieldController(ControllerHelper helper) {
        this.helper = helper;
        helper.setRedirectUrl("/form/field");
    }

    @GetMapping
    public ModelAndView form() {
        helper.setViewName("form/field");

        bindAttributes(new FormFieldDTO());

        return helper.resolveWithoutRedirect();
    }

    @PostMapping("/perform")
    public ModelAndView perform(@ModelAttribute @Valid FormFieldDTO fieldDTO, BindingResult result, RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);

        helper.setViewName("form/field");

        bindAttributes(fieldDTO);

        return helper.resolveWithRedirect();
    }

    private void bindAttributes(FormFieldDTO fieldDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("fieldDTO", fieldDTO);
        attributes.put("elementTypes", ElementType.values());

        helper.attributes(attributes);
    }

}
