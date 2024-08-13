package df.web.common.flash;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class FlashMessageService {

    public void addMessage(RedirectAttributes redirectAttributes, BindingResult bindingResult, FlashMessageType type) {
        if (bindingResult.hasFieldErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                addMessage(redirectAttributes,
                        "%s: %s".formatted(fieldError.getField(), fieldError.getDefaultMessage()), type);
            }
        }
    }

    public void addMessage(FlashMap flashMap, String message, FlashMessageType type) {
        Map<FlashMessageType, List<String>> messages = (Map<FlashMessageType, List<String>>) flashMap.get("flashMessages");

        messages = performMessages(messages, type, message);

        flashMap.put("flashMessages", messages);
    }

    public void addMessage(RedirectAttributes redirectAttributes, String message, FlashMessageType type) {
        Map<String, ?>                      attributes = redirectAttributes.getFlashAttributes();
        Map<FlashMessageType, List<String>> messages   = (Map<FlashMessageType, List<String>>) attributes.get("flashMessages");

        messages = performMessages(messages, type, message);

        redirectAttributes.addFlashAttribute("flashMessages", messages);
    }

    public void addMessage(ModelMap modelMap, String message, FlashMessageType type) {
        Map<FlashMessageType, List<String>> messages = (Map<FlashMessageType, List<String>>) modelMap.getAttribute("alertMessages");

        messages = performMessages(messages, type, message);

        modelMap.addAttribute("alertMessages", messages);
    }

    private Map<FlashMessageType, List<String>> performMessages(Map<FlashMessageType, List<String>> messages, FlashMessageType type, String message) {
        if (messages == null) {
            messages = new HashMap<>();
        }

        messages.computeIfAbsent(type, key -> new ArrayList<>()).add(message);

        return messages;
    }

}
