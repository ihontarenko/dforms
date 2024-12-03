package df.base.operation;

import df.base.common.operation.AbstractOperationHandler;

public class ValidationOperationHandler extends AbstractOperationHandler<ValidationOperation> {

    public static final String NAME = "validation";

    @Override
    public String getName() {
        return NAME;
    }

}
