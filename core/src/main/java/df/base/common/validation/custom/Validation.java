package df.base.common.validation.custom;

import df.base.common.beans.BeanObjectInfo;
import df.base.common.beans.BeanObjectInfoFactory;
import df.base.common.beans.FieldAccessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Validation {

    private final AutowireCapableBeanFactory   beanFactory;
    private final List<Validator>              globalValidators = new ArrayList<>();
    private final Map<String, List<Validator>> fieldsValidators = new HashMap<>();
    private final String                       name;
    private final MessageResolver              resolver;
    private final BindingResultMapper          bindingResultMapper;

    public Validation(String name, MessageResolver resolver, ApplicationContext context) {
        this.beanFactory = context.getAutowireCapableBeanFactory();
        this.bindingResultMapper = new BindingResultMapper();
        this.name = name;
        this.resolver = resolver;
    }

    public String getName() {
        return name;
    }

    public void addValidator(Validator validator) {
        this.globalValidators.add(validator);
    }

    public void addValidator(String fieldName, Validator validator) {
        this.fieldsValidators.computeIfAbsent(fieldName, key -> new ArrayList<>()).add(validator);
    }

    @SuppressWarnings({"all"})
    public Errors validate(Object object, ValidationContext validationContext) {
        Errors errors = new Errors();

        validationContext.setAttribute(ValidationContext.VALIDATION_MANAGER_KEY, this);

        for (Validator validator : globalValidators) {
            applyValidator(object, validator, validationContext, errors);
        }

        return errors;
    }

    public void validate(Object object, ValidationContext validationContext, BindingResult result) {
        BindingResult bindingResult = validationContext.getAttribute(ValidationContext.BINDING_RESULT_KEY);

        if (bindingResult == null) {
            bindingResult = result;
        }

        bindingResultMapper.map(validate(object, validationContext), bindingResult);
    }

    public Errors validate(Object object, String fieldName, ValidationContext validationContext) {
        Errors errors = new Errors();
        validationContext.setAttribute(ValidationContext.VALIDATION_MANAGER_KEY, this);

        BeanObjectInfo  beanInfo   = createBeanObjectInfo(object);
        FieldAccessor   accessor   = beanInfo.getBeanField(fieldName).getFieldAccessor();
        Object          fieldValue = accessor.getValue();
        List<Validator> validators = fieldsValidators.getOrDefault(fieldName, new ArrayList<>());

        for (Validator validator : validators) {
            applyValidator(fieldValue, validator, validationContext, errors);
        }

        return errors;
    }

    public void validate(Object object, String fieldName, ValidationContext validationContext, BindingResult result) {
        BindingResult bindingResult = validationContext.getAttribute(ValidationContext.BINDING_RESULT_KEY);

        if (bindingResult == null) {
            bindingResult = result;
        }

        bindingResultMapper.map(validate(object, fieldName, validationContext), bindingResult);
    }

    private void applyValidator(Object object, Validator validator, ValidationContext validationContext, Errors errors) {
        Class<?> objectType = object == null ? Object.class : object.getClass();

        if (validator.supports(objectType)) {
            try {
                beanFactory.autowireBean(validator);
                validator.validate(object, validationContext);
            } catch (ValidationException exception) {
                ErrorMessage message = resolver.resolve(name, exception.getErrorCode(), exception.getErrorContext());

                // overwrite error message if any in exception
                if (StringUtils.hasText(exception.getMessage())) {
                    message = new ErrorMessage(message.code(), exception.getMessage(), message.pointer());
                }

                errors.add(message);
            }
        }
    }

    private BeanObjectInfo createBeanObjectInfo(Object object) {
        return BeanObjectInfoFactory.createBeanObjectInfo(object);
    }

    public void configure(Consumer<Validation> consumer) {
        consumer.accept(this);
    }

}
