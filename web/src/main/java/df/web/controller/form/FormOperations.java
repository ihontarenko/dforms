package df.web.controller.form;

import df.base.common.breadcrumb.Breadcrumbs;
import df.base.dto.form.FormDTO;
import df.web.controller.DefaultOperations;
import df.web.controller.MAVConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@RequestMapping(MAVConstants.REQUEST_MAPPING_FORM)
public interface FormOperations extends DefaultOperations<FormDTO> {

    @Override
    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms")
    })
    @GetMapping
    ModelAndView index();

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms", url = "/form"),
            @Breadcrumbs.Item(label = "Preview Form")
    })
    @GetMapping("/{primaryId}/demo")
    ModelAndView demo(@PathVariable("primaryId") String primaryId, RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms", url = "/form"),
            @Breadcrumbs.Item(label = "Preview Form")
    })
    @PostMapping("/{primaryId}/demo")
    ModelAndView demo(@PathVariable("primaryId") String primaryId,
                      @RequestParam Map<String, Object> postData,
                      HttpServletRequest request,
                      RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Forms", url = "/form"),
            @Breadcrumbs.Item(label = "Modify '{itemId}'")
    })
    @GetMapping("/{itemId}/modify")
    ModelAndView modify(@PathVariable("itemId") String itemId,
                        RedirectAttributes attributes);

    @Breadcrumbs({
            @Breadcrumbs.Item(label = "Home", url = "/"),
            @Breadcrumbs.Item(label = "Performing")
    })
    @PostMapping("/perform")
    ModelAndView perform(@ModelAttribute("itemDTO") @Valid FormDTO itemDTO, BindingResult result,
                         RedirectAttributes attributes);

    @GetMapping("/{itemId}/remove")
    ModelAndView remove(@PathVariable("itemId") String itemId,
                        RedirectAttributes attributes);

    @GetMapping("/{itemId}/status/{status}")
    ModelAndView status(@PathVariable("itemId") String itemId, @PathVariable("status") String status,
                        RedirectAttributes attributes);

}
