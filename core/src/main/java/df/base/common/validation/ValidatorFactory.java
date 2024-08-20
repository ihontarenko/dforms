package df.base.common.validation;

import df.base.common.jbm.bean.Bean;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Bean
public class ValidatorFactory<T> {

    private final Map<String, List<Validator<?>>> validators = new HashMap<>();

    public void loadValidator(String yamlFile) {
        Yaml        yaml   = new Yaml();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = loader.getResourceAsStream(yamlFile)) {
            ValidatorBundle bundle = yaml.loadAs(inputStream, ValidatorBundle.class);

            if (bundle == null || bundle.getValidators().isEmpty()) {
                throw new ValidatorException("INVALID OR EMPTY CONFIGURATION");
            }

            List<ValidatorBundle.Configuration> configurations = bundle.getValidators();

            for (ValidatorBundle.Configuration configuration : configurations) {
                validators.computeIfAbsent(yamlFile, key -> new ArrayList<>())
                        .add(createValidator(configuration));
            }
        } catch (Exception e) {
            throw new ValidatorException("FAILED TO LOAD VALIDATION CONFIGURATION", e);
        }
    }

    @SuppressWarnings({"unchecked"})
    private Validator<T> createValidator(ValidatorBundle.Configuration config) {
        try {
            Class<?>     validatorClass = Class.forName(config.getValidator());
            Validator<T> validator      = (Validator<T>) validatorClass.getDeclaredConstructor().newInstance();

            validator.initialize(config);

            if (config.getChildren() != null && !config.getChildren().isEmpty()) {
                for (ValidatorBundle.Configuration child : config.getChildren()) {
                    validator.addChild(createValidator(child));
                }
            }

            return validator;
        } catch (Exception e) {
            throw new ValidatorException("FAILED TO CREATE VALIDATOR INSTANCE", e);
        }
    }
}
