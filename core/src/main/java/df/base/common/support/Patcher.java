package df.base.common.support;

public interface Patcher<R, A, P> {

    R patch(A actual, P patch);

}
