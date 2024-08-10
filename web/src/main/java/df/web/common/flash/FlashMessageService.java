package df.web.common.flash;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class FlashMessageService {

    public void addFlashMessage(RedirectAttributes redirectAttributes, String message, FlashMessageType type) {
        Map<String, ?>                      attributes = redirectAttributes.getFlashAttributes();
        Map<FlashMessageType, List<String>> messages   = (Map<FlashMessageType, List<String>>) attributes.get("flashMessages");

        if (messages == null) {
            messages = new HashMap<>();
        }

        messages.computeIfAbsent(type, key -> new ArrayList<>()).add(message);
        redirectAttributes.addFlashAttribute("flashMessages", messages);
    }

    public void addAttribute(ModelMap modelMap, String message, FlashMessageType type) {
        Map<FlashMessageType, List<String>> messages   = (Map<FlashMessageType, List<String>>) modelMap.getAttribute("alertMessages");

        if (messages == null) {
            messages = new HashMap<>();
        }

        messages.computeIfAbsent(type, key -> new ArrayList<>()).add(message);
        modelMap.addAttribute("alertMessages", messages);
    }

}
