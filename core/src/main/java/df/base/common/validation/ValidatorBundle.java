package df.base.common.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidatorBundle {

    private List<Configuration> validators = new ArrayList<>();
    private String              name;

    public List<Configuration> getValidators() {
        return validators;
    }

    public void setValidators(List<Configuration> validators) {
        this.validators = validators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Configuration {

        private String              validator;
        private String              pointer;
        private String              errorCode;
        private List<Configuration> children;

        // Getters and Setters
        public String getValidator() {
            return validator;
        }

        public void setValidator(String validator) {
            this.validator = validator;
        }

        public String getPointer() {
            return pointer;
        }

        public void setPointer(String pointer) {
            this.pointer = pointer;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public List<Configuration> getChildren() {
            return children;
        }

        public void setChildren(List<Configuration> children) {
            this.children = children;
        }

    }

}
