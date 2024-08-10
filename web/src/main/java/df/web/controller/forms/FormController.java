package df.web.controller.forms;

import df.base.model.FormDTO;
import df.web.common.flash.FlashMessageService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/form")
public class FormController {

    private final FlashMessageService flashMessage;

    public FormController(FlashMessageService flashMessage) {
        this.flashMessage = flashMessage;
    }

    @GetMapping("/add")
    public ModelAndView form() {
        return new ModelAndView("forms/add_form");
    }

    @PostMapping("/perform")
    public String perform(@Valid FormDTO formDTO, BindingResult result) {
        System.out.println(formDTO);
        System.out.println(result);

        return "redirect:/form/add";
    }

}
