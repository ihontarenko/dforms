package df.base.common.validation;

public interface Validator<T> {

    void initialize(ValidatorBundle.Configuration configuration);

    ValidationResult validate(T value);

    void validate(T value, ValidationResult result);

    void addChild(Validator<T> child);

}
