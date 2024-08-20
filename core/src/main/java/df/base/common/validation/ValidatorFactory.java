package df.base.common.validation;

import df.base.common.jbm.bean.Bean;
import df.base.common.validation.builder.ValidationBuilder;
import df.base.common.validation.loader.ConfigurationLoader;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@Bean
public class ValidatorFactory {

    private final Map<String, ValidatorHolder> holders = new HashMap<>();

    public void loadValidatorHolder(String yamlFile) {
        Configuration.Collection configuration = new ConfigurationLoader().load(yamlFile);
        ValidatorHolder          holder        = new ValidationBuilder().build(configuration);
        holders.put(holder.getName(), holder);
    }

    public ValidatorHolder getValidatorHolder(String holderName) {
        return requireNonNull(holders.get(holderName), "VALIDATOR HOLDER '%s' COULD NOT BE FOUND"
                .formatted(holderName));
    }


}
