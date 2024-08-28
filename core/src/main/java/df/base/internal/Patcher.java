package df.base.internal;

public interface Patcher<R, A, P> {

    R patch(A actual, P patch);

}
