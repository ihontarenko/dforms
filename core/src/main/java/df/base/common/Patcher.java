package df.base.common;

public interface Patcher<R, A, P> {

    R patch(A actual, P patch);

}
