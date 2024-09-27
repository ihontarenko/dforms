package df.web.controller.form;

import df.base.common.specification.SpecificationContext;
import df.base.common.specification.SpecificationRunner;
import df.base.common.validation.custom.ValidationContext;
import df.base.dto.NestedKeyValue;
import df.base.dto.form.FieldAttributeDTO;
import df.base.dto.form.FieldConfigDTO;
import df.base.dto.form.FieldDTO;
import df.base.dto.form.FieldOptionDTO;
import df.base.mapping.form.DeepFieldMapper;
import df.base.mapping.form.FieldAttributeMapper;
import df.base.mapping.form.FieldConfigMapper;
import df.base.mapping.form.FieldOptionMapper;
import df.base.persistence.entity.form.Field;
import df.base.persistence.exception.JpaResourceNotFoundException;
import df.base.service.form.FieldAttributeService;
import df.base.service.form.FieldConfigService;
import df.base.service.form.FieldOptionService;
import df.base.service.form.FieldService;
import df.base.specification.FieldSelectiveSpecification;
import df.base.specification.FieldTypeSpecification;
import df.web.common.ControllerHelper;
import df.web.controller.MAVConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static df.base.Messages.*;
import static df.web.common.flash.FlashMessage.primary;
import static df.web.common.flash.FlashMessage.success;
import static df.web.controller.MAVConstants.REDIRECT_FIELD_CUSTOMIZATION;

@Controller
public class FieldCustomizationController implements FieldCustomizationOperations {

    private final ControllerHelper      helper;
    private final FieldService          service;
    private final DeepFieldMapper       mapper;
    private final FieldConfigService    configService;
    private final FieldAttributeService attributeService;
    private final FieldOptionService    optionService;

    public FieldCustomizationController(
            ControllerHelper helper, FieldService service, DeepFieldMapper mapper,
            FieldConfigService configService, FieldAttributeService attributeService, FieldOptionService optionService) {
        this.helper = helper;
        this.service = service;
        this.mapper = mapper;
        this.configService = configService;
        this.attributeService = attributeService;
        this.optionService = optionService;
    }

    @Override
    public ModelAndView index(String section, String primaryId, RedirectAttributes attributes) {
        helper.setViewName("EMBEDDED".equalsIgnoreCase(section)
                ? MAVConstants.VIEW_FIELD_EMBEDDED : MAVConstants.VIEW_FIELD_CUSTOMIZATION);
        helper.setRedirectAttributes(attributes);
        helper.setRedirectUrl(REDIRECT_FIELD_CUSTOMIZATION.formatted(primaryId, "config"));

        ModelAndView mav;

        try {
            bindAttributes(null, primaryId, section);
            mav = helper.resolveWithoutRedirect();
        } catch (Exception exception) {
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

            bindAttributes((NestedKeyValue) mapped, primaryId, section);
            mav = helper.resolveWithoutRedirect();
        } catch (Exception exception) {
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

    private ModelAndView doPerform(String section, NestedKeyValue itemDTO, String primaryId,
                                   BindingResult result, RedirectAttributes attributes) {
        helper.setBindingResult(result);
        helper.setRedirectAttributes(attributes);
        helper.setViewName(MAVConstants.VIEW_FIELD_CUSTOMIZATION);
        helper.setRedirectUrl(REDIRECT_FIELD_CUSTOMIZATION.formatted(itemDTO.getPrimaryId(), section));
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

            helper.addMessage(success(SUCCESS_CUSTOMIZATION_SAVED.formatted(section)));
        }

        return helper.resolveWithRedirect();
    }

    @Override
    public ModelAndView remove(String section, String primaryId, String itemId, RedirectAttributes attributes) {
        helper.setRedirectAttributes(attributes);
        helper.setRedirectUrl(REDIRECT_FIELD_CUSTOMIZATION.formatted(primaryId, section));

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
    public ModelAndView attach(String primaryId, FieldDTO itemDTO, BindingResult result,
                               RedirectAttributes attributes, ValidationContext context) {
        helper.setRedirectUrl(REDIRECT_FIELD_CUSTOMIZATION.formatted(primaryId, "embedded"));
        helper.setRedirectAttributes(attributes);

        if (!result.hasErrors()) {
            service.attach(primaryId, itemDTO.id());
            helper.addMessage(success(SUCCESS_EMBEDDED_ATTACHED.formatted(primaryId, itemDTO.id())));
        }

        return helper.redirect();
    }

    @Override
    public ModelAndView detach(String primary, String embeddedId, RedirectAttributes attributes) {
        helper.setRedirectUrl(REDIRECT_FIELD_CUSTOMIZATION.formatted(primary, "embedded"));
        helper.setRedirectAttributes(attributes);
        helper.addMessage(primary(SUCCESS_EMBEDDED_DETACHED.formatted(primary, embeddedId)));

        service.detach(primary, embeddedId);

        return helper.redirect();
    }

    private void bindAttributes(NestedKeyValue nestedKeyValue, String primaryId, String section) {
        Field    fieldEntity = service.requireById(primaryId);
        FieldDTO fieldDTO    = mapper.map(fieldEntity);
        SpecificationContext context = new SpecificationContext.Builder().with("section", section)
                .with("redirect", REDIRECT_FIELD_CUSTOMIZATION.formatted(primaryId, "config")).build();

        new SpecificationRunner<Field>().checkAllSatisfied(
                fieldEntity, context, new FieldSelectiveSpecification(), new FieldTypeSpecification());

        helper.attribute("embeddable", service.getEmbeddableFields());
        helper.attribute("slave", nestedKeyValue);
        helper.attribute("field", fieldDTO);
        helper.attribute("section", section);
    }

}
