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
    default ModelAndView index() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{ownerId}")
    default ModelAndView index(@PathVariable("ownerId") String ownerId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/create")
    default ModelAndView create() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/modify/{itemId}")
    default ModelAndView modify(@PathVariable("itemId") String itemId,
                                RedirectAttributes attributes) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/perform")
    default ModelAndView perform(@ModelAttribute("itemDTO") @Valid DTO itemDTO,
                                 BindingResult result, RedirectAttributes attributes) {
        throw new UnsupportedOperationException();
    }

    @PreAuthorize("hasRole('SUPER_USER')")
    @GetMapping("/remove/{itemId}")
    default ModelAndView remove(@PathVariable("itemId") String itemId,
                                RedirectAttributes attributes) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/status/{status}/{itemId}")
    default ModelAndView status(@PathVariable("itemId") String itemId, @PathVariable("status") String status,
                                RedirectAttributes attributes) {
        throw new UnsupportedOperationException();
    }

}
