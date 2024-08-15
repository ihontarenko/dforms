package df.web.common.flash;

import df.web.common.flash.FlashMessage.Type;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class FlashMessageService {

    public void addMessage(FlashMap flashMap, String message, Type type) {
        Map<Type, List<String>> messages = (Map<Type, List<String>>) flashMap.get("flashMessages");

        messages = performMessages(messages, type, message);

        flashMap.put("flashMessages", messages);
    }

    public void addMessage(RedirectAttributes redirectAttributes, String message, Type type) {
        Map<String, ?>          attributes = redirectAttributes.getFlashAttributes();
        Map<Type, List<String>> messages   = (Map<Type, List<String>>) attributes.get("flashMessages");

        messages = performMessages(messages, type, message);

        redirectAttributes.addFlashAttribute("flashMessages", messages);
    }

    public void addMessage(ModelMap modelMap, String message, Type type) {
        Map<Type, List<String>> messages = (Map<Type, List<String>>) modelMap.getAttribute("alertMessages");

        messages = performMessages(messages, type, message);

        modelMap.addAttribute("alertMessages", messages);
    }

    public void addMessage(FlashMap flashMap, FlashMessage message) {
        addMessage(flashMap, message.getMessage(), message.getType());
    }

    public void addMessage(RedirectAttributes redirectAttributes, FlashMessage message) {
        addMessage(redirectAttributes, message.getMessage(), message.getType());
    }

    public void addMessage(ModelMap modelMap, FlashMessage message) {
        addMessage(modelMap, message.getMessage(), message.getType());
    }

    private Map<Type, List<String>> performMessages(Map<Type, List<String>> messages, Type type, String message) {
        if (messages == null) {
            messages = new HashMap<>();
        }

        messages.computeIfAbsent(type, key -> new ArrayList<>()).add(message);

        return messages;
    }

}
