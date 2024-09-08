package df.base.common.elements;

public enum TagName {

    HTML, P, BODY, DIV, FORM, INPUT, SPAN, COMMENT,
    ;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
