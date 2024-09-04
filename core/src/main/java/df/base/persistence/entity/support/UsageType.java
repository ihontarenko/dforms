package df.base.persistence.entity.support;

public enum UsageType {
    VIRTUAL,     // The field is composite and cannot be part of another field.
    EMBEDDABLE,  // The field can only be part of a virtual field and cannot be standalone.
    STANDALONE   // The field is standalone and cannot be either composite or part of a composite field.
}
