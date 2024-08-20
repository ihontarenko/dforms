package df.base.common.validation;

public class ValidationExample {

    public static void main(String[] args) {
        new ErrorMessagesFactory();

        new ValidatorFactory<>().loadValidator("standard-validation.yml");
    }

}
