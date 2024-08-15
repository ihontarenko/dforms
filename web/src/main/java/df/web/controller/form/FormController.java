package df.web.controller.form;

import df.base.jpa.User;
import df.base.jpa.form.Form;
import df.base.jpa.form.FormStatus;
import df.base.mapper.form.FormMapper;
import df.base.model.form.FormDTO;
import df.base.security.UserInfo;
import df.base.service.form.FormService;
import df.web.common.flash.FlashMessage.Type;
import df.web.common.flash.FlashMessageService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static df.web.common.flash.FlashMessage.Type.*;

@Controller
@RequestMapping("/form")
public class FormController {

    private final FlashMessageService     flash;
    private final FormService             service;
    private final Map<FormStatus, String> statuses = new HashMap<>() {{
        put(FormStatus.ACTIVE, "bg-success");
        put(FormStatus.INACTIVE, "bg-dark");
        put(FormStatus.DELETED, "bg-danger");
    }};

    public FormController(FlashMessageService flash, FormService service) {
        this.flash = flash;
        this.service = service;
        service.setRedirectUrl("/form?error=exception");
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal UserInfo principal) {
        ModelAndView mav     = new ModelAndView("form/index");
        FormDTO      formDTO = new FormDTO();

        formDTO.setOwnerId(principal.getUser().getId());

        bindAttributes(mav, formDTO);

        return mav;
    }

    @GetMapping("/{formId}/modify")
    public ModelAndView index(@PathVariable(value = "formId", required = false) String formId) {
        ModelAndView mav = new ModelAndView("form/index");

        bindAttributes(mav, new FormMapper().map(service.requireById(formId)));

        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{formId}/delete")
    public String remove(@PathVariable("formId") String formId, RedirectAttributes attributes) {
        Optional<Form> result = service.getById(formId);

        if (result.isPresent()) {
            service.delete(result.get());
            flash.addMessage(attributes, "Form ID '%s' has been permanently deleted"
                    .formatted(formId), DARK);
        } else {
            flash.addMessage(attributes, "Form ID '%s' not found"
                    .formatted(formId), Type.ERROR);
        }

        return "redirect:/form/index";
    }

    @GetMapping("/{formId}/status/{status}")
    public String status(@PathVariable("formId") String formId, @PathVariable("status") String status,
                         RedirectAttributes attributes) {
        Optional<Form> result = service.getById(formId);

        if (result.isPresent()) {
            service.changeStatus(result.get(), FormStatus.valueOf(status.toUpperCase(Locale.ROOT)));
            flash.addMessage(attributes, "Form ID '%s' has been successfully updated".formatted(formId), NOTICE);
        } else {
            flash.addMessage(attributes, "Form ID '%s' not found".formatted(formId), WARNING);
        }

        return "redirect:/form/index";
    }

    @PostMapping("/perform")
    public ModelAndView perform(@Valid FormDTO formDTO, BindingResult result, RedirectAttributes attributes,
                                @AuthenticationPrincipal UserInfo principal) {
        ModelAndView mav  = new ModelAndView("redirect:/form/index");
        User         user = principal.getUser();

        if (!result.hasFieldErrors()) {
            if (formDTO.getId() != null) {
                Form form = service.getById(formDTO.getId())
                        .map(f -> service.update(f, formDTO)).orElseGet(() -> service.create(user, formDTO));
                flash.addMessage(attributes, "Form ID '%s' has been successfully saved".formatted(form.getId()), SUCCESS);
            }
        } else {
            bindAttributes(mav, formDTO);
            mav.setViewName("form/index");
        }

        return mav;
    }

    private void bindAttributes(ModelAndView mav, FormDTO formDTO) {
        mav.addObject("formDTO", formDTO);
        mav.addObject("forms", service.getAll());
        mav.addObject("statuses", statuses);
    }

}
