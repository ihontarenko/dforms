package df.base.common.validation.custom;

import static df.base.common.libs.jbm.StringUtils.underscored;

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
        return "%s [message='%s']".formatted(underscored(this.getClass().getSimpleName(), true), message);
    }

}
