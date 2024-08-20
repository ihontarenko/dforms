package df.base.common.validation.loader;

import df.base.common.validation.ErrorMessage;
import df.base.common.validation.ValidatorException;
import org.apache.commons.text.StringSubstitutor;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.text.StringSubstitutor.replace;

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

            // assign error code for 1st level message
            StringSubstitutor substitutor = new StringSubstitutor();
            collection.getMessages()
                    .forEach((errorCode, message) -> {
                        Map<String, String> values = new HashMap<>();

                        values.put("pointer", message.getPointer());

                        message.setCode(errorCode);
                        message.setMessage(replace(message.getMessage(), values));
                        message.setDetail(replace(message.getDetail(), values));
                    });

            return collection;
        } catch (Exception e) {
            throw new ValidatorException("FAILED TO LOAD VALIDATION CONFIGURATION", e);
        }
    }

    public static void main(String[] args) {
        new ErrorMessageLoader().load("standard-messages.yml");
    }

}
