package df.base.dto;

public interface NestedKeyValue extends DTO {

    String getPrimaryId();

    String getKey();

    String getValue();

    record Simple(String id, String key, String value, String primaryId) implements NestedKeyValue {

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
