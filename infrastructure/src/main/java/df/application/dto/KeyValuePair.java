package df.application.dto;

public interface KeyValuePair extends DTO {

    String getPrimaryId();

    String getKey();

    String getValue();

    default String key() {
        return getKey();
    }

    default String value() {
        return getValue();
    }

    record Simple(String id, String key, String value, String primaryId) implements KeyValuePair {

        @Override
        public String getPrimaryId() {
            return primaryId;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

    }

}
