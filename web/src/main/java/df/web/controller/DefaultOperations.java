package df.web.controller;

import df.application.exception.UnsupportedRequestMethod;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface DefaultOperations<DTO> {
    
    UnsupportedRequestMethod UNSUPPORTED_REQUEST_METHOD = new UnsupportedRequestMethod(
            "This is the default controller and is not supported or not implemented yet");

    default ModelAndView index() {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    default ModelAndView index(@PathVariable("ownerId") String ownerId) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }


    default ModelAndView create() {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    default ModelAndView modify(@PathVariable("itemId") String itemId,
                                RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    default ModelAndView modify(@PathVariable("ownerId") String ownerId, @PathVariable("itemId") String itemId,
                                RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    default ModelAndView perform(@ModelAttribute("itemDTO") @Valid DTO itemDTO,
                                 BindingResult result, RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    default ModelAndView remove(@PathVariable("itemId") String itemId, RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    default ModelAndView status(
            @PathVariable("itemId") String itemId,
            @PathVariable("status") String status,
            RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

    default ModelAndView status(
            @PathVariable("primaryId") String primaryId,
            @PathVariable("itemId") String itemId,
            @PathVariable("status") String status,
            RedirectAttributes attributes) {
        throw UNSUPPORTED_REQUEST_METHOD;
    }

}
