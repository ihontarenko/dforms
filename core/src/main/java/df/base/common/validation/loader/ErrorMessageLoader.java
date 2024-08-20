package df.base.common.validation.loader;

import df.base.common.validation.ErrorMessage;
import df.base.common.validation.ValidatorException;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ErrorMessageLoader implements Loader<ErrorMessage.Collection, String> {

    @Override
    public ErrorMessage.Collection load(String source) {
        Yaml                            yaml       = new Yaml();
        ClassLoader                     loader     = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = loader.getResourceAsStream(source)) {
            ErrorMessage.Collection collection = yaml.loadAs(inputStream, ErrorMessage.Collection.class);

            if (collection == null || collection.getMessages().isEmpty()) {
                throw new ValidatorException("INVALID OR EMPTY ERROR-MESSAGES COLLECTION");
            }

            return collection;
        } catch (Exception e) {
            throw new ValidatorException("FAILED TO LOAD VALIDATION CONFIGURATION", e);
        }
    }

    public static void main(String[] args) {
        new ErrorMessageLoader().load("standard-messages.yml");
    }

}
