package df.base.common.context.beans;

public class NullFieldAccessor implements FieldAccessor {

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {
        throw new UnsupportedOperationException();
    }

}
