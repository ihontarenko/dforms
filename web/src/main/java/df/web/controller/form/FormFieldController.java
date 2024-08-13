package df.web.controller.form;

import df.base.model.form.FormDTO;
import df.web.common.flash.FlashMessageService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/form/field")
public class FormFieldController {

    private final FlashMessageService flashMessage;

    public FormFieldController(FlashMessageService flashMessage) {
        this.flashMessage = flashMessage;
    }

    @GetMapping("/add")
    public ModelAndView form() {
        return new ModelAndView("forms/add_form");
    }

    @PostMapping("/perform")
    public String perform(@ModelAttribute @Valid FormDTO formDTO, BindingResult result) {
        System.out.println(formDTO);
        System.out.println(result);

        return "redirect:/forms/add";
    }

}
