package df.web.controller.bean_console;

import df.common.breadcrumb.Breadcrumbs;
import df.application.dto.reflection.ClassDTO;
import df.application.dto.reflection.ClassSetDTO;
import df.application.service.bc.ClassManagementService;
import df.application.service.bc.ClassService;
import df.web.common.flash.FlashMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/bean-console")
public class BeanConsoleController {

    private final FlashMessageService   flashMessage;
    private final ClassService           classService;
    private final ClassManagementService classManagement;


    public BeanConsoleController(FlashMessageService flashMessage, ClassService classService, ClassManagementService managementService) {
        this.flashMessage = flashMessage;
        this.classService = classService;
        this.classManagement = managementService;
    }

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Bean Console")
    })
    @GetMapping("/index")
    public ModelAndView index() {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("html", "hello!");

        return new ModelAndView("bean_console/index", attributes);
    }

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Bean Console", url = "/bean-console/index"),
            @Breadcrumbs.Item(label = "Search")
    })
    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> attributes = new HashMap<>();
        ClassSetDTO         classes    = classService.findClassesByName(keyword);

        attributes.put("keyword", keyword);
        attributes.put("html", classManagement.renderHtml(classes));

        return new ModelAndView("bean_console/index", attributes);
    }

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Bean Console", url = "/bean-console/index"),
            @Breadcrumbs.Item(label = "{class}")
    })
    @GetMapping("/_class/{class}")
    public ModelAndView classItem(@PathVariable("class") String className) {
        Map<String, Object> attributes = new HashMap<>();
        ClassDTO            classDTO   = classService.getClass(className);

        attributes.put("html", classManagement.renderHtml(classDTO));

        return new ModelAndView("bean_console/item", attributes);
    }

}
