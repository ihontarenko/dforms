package df.web.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/default")
public interface DefaultOperations<DTO> {

    @GetMapping
    ModelAndView index();

    @GetMapping("/create")
    ModelAndView create();

    @GetMapping("/modify/{itemId}")
    ModelAndView modify(@PathVariable("itemId") String itemId, RedirectAttributes attributes);

    @PostMapping("/perform")
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid DTO itemDTO, BindingResult result, RedirectAttributes attributes);

    @PreAuthorize("hasRole('SUPER_USER')")
    @GetMapping("/delete/{itemId}")
    ModelAndView remove(@PathVariable("itemId") String itemId, RedirectAttributes attributes);

    @GetMapping("/status/{status}/{itemId}")
    ModelAndView status(@PathVariable("itemId") String itemId, @PathVariable("status") String status, RedirectAttributes attributes);

}
