package df.web.controller.form;

import df.base.jpa.form.*;
import df.base.model.form.FormFieldDTO;
import df.base.service.form.FormFieldService;
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
import java.util.List;
import java.util.Map;

import static df.base.Messages.SUCCESS_FIELD_SAVED;
import static df.web.common.flash.FlashMessage.success;

@Controller
@RequestMapping("/form/field")
public class FormFieldController {

    private final ControllerHelper    helper;
    private final FormFieldService    service;
    private final FormEntryRepository repository;

    public FormFieldController(ControllerHelper helper, FormFieldService service, FormEntryRepository repository) {
        this.service = service;
        this.helper = helper;
        this.repository = repository;
        helper.setRedirectUrl("/form/field");
    }

    @GetMapping
    public ModelAndView form() {
        helper.setViewName("form/field");

        bindAttributes(new FormFieldDTO());

        List<FormEntry> formEntries = repository.findAllByForm(new Form(){{
            setId("test");
        }});

        return helper.resolveWithoutRedirect();
    }

    @PostMapping("/perform")
    public ModelAndView perform(@ModelAttribute @Valid FormFieldDTO fieldDTO, BindingResult result, RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName("form/field");

        bindAttributes(fieldDTO);

        if (!result.hasFieldErrors()) {
            FormField field = service.createOrUpdate(fieldDTO);
            helper.addMessage(success(SUCCESS_FIELD_SAVED.formatted(field.getLabel())));
        }

        return helper.resolveWithRedirect();
    }

    private void bindAttributes(FormFieldDTO fieldDTO) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("fieldDTO", fieldDTO);
        attributes.put("elementTypes", ElementType.values());
        attributes.put("fields", service.getAll());

        helper.attributes(attributes);
    }

}
