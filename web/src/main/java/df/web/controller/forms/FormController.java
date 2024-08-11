package df.web.controller.forms;

import df.base.model.FormDTO;
import df.web.common.flash.FlashMessageService;
import df.web.common.flash.FlashMessageType;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String perform(@Valid FormDTO formDTO, BindingResult result, RedirectAttributes attributes) {
        System.out.println(formDTO);
        System.out.println(result);

        if (result.hasFieldErrors()) {
            flashMessage.addFlashMessage(attributes, result, FlashMessageType.ERROR);
        }

        return "redirect:/form/add";
    }

}
