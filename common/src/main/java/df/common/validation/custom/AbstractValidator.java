package df.common.validation.custom;

import df.common.container.StringUtils;

import static df.common.container.StringUtils.underscored;

abstract public class AbstractValidator implements Validator {

    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "%s [message='%s']".formatted(StringUtils.underscored(this.getClass().getSimpleName(), true), message);
    }

}
