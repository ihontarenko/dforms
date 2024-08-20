package df.base.common.validation.loader;

import df.base.common.validation.Configuration;
import df.base.common.validation.ValidatorException;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ConfigurationLoader implements Loader<Configuration.Collection, String> {

    @Override
    public Configuration.Collection load(String source) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Yaml        yaml   = new Yaml();

        try (InputStream inputStream = loader.getResourceAsStream(source)) {
            Configuration.Collection collection = yaml.loadAs(inputStream, Configuration.Collection.class);

            if (collection == null || collection.getValidators().isEmpty()) {
                throw new ValidatorException("INVALID OR EMPTY VALIDATOR-CONFIGURATION");
            }

            return collection;
        } catch (Exception e) {
            throw new ValidatorException("FAILED TO LOAD VALIDATION CONFIGURATION", e);
        }
    }

}
