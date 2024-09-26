package df.base.common.validation.custom;

abstract public class AbstractValidator implements Validator {

    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
