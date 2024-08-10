package df.base.service.forms;

import df.base.jpa.User;
import df.base.jpa.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormConfigRepository formConfigRepository;

    @Autowired
    private FormFieldRepository formFieldRepository;

    @Autowired
    private FormFieldConfigRepository formFieldConfigRepository;

    @Autowired
    private FormFieldAttributeRepository formFieldAttributeRepository;

    @Autowired
    private FormFieldOptionRepository formFieldOptionRepository;

    @Transactional
    public Form createForm(User user, String name, String description, FormStatus status) {
        Form form = new Form();

        form.setUser(user);
        form.setName(name);
        form.setDescription(description);
        form.setStatus(status);

        return formRepository.save(form);
    }

    @Transactional
    public FormConfig createFormConfig(Form form, String configName, String configValue) {
        FormConfig formConfig = new FormConfig();

        formConfig.setForm(form);
        formConfig.setConfigName(configName);
        formConfig.setConfigValue(configValue);

        return formConfigRepository.save(formConfig);
    }

    @Transactional
    public FormField createFormField(Form form, ElementType elementType, String label, String name, String description, FieldStatus status) {
        FormField formField = new FormField();

        formField.setForm(form);
        formField.setElementType(elementType);
        formField.setLabel(label);
        formField.setName(name);
        formField.setDescription(description);
        formField.setStatus(status);

        return formFieldRepository.save(formField);
    }

    @Transactional
    public FormFieldOption createFormFieldOption(FormField formField, String optionValue, String optionLabel) {
        FormFieldOption option = new FormFieldOption();

        option.setFormField(formField);
        option.setOptionValue(optionValue);
        option.setOptionLabel(optionLabel);

        return formFieldOptionRepository.save(option);
    }

    @Transactional
    public FormFieldConfig createFormFieldConfig(FormField formField, String configName, String configValue) {
        FormFieldConfig fieldConfig = new FormFieldConfig();

        fieldConfig.setFormField(formField);
        fieldConfig.setConfigName(configName);
        fieldConfig.setConfigValue(configValue);

        return formFieldConfigRepository.save(fieldConfig);
    }

    @Transactional
    public FormFieldAttribute createFormFieldAttribute(FormField formField, String attributeName, String attributeValue) {
        FormFieldAttribute attribute = new FormFieldAttribute();

        attribute.setFormField(formField);
        attribute.setName(attributeName);
        attribute.setValue(attributeValue);

        return formFieldAttributeRepository.save(attribute);
    }

    @Transactional(readOnly = true)
    public Optional<Form> getFormById(String formId) {
        return formRepository.findById(formId);
    }

    @Transactional(readOnly = true)
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    @Transactional
    public Form updateForm(Form form, String name, String description, FormStatus status) {
        form.setName(name);
        form.setDescription(description);
        form.setStatus(status);

        return formRepository.save(form);
    }

    @Transactional
    public void deleteForm(String formId) {
        formRepository.deleteById(formId);
    }

}

