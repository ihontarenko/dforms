package df.base.internal.support;

@SuppressWarnings({"unused"})
public interface JpaCriteria {

    String entityField();

    Object entityValue();

    Comparison comparison();

    enum Comparison {
        EQUAL, NOT_EQUAL, GTE, GT
    }

    record SimpleCriteria(String entityField, Object entityValue, Comparison comparison) implements JpaCriteria {

    }

}
