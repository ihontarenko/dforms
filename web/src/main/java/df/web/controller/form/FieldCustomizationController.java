package df.web.controller.form;

import df.base.dto.SecondaryDTO;
import df.base.dto.form.FieldAttributeDTO;
import df.base.dto.form.FieldConfigDTO;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldOptionDTO;
import df.base.mapping.Mappers;
import df.base.mapping.form.FieldMapper;
import df.base.persistence.entity.form.*;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.service.form.FieldAttributeService;
import df.base.service.form.FieldConfigService;
import df.base.service.form.FieldOptionService;
import df.base.service.form.FieldService;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.html.HTMLBodyElement;

import java.util.HashMap;
import java.util.Map;

import static df.web.common.flash.FlashMessage.success;

@Controller
public class FieldCustomizationController implements FieldCustomizationOperations {

    private final ControllerHelper helper;
    private final FieldService     service;
    private final FieldMapper        mapper;
    private final FieldConfigService    configService;
    private final FieldAttributeService attributeService;
    private final FieldOptionService    optionService;

    public FieldCustomizationController(
            ControllerHelper helper, FieldService service, FieldMapper mapper,
            FieldConfigService configService, FieldAttributeService attributeService, FieldOptionService optionService) {
        this.helper = helper;
        this.service = service;
        this.mapper = mapper;
        this.configService = configService;
        this.attributeService = attributeService;
        this.optionService = optionService;
    }

    @Override
    public ModelAndView index(String section, String primaryId) {
        helper.setViewName(MAVConstants.VIEW_FIELD_CUSTOMIZATION);

        ModelAndView mav;

        try {
            bindAttributes(new SecondaryDTO.Simple(null, null, null, primaryId));
            mav = helper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView modify(String section, String primaryId, String itemId, RedirectAttributes attributes) {
        Mappers      mappers = Mappers.INSTANCE;
        ModelAndView mav;

        helper.setViewName(MAVConstants.VIEW_FIELD_CUSTOMIZATION);
        helper.setRedirectAttributes(attributes);

        try {
            Object mapped = switch (section) {
                case "config" -> mappers.get(FieldConfig.class).map(configService.requireById(itemId));
                case "attribute" -> mappers.get(FieldAttribute.class).map(attributeService.requireById(itemId));
                case "option" -> mappers.get(FieldOption.class).map(optionService.requireById(itemId));
                default -> throw new JpaResourceNotFoundException("Unsupported section '%s'".formatted(section));
            };
            bindAttributes((SecondaryDTO) mapped);
            mav = helper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(FieldConfigDTO itemDTO, BindingResult result, RedirectAttributes attributes) {
        return doPerform("config", itemDTO, result, attributes);
    }

    @Override
    public ModelAndView perform(FieldAttributeDTO itemDTO, BindingResult result, RedirectAttributes attributes) {
        return doPerform("attribute", itemDTO, result, attributes);
    }

    public ModelAndView perform(FieldOptionDTO itemDTO, BindingResult result, RedirectAttributes attributes) {
        return doPerform("option", itemDTO, result, attributes);
    }

    private ModelAndView doPerform(String section, SecondaryDTO itemDTO, BindingResult result, RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName(MAVConstants.VIEW_FIELD_CUSTOMIZATION);
        helper.setRedirectUrl(MAVConstants.REDIRECT_FIELD_CUSTOMIZATION.formatted(itemDTO.getPrimaryId(), section));
        helper.attribute("section", section);

        bindAttributes(itemDTO);

        if (!result.hasErrors()) {
            Field field = service.requireById(itemDTO.getPrimaryId());

            if (section.equals("config")) {
                configService.createOrUpdate(field, (FieldConfigDTO) itemDTO);
//                case "attribute" -> attributeService.createOrUpdate(field, (FieldAttributeDTO) itemDTO);
//                case "option" -> optionService.createOrUpdate(field, (FieldOptionDTO) itemDTO);
            } else {
                throw new JpaResourceNotFoundException("Unsupported section '%s'".formatted(section));
            }

            helper.addMessage(success("something saved"));
        }

        return helper.resolveWithRedirect();
    }

    private void bindAttributes(SecondaryDTO secondaryDTO) {
        Map<String, Object> attributes = new HashMap<>();
        FieldDTO            fieldDTO   = mapper.map(service.requireById(secondaryDTO.getPrimaryId()));

        attributes.put("secondaryDTO", secondaryDTO);
        attributes.put("field", fieldDTO);

        helper.attributes(attributes);
    }

}
