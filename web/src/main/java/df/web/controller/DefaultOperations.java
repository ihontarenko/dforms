package df.web.controller;

import df.base.common.exception.UnsupportedRequestMethod;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/default")
public interface DefaultOperations<DTO> {
    
    UnsupportedRequestMethod UNSUPPORTED_REQUEST_METHOD = new UnsupportedRequestMethod(
            "This is the default operation and is not supported or implemented yet");

    @GetMapping
    default ModelAndView index() {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    @GetMapping("/{ownerId}")
    default ModelAndView index(@PathVariable("ownerId") String ownerId) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    @GetMapping("/create")
    default ModelAndView create() {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    @GetMapping("/modify/{itemId}")
    default ModelAndView modify(@PathVariable("itemId") String itemId,
                                RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    @PostMapping("/perform")
    default ModelAndView perform(@ModelAttribute("itemDTO") @Valid DTO itemDTO,
                                 BindingResult result, RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    @PreAuthorize("hasRole('SUPER_USER')")
    @GetMapping("/remove/{itemId}")
    default ModelAndView remove(@PathVariable("itemId") String itemId,
                                RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    @GetMapping("/status/{status}/{itemId}")
    default ModelAndView status(@PathVariable("itemId") String itemId, @PathVariable("status") String status,
                                RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

}
