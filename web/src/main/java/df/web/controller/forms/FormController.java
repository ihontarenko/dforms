package df.web.controller.forms;

import df.base.model.FormDTO;
import df.base.service.forms.FormService;
import df.web.common.flash.FlashMessageService;
import df.web.common.flash.FlashMessageType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    private final FormService formService;

    public FormController(FlashMessageService flashMessage, FormService formService) {
        this.flashMessage = flashMessage;
        this.formService = formService;
    }

    @GetMapping("/add")
    public ModelAndView form() {
        ModelAndView mav = new ModelAndView("forms/add_form");

        mav.addObject("formDTO", new FormDTO());

        return mav;
    }

    @PostMapping("/perform")
    public ModelAndView perform(@Valid FormDTO formDTO, BindingResult result, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("redirect:/form/add");

        if (!result.hasFieldErrors()) {
//            formService.createForm()
            flashMessage.addFlashMessage(attributes, result, FlashMessageType.ERROR);
            mav.setStatus(HttpStatus.CREATED);
        } else {
            mav.addObject("formDTO", formDTO);
            mav.setViewName("forms/add_form");
        }

        return mav;
    }

}
