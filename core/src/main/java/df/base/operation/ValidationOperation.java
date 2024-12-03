package df.base.operation;

import df.base.common.operation.Operation;
import df.base.common.validation.custom.Validator;

public interface ValidationOperation extends Operation {
    Validator createValidator();
}
