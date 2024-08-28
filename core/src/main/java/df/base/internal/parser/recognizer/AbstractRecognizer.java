package df.base.internal.parser.recognizer;

abstract public class AbstractRecognizer<R, T> implements Recognizer<R, T> {

    protected final int priority;

    public AbstractRecognizer(int priority) {
        this.priority = priority;
    }

    @Override
    public int priority() {
        return priority;
    }

}
