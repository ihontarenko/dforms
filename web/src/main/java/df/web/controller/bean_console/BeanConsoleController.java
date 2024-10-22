package df.web.controller.bean_console;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.common.validation.custom.Validator;
import df.base.dto.reflection.ClassDTO;
import df.base.service.ClassService;
import df.web.common.flash.FlashMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/bean-console")
public class BeanConsoleController {

    private final FlashMessageService flashMessage;
    private final ClassService        classService;

    public BeanConsoleController(FlashMessageService flashMessage, ClassService classService) {
        this.flashMessage = flashMessage;
        this.classService = classService;
    }

    @Breadcrumbs({@Breadcrumbs.Item(label = "Home", url = "/"), @Breadcrumbs.Item(label = "Bean Console")})
    @GetMapping("/index")
    public ModelAndView index() {
        Map<String, Object> attributes = new HashMap<>();
        Set<ClassDTO>       types      = classService.findImplementations(Validator.class);

        Map<String, Set<ClassDTO>> grouped = classService.groupedClasses(types, dto
                -> dto.getNativeClass().getPackageName());

        attributes.put("types", grouped);

        return new ModelAndView("bean_console/index", attributes);
    }

    @Breadcrumbs({@Breadcrumbs.Item(label = "Home", url = "/"), @Breadcrumbs.Item(label = "Bean Console", url = "/bean-console/index"), @Breadcrumbs.Item(label = "Search")})
    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> attributes = new HashMap<>();
        Set<ClassDTO>       classes    = classService.findClassesByName(keyword);

        attributes.put("types", classService.groupedClasses(classes, dto -> dto.getNativeClass().getPackageName()));
        attributes.put("keyword", keyword);
        attributes.put("length", classes.size());

        return new ModelAndView("bean_console/search", attributes);
    }

}
