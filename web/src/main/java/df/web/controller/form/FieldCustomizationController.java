package df.web.controller.form;

import df.base.Messages;
import df.base.dto.SlaveDTO;
import df.base.dto.form.FieldAttributeDTO;
import df.base.dto.form.FieldConfigDTO;
import df.base.dto.form.FieldOptionDTO;
import df.base.mapping.form.FieldAttributeMapper;
import df.base.mapping.form.FieldConfigMapper;
import df.base.mapping.form.FieldMapper;
import df.base.mapping.form.FieldOptionMapper;
import df.base.persistence.entity.form.Field;
import df.base.persistence.entity.support.ElementType;
import df.base.persistence.exception.JpaResourceIneligibleException;
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

import java.util.List;

import static df.base.Messages.*;
import static df.base.persistence.entity.support.ElementType.*;
import static df.web.common.flash.FlashMessage.success;

@Controller
public class FieldCustomizationController implements FieldCustomizationOperations {

    private final ControllerHelper      helper;
    private final FieldService          service;
    private final FieldMapper           mapper;
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
            bindAttributes(null, primaryId, section);
            mav = helper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView modify(String section, String primaryId, String itemId, RedirectAttributes attributes) {
        ModelAndView mav;

        helper.setViewName(MAVConstants.VIEW_FIELD_CUSTOMIZATION);
        helper.setRedirectAttributes(attributes);
        helper.attribute("section", section);

        try {
            Object mapped = switch (section) {
                case "config" -> new FieldConfigMapper().map(configService.requireById(itemId));
                case "attribute" -> new FieldAttributeMapper().map(attributeService.requireById(itemId));
                case "option" -> new FieldOptionMapper().map(optionService.requireById(itemId));
                default -> throw new JpaResourceNotFoundException(SUCCESS_CUSTOMIZATION_UNSUPPORTED
                        .formatted(section));
            };
            bindAttributes((SlaveDTO) mapped, primaryId, section);
            mav = helper.resolveWithoutRedirect();
        } catch (JpaResourceNotFoundException exception) {
            mav = helper.redirect(exception);
        }

        return mav;
    }

    @Override
    public ModelAndView perform(String primaryId, FieldConfigDTO itemDTO,
                                BindingResult result, RedirectAttributes attributes) {
        return doPerform("config", itemDTO, primaryId, result, attributes);
    }

    @Override
    public ModelAndView perform(String primaryId, FieldAttributeDTO itemDTO,
                                BindingResult result, RedirectAttributes attributes) {
        return doPerform("attribute", itemDTO, primaryId, result, attributes);
    }

    @Override
    public ModelAndView perform(String primaryId, FieldOptionDTO itemDTO,
                                BindingResult result, RedirectAttributes attributes) {
        return doPerform("option", itemDTO, primaryId, result, attributes);
    }

    private ModelAndView doPerform(String section, SlaveDTO itemDTO, String primaryId,
                                   BindingResult result, RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName(MAVConstants.VIEW_FIELD_CUSTOMIZATION);
        helper.setRedirectUrl(MAVConstants.REDIRECT_FIELD_CUSTOMIZATION.formatted(itemDTO.getPrimaryId(), section));
        helper.attribute("section", section);

        bindAttributes(itemDTO, primaryId, section);

        if (!result.hasErrors()) {
            Field field = service.requireById(itemDTO.getPrimaryId());

            switch (section.toLowerCase()) {
                case "config" -> configService.createOrUpdate(field, (FieldConfigDTO) itemDTO);
                case "attribute" -> attributeService.createOrUpdate(field, (FieldAttributeDTO) itemDTO);
                case "option" -> optionService.createOrUpdate(field, (FieldOptionDTO) itemDTO);
                default -> throw new JpaResourceNotFoundException(SUCCESS_CUSTOMIZATION_UNSUPPORTED
                        .formatted(section));
            }

            helper.addMessage(success(Messages.SUCCESS_CUSTOMIZATION_SAVED.formatted(section)));
        }

        return helper.resolveWithRedirect();
    }

    @Override
    public ModelAndView remove(String section, String primaryId, String itemId, RedirectAttributes attributes) {
        helper.setRedirectAttributes(attributes);
        helper.setRedirectUrl(MAVConstants.REDIRECT_FIELD_CUSTOMIZATION.formatted(primaryId, section));

        switch (section.toLowerCase()) {
            case "config" -> configService.deleteIfExists(itemId);
            case "attribute" -> attributeService.deleteIfExists(itemId);
            case "option" -> optionService.deleteIfExists(itemId);
            default -> throw new JpaResourceNotFoundException(SUCCESS_CUSTOMIZATION_UNSUPPORTED
                    .formatted(section));
        }

        helper.addMessage(success(SUCCESS_CUSTOMIZATION_DELETED.formatted(section)));

        return helper.redirect();
    }

    @Override
    public ModelAndView embedded(String primaryId) {
        return null;
    }

    private void bindAttributes(SlaveDTO slaveDTO, String primaryId, String section) {
        Field             field               = service.requireById(primaryId);
        List<ElementType> allowedElementTypes = List.of(CHECKBOX, RADIO, SELECT);
        ElementType       elementType         = field.getElementType();

        if ("option".equalsIgnoreCase(section) && !allowedElementTypes.contains(elementType)) {
            JpaResourceIneligibleException exception = new JpaResourceIneligibleException(
                    SUCCESS_CUSTOMIZATION_INELIGIBLE_FIELD.formatted(elementType));
            // redirect to the "config" section as it is applicable to any field type
            exception.setRedirectUrl(MAVConstants.REDIRECT_FIELD_CUSTOMIZATION.formatted(primaryId, "config"));
            throw exception;
        }

        helper.attribute("slave", slaveDTO);
        helper.attribute("field", mapper.map(field));
        helper.attribute("section", section);
    }

}
