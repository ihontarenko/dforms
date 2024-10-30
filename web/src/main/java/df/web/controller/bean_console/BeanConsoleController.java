package df.web.controller.bean_console;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.common.pipeline.PipelineContextFactoty;
import df.base.common.pipeline.context.PipelineContext;
import df.base.common.validation.custom.Validator;
import df.base.dto.reflection.ClassDTO;
import df.base.dto.reflection.MethodDTO;
import df.base.service.ClassManagmentService;
import df.base.service.ClassService;
import df.web.common.flash.FlashMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/bean-console")
public class BeanConsoleController {

    private final FlashMessageService flashMessage;
    private final ClassService        classService;
    private final ClassManagmentService classManagmentService;

    public BeanConsoleController(FlashMessageService flashMessage, ClassService classService, ClassManagmentService classManagmentService) {
        this.flashMessage = flashMessage;
        this.classService = classService;
        this.classManagmentService = classManagmentService;
    }

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Bean Console")
    })
    @GetMapping("/index")
    public ModelAndView index() {
        Map<String, Object> attributes = new HashMap<>();
        Set<ClassDTO>       types      = classService.findImplementations(Validator.class);

        attributes.put("types", classService.groupedClasses(types, dto -> Validator.class.getName()));

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
        Set<ClassDTO>       classes    = classService.findClassesByName(keyword);

        attributes.put("types", classService.groupedClasses(classes, dto -> dto.getNativeClass().getPackageName()));
        attributes.put("keyword", keyword);
        attributes.put("length", classes.size());

        return new ModelAndView("bean_console/search", attributes);
    }

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Bean Console", url = "/bean-console/index"),
            @Breadcrumbs.Item(label = "{class}")
    })
    @GetMapping("/_/{class}")
    public ModelAndView clazz(@PathVariable("class") String className) {
        Map<String, Object> attributes = new HashMap<>();
        ClassDTO            classDTO   = classService.getClass(className);

        attributes.put("class", classDTO);
        attributes.put("methods", classService.groupedMethods(new HashSet<>(classDTO.getMethods()), MethodDTO::getAccessLevel));

        return new ModelAndView("bean_console/class_item", attributes);
    }

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Bean Console", url = "/bean-console/index"),
            @Breadcrumbs.Item(label = "{class}", url = "/bean-console/_/{class}"),
            @Breadcrumbs.Item(label = "{method}")
    })
    @GetMapping("/_/{class}/{method}")
    public ModelAndView method(@PathVariable("class") String className) {
        Map<String, Object> attributes = new HashMap<>();
        ClassDTO        classDTO = classService.getClass(className);
        PipelineContext context  = PipelineContextFactoty.createByDefault();

        classManagmentService.performPipeline(context);

        attributes.put("class", classDTO);
        attributes.put("methods", classService.groupedMethods(new HashSet<>(classDTO.getMethods()), MethodDTO::getAccessLevel));

        return new ModelAndView("bean_console/class_item", attributes);
    }

}
