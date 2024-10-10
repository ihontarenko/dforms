package df.web.controller.bean_console;

import df.base.service.ClassService;
import df.web.common.flash.FlashMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bean-console")
public class BeanConsoleController {

    private final FlashMessageService flashMessage;

    private final ClassService classService;

    public BeanConsoleController(FlashMessageService flashMessage, ClassService classService) {
        this.flashMessage = flashMessage;
        this.classService = classService;
    }

    @GetMapping("/index")
    public ModelAndView index() {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("enums", classService.findEnums());

        classService.findEnums().stream().collect(Collectors.groupingBy(
                classDTO -> classDTO.getClass().getPackageName(), Collectors.toSet()));

        return new ModelAndView("bean_console/index", attributes);
    }

}
