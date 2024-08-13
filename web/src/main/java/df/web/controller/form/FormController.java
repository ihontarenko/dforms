package df.web.controller.form;

import df.base.Messages;
import df.base.converter.FormConverter;
import df.base.jpa.form.Form;
import df.base.jpa.form.FormStatus;
import df.base.model.form.FormDTO;
import df.base.security.UserInfo;
import df.base.service.forms.FormService;
import df.web.common.flash.FlashMessageService;
import df.web.common.flash.FlashMessageType;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static df.web.common.flash.FlashMessageType.*;

@Controller
@RequestMapping("/form")
public class FormController {

    private final FlashMessageService     flashMessage;
    private final FormService             formService;
    private final Map<FormStatus, String> statuses = new HashMap<>() {{
        put(FormStatus.ACTIVE, "bg-success");
        put(FormStatus.INACTIVE, "bg-dark");
        put(FormStatus.DELETED, "bg-danger");
    }};

    public FormController(FlashMessageService flashMessage, FormService formService) {
        this.flashMessage = flashMessage;
        this.formService = formService;
    }

    @GetMapping(value = {"/index", "/{formId}/edit"})
    public ModelAndView index(@AuthenticationPrincipal UserInfo principal,
                              @PathVariable(value = "formId", required = false) String formId,
                              RedirectAttributes attributes) {
        ModelAndView mav     = new ModelAndView("form/index");
        FormDTO      formDTO = new FormDTO();

        formDTO.setOwnerId(principal.getUser().getId());

        if (formId != null) {
            Optional<Form> result = formService.getFormById(formId);
            if (result.isPresent()) {
                formDTO = new FormConverter().convert(result.get());
            }
        }

        mav.addObject("formDTO", formDTO);
        mav.addObject("forms", formService.getAllForms());
        mav.addObject("statuses", statuses);

        return mav;
    }

    @GetMapping("/{formId}/modify")
    public ModelAndView modify() {
        ModelAndView mav     = new ModelAndView("form/index");

        return mav;
    }

    @GetMapping("/{formId}/remove")
    public String remove(@PathVariable("formId") String formId, RedirectAttributes attributes) {
        Optional<Form> result = formService.getFormById(formId);

        if (result.isPresent()) {
            formService.deleteForm(result.get());
            flashMessage.addMessage(attributes, "Form ID '%s' has been successfully removed"
                    .formatted(formId), NOTICE);
        } else {
            flashMessage.addMessage(attributes, "Form ID '%s' not found"
                    .formatted(formId), FlashMessageType.WARNING);
        }

        return "redirect:/form/index";
    }

    @PostMapping("/perform")
    public ModelAndView perform(@Valid FormDTO formDTO, BindingResult result, RedirectAttributes attributes,
                                @AuthenticationPrincipal UserInfo principal) {
        ModelAndView mav = new ModelAndView("redirect:/form/index");

        if (!result.hasFieldErrors()) {
            if (formDTO.getId() != null) {
                Form form = formService.getFormById(formDTO.getId())
                        .map(f -> formService.updateForm(f, formDTO))
                        .orElseGet(() -> formService.createForm(principal.getUser(), formDTO));
                flashMessage.addMessage(attributes, "Form ID '%s' has been successfully saved"
                        .formatted(form.getId()), SUCCESS);
            }
        } else {
            mav.addObject("formDTO", formDTO);
            mav.addObject("forms", formService.getAllForms());
            mav.addObject("statuses", statuses);
            mav.setViewName("form/index");
        }

        return mav;
    }

}
